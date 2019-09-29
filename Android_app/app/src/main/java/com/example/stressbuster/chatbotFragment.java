package com.example.stressbuster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KmCallback;
import io.kommunicate.users.KMUser;

public class chatbotFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public chatbotFragment() {
        // Required empty public constructor
    }

    public static chatbotFragment newInstance() {
        return new chatbotFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        askForSystemOverlayPermission();

    }





    private void askForSystemOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getContext())) {

            //If the draw over permission is not available to open the settings screen
            //to grant the permission.

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + "com.example.stressbuster"));
            startActivityForResult(intent, 0);
        }
    }


    LinearLayout layout;

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Kommunicate.init(getContext(), "203c161f139e4a655221f277010f9b279");

        KMUser kmUser = new KMUser();
        kmUser.setUserId(firebaseUser.getUid());
        kmUser.setEmail(firebaseUser.getEmail());
        kmUser.setEmailVerified(true);

        new KmConversationBuilder(getContext())
                .setWithPreChat(true)
                .setSingleConversation(false)
                .setKmUser(kmUser)
                .launchConversation(new KmCallback() {
            @Override
            public void onSuccess(Object message) {
                Log.d("Conversation", "Success : " + message);

            }

            @Override
            public void onFailure(Object error) {
                Log.d("Conversation", "Failure : " + error);
            }
        });

        return inflater.inflate(R.layout.fragment_chatbot, container, false);

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
