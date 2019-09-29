package com.example.stressbuster;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.List;
import java.util.Objects;

public class BottomSheetNavigationFragment extends BottomSheetDialogFragment {


    public static BottomSheetNavigationFragment newInstance() {

        Bundle args = new Bundle();

        BottomSheetNavigationFragment fragment = new BottomSheetNavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Bottom Sheet Callback
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            //check the slide offset and change the visibility of close button
            if (slideOffset > 0.5) {
                closeButton.setVisibility(View.VISIBLE);
            } else {
                closeButton.setVisibility(View.GONE);
            }
        }
    };

    private ImageView closeButton;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        //Get the content View
        final View contentView = View.inflate(getContext(), R.layout.bottom_nav_drawer, null);

        final SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();


        NavigationView navigationView = contentView.findViewById(R.id.navigation_view);
        final TextView userName = contentView.findViewById(R.id.user_name);
        final TextView userEmail = contentView.findViewById(R.id.user_email);


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userEmail.setText(firebaseUser.getEmail());

        final Button button = contentView.findViewById(R.id.pointsRedeemButton);

        Source source = Source.DEFAULT;
        firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("personalInfo")
                .document("sampleDoesTheThing")
                .get(source)
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            String apple = task.getResult().get("userName").toString();
                            userName.setText(apple);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Exception", e.toString());
            }
        });


        firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("userPoints")
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
                   @Override
                   public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                       List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                       int points = 0;
                       for(DocumentSnapshot apple: list) {
                           points = points + Integer.parseInt(apple.get("UserPoints").toString());
                       }
                       button.setText(points+"");
                   }
               });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RedeemPoints.class));
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                startActivity(new Intent(getContext(), HistoryOfPaymentActivity.class));

                return false;
            }
        });


        dialog.setContentView(contentView);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (width * 0.9f);
        // Set alert dialog height equal to screen height 70%
        int dialogWindowHeight = (int) (height * 0.9f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;




        //implement navigation menu item click event
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav01:

                        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        LayoutInflater layoutInflater = alertDialog.getLayoutInflater();

                        final View view = layoutInflater.inflate(R.layout.pacebook_like_loginpage, null);
                        alertDialog.setCancelable(true);

                        final EditText userNameOfFacebookInfo = view.findViewById(R.id.getUserNameOfPacebook);
                        final EditText passwordForFacebookInfo = view.findViewById(R.id.getPasswordOfPacebook);

                        final Button loginDetails = view.findViewById(R.id.loginButtonForpacebook);
                        loginDetails.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                editor.putString("FacebookUserID", userNameOfFacebookInfo.getText().toString());
                                editor.putString("FacebookUserPassword", passwordForFacebookInfo.getText().toString());
                                editor.apply();

                                Snackbar.make(view, "User details stored securely", Snackbar.LENGTH_SHORT).show();

                            }
                        });

                        alertDialog.setView(view);
                        alertDialog.show();


                        alertDialog.getWindow().setAttributes(layoutParams);









