package com.example.p3l;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.p3l.SharedPreferences.Entity.User;
import com.example.p3l.SharedPreferences.Preferences.UserPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    UserPreferences preferences;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = new UserPreferences(MainActivity.this);
        user = preferences.getUserLogin();

        setTitle("Atma Jogja Rental");

        if(user.getRole().equals("Customer")) {
            startActivity(new Intent(MainActivity.this, CustomerActivity.class));
        }
        else if (user.getRole().equals("Driver")) {
            startActivity(new Intent(MainActivity.this, DriverActivity.class));
        }
        else if (user.getRole().equals("Manager")) {
            startActivity(new Intent(MainActivity.this, ManagerActivity.class));
        }
    }
}