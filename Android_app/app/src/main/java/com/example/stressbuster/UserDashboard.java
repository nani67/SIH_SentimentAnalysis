package com.example.stressbuster;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UserDashboard extends AppCompatActivity
        implements dashboardFragment.OnFragmentInteractionListener,
            chatbotFragment.OnFragmentInteractionListener,
            mentorConnections.OnFragmentInteractionListener,
            remindersFragment.OnFragmentInteractionListener,
            userSettingsFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        setStatusBarGradientOk.setStatusBarGradient(this);

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_chatbot:

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_userDashboard, new chatbotFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        break;
                    case R.id.navigation_dashboard:

                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.replace(R.id.fragment_userDashboard, new dashboardFragment());
                        fragmentTransaction2.addToBackStack(null);
                        fragmentTransaction2.commit();

                        break;
                    case R.id.navigation_mentor_counselling:

                        FragmentManager fragmentManager3 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        fragmentTransaction3.replace(R.id.fragment_userDashboard, new mentorConnections());
                        fragmentTransaction3.addToBackStack(null);
                        fragmentTransaction3.commit();
                        break;

                    case R.id.navigation_reminders:

                        FragmentManager fragmentManager4 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
                        fragmentTransaction4.replace(R.id.fragment_userDashboard, new remindersFragment());
                        fragmentTransaction4.addToBackStack(null);
                        fragmentTransaction4.commit();
                        break;

                    case R.id.userSettings:

                        FragmentManager fragmentManager5 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction5 = fragmentManager5.beginTransaction();
                        fragmentTransaction5.replace(R.id.fragment_userDashboard, new userSettingsFragment());
                        fragmentTransaction5.addToBackStack(null);
                        fragmentTransaction5.commit();
                        break;

                }
                return false;
            }
        });


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_userDashboard, new dashboardFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        //click event over navigation menu like back arrow or hamburger icon
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomSheetDialogFragment = BottomSheetNavigationFragment.newInstance();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");

            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_items, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