//                        final View view = layoutInflater.inflate(R.layout.get_social_media_info_bottomappbar, null);
//                        alertDialog.setTitle("Facebook Credentials");
//                        alertDialog.setCancelable(true);
//
//                        final EditText userNameOfFacebookInfo = view.findViewById(R.id.editTextForUserName);
//                        final EditText passwordForFacebookInfo = view.findViewById(R.id.passwordEditTextForDialog);
//
//                        userNameOfFacebookInfo.setHint(sharedPref.getString("FacebookUserID",""));
//                        passwordForFacebookInfo.setHint(sharedPref.getString("FacebookUserPassword",""));
//
//                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                alertDialog.dismiss();
//                            }
//                        });
//
//
//                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Update", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                        editor.putString("FacebookUserID", userNameOfFacebookInfo.getText().toString());
//                                        editor.putString("FacebookUserPassword", passwordForFacebookInfo.getText().toString());
//                                        editor.apply();
//
//
//                            }
//                        });
//
//
//                        alertDialog.setView(view);
//                        alertDialog.show();



                        break;
                    case R.id.nav02:


                        final AlertDialog alertDialog2 = new AlertDialog.Builder(getContext()).create();
                        LayoutInflater layoutInflater2 = alertDialog2.getLayoutInflater();
                        final View view2 = layoutInflater2.inflate(R.layout.finstagram_identical_stuffpage, null);
                        alertDialog2.setCancelable(true);

                        final EditText userNameOfInstagramInfo = view2.findViewById(R.id.FinstagramUser);
                        final EditText passwordForInstagramInfo = view2.findViewById(R.id.finstagramPassword);
                        final Button button = view2.findViewById(R.id.loginButtonForFinstagram);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                editor.putString("InstagramUserID", userNameOfInstagramInfo.getText().toString());
                                editor.putString("InstagramUserPassword", passwordForInstagramInfo.getText().toString());
                                editor.apply();

                                Snackbar.make(view, "User details stored securely", Snackbar.LENGTH_SHORT).show();
                            }
                        });


                        alertDialog2.setView(view2);
                        alertDialog2.show();
                        alertDialog2.getWindow().setAttributes(layoutParams);

                        break;

                    case R.id.nav03:


                        final AlertDialog alertDialog3 = new AlertDialog.Builder(getContext()).create();
                        LayoutInflater layoutInflater3 = alertDialog3.getLayoutInflater();
                        final View view3 = layoutInflater3.inflate(R.layout.twitter_identical_page, null);
                        alertDialog3.setCancelable(true);

                        final EditText userNameOfTwitterInfo = view3.findViewById(R.id.TweeterUserNameText);
                        final EditText passwordForTwitterInfo = view3.findViewById(R.id.TweeterPassword);

                        final MaterialButton apple = view3.findViewById(R.id.loginForTweeter);
                        apple.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                editor.putString("TwitterUserID", userNameOfTwitterInfo.getText().toString());
                                editor.putString("TwitterUserPassword", passwordForTwitterInfo.getText().toString());
                                editor.apply();

                                Snackbar.make(view, "User details stored securely", Snackbar.LENGTH_SHORT).show();
                            }
                        });


                        alertDialog3.setView(view3);
                        alertDialog3.show();

                        alertDialog3.getWindow().setAttributes(layoutParams);



                        break;






                    case R.id.nav04:


                        final AlertDialog alertDialog4 = new AlertDialog.Builder(getContext()).create();
                        LayoutInflater layoutInflater4 = alertDialog4.getLayoutInflater();
                        final View view4 = layoutInflater4.inflate(R.layout.get_social_media_info_bottomappbar, null);
                        alertDialog4.setTitle("Twitter Credentials");
                        alertDialog4.setCancelable(true);

                        final EditText userNameOfRedditInfo = view4.findViewById(R.id.editTextForUserName);
                        final EditText passwordForRedditInfo = view4.findViewById(R.id.passwordEditTextForDialog);

                        userNameOfRedditInfo.setHint(sharedPref.getString("RedditUserID",""));
                        passwordForRedditInfo.setHint(sharedPref.getString("RedditUserPassword",""));

                        alertDialog4.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                alertDialog4.dismiss();
                            }
                        });


                        alertDialog4.setButton(AlertDialog.BUTTON_NEUTRAL, "Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("RedditUserID", userNameOfRedditInfo.getText().toString());
                                editor.putString("RedditUserPassword", passwordForRedditInfo.getText().toString());
                                editor.apply();


                            }
                        });

                        alertDialog4.setView(view4);
                        alertDialog4.show();

                        break;


                    case R.id.user_privacyPolicy:

                        final AlertDialog alertDialog5 = new AlertDialog.Builder(getContext()).create();
                        LayoutInflater layoutInflater5 = alertDialog5.getLayoutInflater();
                        final View view5 = layoutInflater5.inflate(R.layout.user_privacy_policy, null);
                        alertDialog5.setCancelable(true);
                        alertDialog5.setView(view5);
                        alertDialog5.setTitle("Privacy Policy");
                        alertDialog5.setButton(DialogInterface.BUTTON_POSITIVE, "Looks Nice!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dismiss();
                            }
                        });
                        alertDialog5.show();
                }
                return false;
            }
        });

        closeButton = contentView.findViewById(R.id.close_image_view);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

    }

}