package com.example.stressbuster;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class mentorConnections extends Fragment {
    private OnFragmentInteractionListener mListener;

    public mentorConnections() {
        // Required empty public constructor
    }

    public static mentorConnections newInstance() {
        return new mentorConnections();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mentor_connections, container, false);

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerViewForCounsellingHistory);


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("counsellingHistory")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        List<DocumentSnapshot> list = Objects.requireNonNull(queryDocumentSnapshots).getDocuments();
                        List<MyListDataForCounselling> myListDataForCounsellings = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot: list) {

                            myListDataForCounsellings.add(new MyListDataForCounselling(documentSnapshot.get("typeOfCounselling").toString(),
                                    documentSnapshot.get("counsellorName").toString(),
                                    documentSnapshot.get("dateOfCounselling").toString(),
                                    documentSnapshot.get("durationOfCounselling").toString(),
                                    Integer.parseInt(documentSnapshot.get("ratingsForSession").toString())));


                        }


                        MyListAdapterForCounselling adapter = new MyListAdapterForCounselling(myListDataForCounsellings);

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);


                    }
                });






        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
