package com.example.ed;

import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // def variable
    HomeFragment homeFragment;
    SearchFragment searchFragment;
    LoginFragment loginFragment;
    UserFragment userFragment;
    SearchApiFragment searchApiFragment;


    FragmentTransaction ft = getFragmentManager().beginTransaction();

    // def method
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            homeFragment = new HomeFragment();
            searchFragment = new SearchFragment();
            loginFragment = new LoginFragment();
            userFragment = new UserFragment();
            searchApiFragment = new SearchApiFragment();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

            switch (item.getItemId()) {
                case R.id.menu_home:
                    ft.replace(R.id.main_content, homeFragment);
                    ft.commit();
                    return true;
                case R.id.menu_search:
                    ft.replace(R.id.main_content, searchFragment);
                    ft.commit();
                    return true;
                case R.id.menu_login:
                    ft.replace(R.id.main_content, loginFragment);
                    ft.commit();
                    return true;

                case R.id.user:
                    ft.replace(R.id.main_content, userFragment);
                    ft.commit();
                    return true;

                case R.id.menu_search_api:
                    ft.replace(R.id.main_content, searchApiFragment);
                    ft.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.add(R.id.main_content, new RegisterFragment());
//        ft.commit();
        // test Fragment
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.main_content, new HomeFragment());
        ft.commit();
//
//        // navigation
        BottomNavigationView navView = findViewById(R.id.my_toolbar);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navView.getMenu();
        menu.findItem(R.id.user).setVisible(false);
        navView.setSelectedItemId(R.id.menu_home);

    }
}
