from imutils import face_utils
from scipy.spatial import distance as dist
import cv2

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

    def detect_drowsy(self, rect, gray, frame, person):
        # loop over the face detections
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
            person.counter += 1

            # if the eyes were closed for a sufficient number of
            # then sound the alarm
            if person.counter >= self.EYE_AR_CONSEC_FRAMES:
                # if the alarm is not on, turn it on
                if not self.ALARM_ON:
                    self.ALARM_ON = True

                # draw an alarm on the frame
                cv2.putText(frame, person.name + " DROWSINESS ALERT!", (10, 30),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)

                person.inc_drowsiness()
        # otherwise, the eye aspect ratio is not below the blink
        # threshold, so reset the counter and alarm
        else:
            person.counter = 0
            self.ALARM_ON = False

        # draw the computed eye aspect ratio on the frame to help
        # with debugging and setting the correct eye aspect ratio
        # thresholds and frame counters
        cv2.putText(frame, "EAR: {:.2f}".format(ear), (300, 30),
            cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)


class Person:
    def __init__(self, name):
        self.counter = 0
        self.name = name
        self.fatigue_counter = 0

    def inc_drowsiness(self):
        self.fatigue_counter += 1