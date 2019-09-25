package com.example.stressbuster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class remindersFragment extends Fragment {
    private OnFragmentInteractionListener mListener;


    public remindersFragment() {
        // Required empty public constructor
    }

    public static remindersFragment newInstance() {
        remindersFragment fragment = new remindersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerViewForReminders);
        final RecyclerView recyclerView1 = view.findViewById(R.id.recyclerViewForUserReminders);

        firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("reminders")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    Map<String, String> myListDataList = new HashMap<>();

                    List<DocumentSnapshot> list = Objects.requireNonNull(queryDocumentSnapshots).getDocuments();
                    for(DocumentSnapshot documentSnapshot: list) {
                        myListDataList.put(documentSnapshot.getId(), documentSnapshot.get("reminder").toString());

                    }

                    MyListAdapter adapter = new MyListAdapter(myListDataList);

                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView1.setAdapter(adapter);

                }
            });


        firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("personalInfo")
                .document("sampleDoesTheThing")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        final Map<String, String> personalInfo = new HashMap<>();

                        personalInfo.put("collegeClass",documentSnapshot.get("collegeClass").toString());
                        personalInfo.put("collegeName", documentSnapshot.get("collegeName").toString());
                        personalInfo.put("userName", documentSnapshot.get("userName").toString());

                        firebaseFirestore.collection("studentsWhoCompletedStuff")
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                        List<DocumentSnapshot> documentSnapshots = null;
                                        if (queryDocumentSnapshots != null) {
                                            documentSnapshots = queryDocumentSnapshots.getDocuments();

                                            for(final DocumentSnapshot documentSnapshot1: documentSnapshots) {

                                                firebaseFirestore.collection("TeachersAssignmentsList")
                                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                                assert queryDocumentSnapshots != null;
                                                                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                                                                List<FacultyDataReminders> getMyAssignmentList = new ArrayList<>();
                                                                for(DocumentSnapshot documentSnapshot: documentSnapshots) {

                                                                    String collegeName = Objects.requireNonNull(documentSnapshot.get("UniversityName")).toString();
                                                                    String className = Objects.requireNonNull(documentSnapshot.get("UniversityClass")).toString();

                                                                    Log.d("CollegeName, ClassName", collegeName+className);
                                                                    Log.d("CollegeName, ClassName", personalInfo.get("collegeName")+personalInfo.get("collegeClass"));

                                                                    Log.d("Complete Info", documentSnapshot1.get("IdOfTheCompletedAssignment").toString() + "\n" + documentSnapshot1.get("UserWhoCompletedThat"));

                                                                    if(Objects.equals(personalInfo.get("collegeName"), collegeName)
                                                                            && Objects.equals(personalInfo.get("collegeClass"), className)
                                                                            && documentSnapshot1.get("IdOfTheCompletedAssignment").toString().equals(documentSnapshot.getId())
                                                                            && !documentSnapshot1.get("UserWhoCompletedThat").toString().equals(firebaseUser.getEmail())) {

                                                                        getMyAssignmentList.add(new FacultyDataReminders(documentSnapshot.get("assignmentInfo").toString(), documentSnapshot.get("awardedPoints").toString(), documentSnapshot.getId().toString()));
                                                                    }
                                                                }

                                                                Log.d("ListOfFacultyReminders", getMyAssignmentList.size()+"");
                                                                FacultyAwardedAdapter adapter = new FacultyAwardedAdapter(getMyAssignmentList);

                                                                recyclerView.setHasFixedSize(true);
                                                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                                recyclerView.setAdapter(adapter);

                                                            }
                                                        });





                                            }

                                        } else {


                                            firebaseFirestore.collection("TeachersAssignmentsList")
                                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                            assert queryDocumentSnapshots != null;
                                                            List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                                                            List<FacultyDataReminders> getMyAssignmentList = new ArrayList<>();
                                                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {

                                                                String collegeName = Objects.requireNonNull(documentSnapshot.get("UniversityName")).toString();
                                                                String className = Objects.requireNonNull(documentSnapshot.get("UniversityClass")).toString();

                                                                Log.d("CollegeName, ClassName", collegeName + className);
                                                                Log.d("CollegeName, ClassName", personalInfo.get("collegeName") + personalInfo.get("collegeClass"));


                                                                if (Objects.equals(personalInfo.get("collegeName"), collegeName)
                                                                        && Objects.equals(personalInfo.get("collegeClass"), className)) {

                                                                    getMyAssignmentList.add(new FacultyDataReminders(documentSnapshot.get("assignmentInfo").toString(), documentSnapshot.get("awardedPoints").toString(), documentSnapshot.getId().toString()));
                                                                }
                                                            }

                                                            Log.d("ListOfFacultyReminders", getMyAssignmentList.size() + "");
                                                            FacultyAwardedAdapter adapter = new FacultyAwardedAdapter(getMyAssignmentList);

                                                            recyclerView.setHasFixedSize(true);
                                                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                            recyclerView.setAdapter(adapter);

                                                        }
                                                    });

                                        }


                                    }
                                });


                    }
                });



        MaterialButton materialButton = view.findViewById(R.id.BtnForAddingReminders);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText editText = new EditText(getContext());

                new AlertDialog.Builder(getContext())
                .setTitle("Add new Reminder")
                .setMessage("Type in the ones you want to add...")
                .setView(editText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String text = editText.getText().toString();

                                HashMap<String, String> a = new HashMap<>();
                                a.put("reminder", text);

                                firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("reminders")
                                        .add(a)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                    }
                                });

                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                .show();




            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
