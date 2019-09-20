package com.example.stressbuster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

public class MainActivity extends AppCompatActivity {

    TextView loaderDataPlacer;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarGradientOk.setStatusBarGradient(this);

        firebaseAuth = FirebaseAuth.getInstance();
        loaderDataPlacer = findViewById(R.id.loadingStatus);

        final Context context = this;
        final Handler handler = new Handler();

        final View view = findViewById(android.R.id.content);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!NetworkConnectivity()) {
                    loaderDataPlacer.setText(R.string.internet_connectivity_not_found);

                    new AlertDialog.Builder(context)
                            .setTitle("No Internet connection")
                            .setMessage("Please make sure the internet connectivity is available.")
                            .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                    Snackbar.make(view, "Close and reopen app after enabling Internet", Snackbar.LENGTH_INDEFINITE).show();

                                }
                            })
                            .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                }
                            })
                            .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                    System.exit(0);
                                }
                            })
                            .setIcon(R.drawable.ic_signal_wifi_off_black_24dp)
                            .show();
                } else {

                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if(firebaseUser != null) {
                        startActivity(new Intent(MainActivity.this, UserDashboard.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
                    }
                }

            }
        }, 2500);


    }

    public void onStart() {
        super.onStart();
    }

    private boolean NetworkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo()!= null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
