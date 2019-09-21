# USAGE
# python recognize_video.py --detector face_detection_model \
#	--embedding-model openface_nn4.small2.v1.t7 \
#	--recognizer output/recognizer.pickle \
#	--le output/le.pickle

# import the necessary packages
from imutils.video import VideoStream
from imutils.video import FPS
import numpy as np
import argparse
import imutils
import pickle
import time
import cv2
import os
from threading import Thread
import dlib
from scipy.spatial import distance as dist
from imutils import face_utils
import playsound
import datetime


class DrowsinessDetector:
    def __init__(self, EYE_AR_THRESH, EYE_AR_CONSEC_FRAMES, ALARM_ON, detector, predictor):
        self.EYE_AR_THRESH = EYE_AR_THRESH
        self.EYE_AR_CONSEC_FRAMES = EYE_AR_CONSEC_FRAMES
        # initialize the frame counter as well as a boolean used to
        # indicate if the alarm is going off
        self.COUNTER = 0
        self.ALARM_ON = ALARM_ON

        self.detector = detector
        self.predictor = predictor


        (self.lStart, self.lEnd) = face_utils.FACIAL_LANDMARKS_IDXS["left_eye"]
        (self.rStart, self.rEnd) = face_utils.FACIAL_LANDMARKS_IDXS["right_eye"]

    def sound_alarm(self, path):
        #play an alarm sound
       	playsound.playsound(path)

    def eye_aspect_ratio(self, eye):
    	# compute the euclidean distances between the two sets of
    	# vertical eye landmarks (x, y)-coordinates
    	A = dist.euclidean(eye[1], eye[5])
    	B = dist.euclidean(eye[2], eye[4])

    	# compute the euclidean distance between the horizontal
    	# eye landmark (x, y)-coordinates
    	C = dist.euclidean(eye[0], eye[3])

    	# compute the eye aspect ratio
    	ear = (A + B) / (2.0 * C)

    	# return the eye aspect ratio
    	return ear

    def detect_drowsy(self, frame):
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    	# detect faces in the grayscale frame
        rects = self.detector(gray, 0)
    	# loop over the face detections
        for rect in rects:
    		# determine the facial landmarks for the face region, then
    		# convert the facial landmark (x, y)-coordinates to a NumPy
    		# array
            shape = self.predictor(gray, rect)
            shape = face_utils.shape_to_np(shape)

    		# extract the left and right eye coordinates, then use the
    		# coordinates to compute the eye aspect ratio for both eyes
            leftEye = shape[self.lStart:self.lEnd]
            rightEye = shape[self.rStart:self.rEnd]
            leftEAR = self.eye_aspect_ratio(leftEye)
            rightEAR = self.eye_aspect_ratio(rightEye)

    		# average the eye aspect ratio together for both eyes
            ear = (leftEAR + rightEAR) / 2.0

    		# compute the convex hull for the left and right eye, then
    		# visualize each of the eyes
            leftEyeHull = cv2.convexHull(leftEye)
            rightEyeHull = cv2.convexHull(rightEye)
            cv2.drawContours(frame, [leftEyeHull], -1, (0, 255, 0), 1)
            cv2.drawContours(frame, [rightEyeHull], -1, (0, 255, 0), 1)

    		# check to see if the eye aspect ratio is below the blink
    		# threshold, and if so, increment the blink frame counter
            if ear < self.EYE_AR_THRESH:
                self.COUNTER += 1

    			# if the eyes were closed for a sufficient number of
    			# then sound the alarm
                if self.COUNTER >= self.EYE_AR_CONSEC_FRAMES:
    				# if the alarm is not on, turn it on
                    if not self.ALARM_ON:
                        self.ALARM_ON = True

    				# draw an alarm on the frame
                    cv2.putText(frame, "DROWSINESS ALERT!", (10, 30),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)

    		# otherwise, the eye aspect ratio is not below the blink
    		# threshold, so reset the counter and alarm
            else:
                self.COUNTER = 0
                self.ALARM_ON = False

    		# draw the computed eye aspect ratio on the frame to help
    		# with debugging and setting the correct eye aspect ratio
    		# thresholds and frame counters
            cv2.putText(frame, "EAR: {:.2f}".format(ear), (300, 30),
                cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)

'''
hogFaceDetector = dlib.get_frontal_face_detector()
faceRects = hogFaceDetector(frameDlibHogSmall, 0)
for faceRect in faceRects:
    x1 = faceRect.left()
    y1 = faceRect.top()
    x2 = faceRect.right()
    y2 = faceRect.bottom()
'''

# construct the argument parser and parse the arguments
ap = argparse.ArgumentParser()
#ap.add_argument("-d", "--detector", required=True,
#	help="path to OpenCV's deep learning face detector")
ap.add_argument("-m", "--embedding-model", required=True,
	help="path to OpenCV's deep learning face embedding model")
ap.add_argument("-d", "--detector", required=True,
	help="path to OpenCV's deep learning face detector")
