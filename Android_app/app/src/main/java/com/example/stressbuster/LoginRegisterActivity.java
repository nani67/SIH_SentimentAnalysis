package com.example.stressbuster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginRegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        setStatusBarGradientOk.setStatusBarGradient(this);

        firebaseAuth = FirebaseAuth.getInstance();

        TextView switcherForLoginAndReg = findViewById(R.id.getSwitchedUp);
        TextView switcherForLoginAndReg2 = findViewById(R.id.getSwitchedUpTwo);
        final MaterialCardView registerCardView = findViewById(R.id.card_view);
        final MaterialCardView loginCardView = findViewById(R.id.loginCardView);

        switcherForLoginAndReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCardView.setVisibility(View.INVISIBLE);
                loginCardView.setVisibility(View.VISIBLE);
            }
        });
        switcherForLoginAndReg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginCardView.setVisibility(View.INVISIBLE);
                registerCardView.setVisibility(View.VISIBLE);

            }
        });

        final com.google.android.material.textfield.TextInputEditText emailObject = findViewById(R.id.emailIDLoginText);
        final com.google.android.material.textfield.TextInputEditText passwordObject = findViewById(R.id.passwordLoginText);

        final com.google.android.material.textfield.TextInputEditText emailRegister = findViewById(R.id.emailIDRegisterText);
        final com.google.android.material.textfield.TextInputEditText nameOfTheUser = findViewById(R.id.nameOfTheUserText);
        final com.google.android.material.textfield.TextInputEditText collName = findViewById(R.id.CollNameText);
        final com.google.android.material.textfield.TextInputEditText collRollNo = findViewById(R.id.CollRollNoText);
        final com.google.android.material.textfield.TextInputEditText passwordRegister = findViewById(R.id.passwordRegisterText);

        MaterialButton materialButton2 = findViewById(R.id.loginUser);
        materialButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Please wait while we log you in...", Snackbar.LENGTH_LONG).show();

                String password = Objects.requireNonNull(passwordObject.getText()).toString();
                String email = Objects.requireNonNull(emailObject.getText()).toString();

                if(!(password.isEmpty() && email.isEmpty())) {

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(LoginRegisterActivity.this, FirstTimeInitialization.class));

                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Password or Email ID is incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });




            MaterialButton materialButton = findViewById(R.id.registerUser);
            materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String emailIDForRegistration = emailRegister.getText().toString();
                    String passwordOfReg = passwordRegister.getText().toString();

                    firebaseAuth.createUserWithEmailAndPassword(emailIDForRegistration, passwordOfReg)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                        Map<String, String> userInfo = new HashMap<>();


                                        String nameOfTheuser = nameOfTheUser.getText().toString();
                                        String collegeName = collName.getText().toString();
                                        String CollRollNo = collRollNo.getText().toString();

                                        userInfo.put("userName", nameOfTheuser);
                                        userInfo.put("collegeName", collegeName);
                                        userInfo.put("collegeClass", CollRollNo);

                                        firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("personalInfo").document("sampleDoesTheThing")
                                                .set(userInfo)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("Exception", e.toString());

                                                    }
                                                });

                                    } else {
                                        Log.d("Creating user", "Failed");
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                    Log.d("Login issue", e.toString());
                                }
                            });



                    firebaseAuth.signInWithEmailAndPassword(emailIDForRegistration, passwordOfReg)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(LoginRegisterActivity.this, FirstTimeInitialization.class));
                                }
                            });

                }
            });


    }
}
