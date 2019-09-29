package com.example.stressbuster;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class dashboardFragment extends Fragment implements remindersFragment.OnFragmentInteractionListener {

    private OnFragmentInteractionListener mListener;

    public dashboardFragment() {
        // Required empty public constructor
    }

    public static dashboardFragment newInstance() {
        return new dashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);


        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        final TextView welcomeUser = view.findViewById(R.id.dashboard_welcomingUser);

        Source source = Source.CACHE;
        firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("personalInfo")
                .document("sampleDoesTheThing")
                .get(source)
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            String apple = task.getResult().get("userName").toString();
                            welcomeUser.setText("Hey "+apple+"!");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Exception", e.toString());
            }
        });

        final LineChartView lineChartData = view.findViewById(R.id.chart);
        lineChartData.setInteractive(true);
        lineChartData.setZoomType(ZoomType.HORIZONTAL);

        Viewport viewport = new Viewport(lineChartData.getCurrentViewport());

        viewport.top = 5;
        viewport.left = 0;
        viewport.right = 7;
        viewport.bottom = 0;

        lineChartData.setMaximumViewport(viewport);
        lineChartData.setCurrentViewport(viewport);

        firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("SmileLevel")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        List<PointValue> values = new ArrayList<>();
                        Map<Integer, Integer> mapStuffHappensHere = new HashMap<>();

                        List<DocumentSnapshot> list = Objects.requireNonNull(queryDocumentSnapshots).getDocuments();
                        for(DocumentSnapshot documentSnapshot: list) {
                            String s = documentSnapshot.getId();
                            int output = Integer.valueOf(s) - 20190921;
                            mapStuffHappensHere.put(output, Integer.parseInt(Objects.requireNonNull(documentSnapshot.get("smileLevel")).toString()));
                        }


                        // Copy all data from hashMap into TreeMap
                        TreeMap<Integer, Integer> sorted = new TreeMap<>(mapStuffHappensHere);
                        Set<Map.Entry<Integer, Integer>> getSortedStuff = sorted.entrySet();

                        int i = list.size()-7;
                        for(Map.Entry<Integer, Integer> tempOnes: getSortedStuff) {
                            if(i <= list.size()) {
                                values.add(new PointValue(i, tempOnes.getValue()));
                                i++;
                            } else {
                                break;
                            }
                        }


                        Log.d("Values in the graph", list.size()+"");

                        Line line = new Line(values)
                                .setColor(Color.parseColor("#000000"))
                                .setCubic(true)
                                .setHasPoints(true);

                        List<Line> lines = new ArrayList<>();
                        lines.add(line);

                        LineChartData data = new LineChartData();
                        data.setLines(lines);

                        lineChartData.setLineChartData(data);

                    }
                });


        final RecyclerView recyclerView = view.findViewById(R.id.listViewForRemindersInDashboard);

        firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("reminders")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        Map<String, String> myListDataList = new HashMap<>();

                        List<DocumentSnapshot> list = Objects.requireNonNull(queryDocumentSnapshots).getDocuments();
                        for(DocumentSnapshot documentSnapshot: list) {
                            myListDataList.put(documentSnapshot.getId(), documentSnapshot.get("reminder").toString());

                        }

                        MyListAdapterForDashboardFragment adapter = new MyListAdapterForDashboardFragment(myListDataList);

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);

                    }
                });

        MaterialButton showReminders = view.findViewById(R.id.showMeMore_remindersInDashboard);
        showReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_userDashboard, new remindersFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final SmileRating smileRating = view.findViewById(R.id.smile_rating);
        smileRating.setSelectedSmile(BaseRating.OKAY, false);

        MaterialButton yep_itsdone = view.findViewById(R.id.yep_itsdone);
        yep_itsdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int level = smileRating.getRating();

                HashMap<String, String> a = new HashMap<>();
                a.put("smileLevel", level+"");

                String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
                Log.d("Date", date);

                firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("SmileLevel")
                        .document(date)
                        .set(a)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("SmileRating", "Updated");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });




        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