ap.add_argument("-r", "--recognizer", required=True,
	help="path to model trained to recognize faces")
ap.add_argument("-l", "--le", required=True,
	help="path to label encoder")
ap.add_argument("-c", "--confidence", type=float, default=0.5,
	help="minimum probability to filter weak detections")

# add drowsiness detector shape predictor
ap.add_argument("-p", "--shape-predictor", required=True,
	help="path to facial landmark predictor")
args = vars(ap.parse_args())

# load our serialized face detector from disk
print("[INFO] loading face detector...")
protoPath = os.path.sep.join([args["detector"], "deploy.prototxt"])
modelPath = os.path.sep.join([args["detector"], "res10_300x300_ssd_iter_140000.caffemodel"])
detector = cv2.dnn.readNetFromCaffe(protoPath, modelPath)
# load our serialized face embedding model from disk
print("[INFO] loading face recognizer...")
embedder = cv2.dnn.readNetFromTorch(args["embedding_model"])

# load drowsiness detector models
print("[INFO] loading facial landmark predictor...")
drowsiness_detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor(args["shape_predictor"])

# initialize drowsiness detector object
drowsinessDetector = DrowsinessDetector(0.3, 30, True, drowsiness_detector, predictor)


# load the actual face recognition model along with the label encoder
recognizer = pickle.loads(open(args["recognizer"], "rb").read())
le = pickle.loads(open(args["le"], "rb").read())

# initialize the video stream, then allow the camera sensor to warm up
print("[INFO] starting video stream...")
vs = VideoStream(src=0).start()
time.sleep(2.0)

# start the FPS throughput estimator
fps = FPS().start()

# loop over frames from the video file stream
while True:
	# grab the frame from the threaded video stream
    frame = vs.read()

    start = datetime.datetime.now()

    # resize the frame to have a width of 600 pixels (while
    # maintaining the aspect ratio), and then grab the image
    # dimensions
    frame = imutils.resize(frame, width=600);
    (h, w) = frame.shape[:2]
    # construct a blob from the image
    imageBlob = cv2.dnn.blobFromImage(
    cv2.resize(frame, (300, 300)), 1.0, (300, 300),
    (104.0, 177.0, 123.0), swapRB=False, crop=False)

    # apply OpenCV's deep learning-based face detector to localize
    # faces in the input image
    detector.setInput(imageBlob)
    detections = detector.forward();

    #gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    # detect faces in the grayscale frame
    #rects = drowsiness_detector(gray, 0)
    #faceRects = hogFaceDetector(rects, 0)

    t = Thread(target=drowsinessDetector.detect_drowsy(frame))
    t.deamon = True
    t.start()
    #drowsinessDetector.detect_drowsy(frame)

    # loop over the detections
    for i in range(0, detections.shape[2]):
        # extract the confidence (i.e., probability) associated with
        # the prediction
        confidence = detections[0, 0, i, 2]

        # filter out weak detections
        if confidence > args["confidence"]:
        # compute the (x, y)-coordinates of the bounding box for
        # the face
            box = detections[0, 0, i, 3:7] * np.array([w, h, w, h])
            (startX, startY, endX, endY) = box.astype("int")

            # extract the face ROI
            face = frame[startY:endY, startX:endX]
            (fH, fW) = face.shape[:2]

            # ensure the face width and height are sufficiently large
            if fW < 20 or fH < 20:
            	continue

            # construct a blob for the face ROI, then pass the blob
            # through our face embedding model to obtain the 128-d
            # quantification of the face
            faceBlob = cv2.dnn.blobFromImage(face, 1.0 / 255,
            	(96, 96), (0, 0, 0), swapRB=True, crop=False)
            embedder.setInput(faceBlob)
            vec = embedder.forward()

            # perform classification to recognize the face
            preds = recognizer.predict_proba(vec)[0]
            j = np.argmax(preds)
            proba = preds[j]
            name = le.classes_[j]


            # draw the bounding box of the face along with the
            # associated probability
            text = "{}: {:.2f}%".format(name, proba * 100)
            y = startY - 10 if startY - 10 > 10 else startY + 10
            cv2.rectangle(frame, (startX, startY), (endX, endY),
            	(0, 0, 255), 2)
            cv2.putText(frame, text, (startX, y),
            	cv2.FONT_HERSHEY_SIMPLEX, 0.45, (0, 0, 255), 2)

    # update the FPS counter
    fps.update()

    # show the output frame
    cv2.imshow("Frame", frame)
    key = cv2.waitKey(1) & 0xFF

    end = datetime.datetime.now()
    elapsed_time = end - start
    print(elapsed_time.total_seconds() * 1000)

    # if the `q` key was pressed, break from the loop
    if key == ord("q"):
        break

    t.join()

# stop the timer and display FPS information
fps.stop()
print("[INFO] elasped time: {:.2f}".format(fps.elapsed()))
print("[INFO] approx. FPS: {:.2f}".format(fps.fps()))

# do a bit of cleanup
cv2.destroyAllWindows()
vs.stop()







