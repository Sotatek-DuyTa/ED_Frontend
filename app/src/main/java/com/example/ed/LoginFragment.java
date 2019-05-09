package com.example.ed;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

public class LoginFragment extends Fragment {

    // define variable
    Button button_login;
    Button suggest;
    EditText email;
    EditText password;



    // define method
    public void loginHandle(View v) {
        button_login= v.findViewById(R.id.login_btn);
        email = v.findViewById(R.id.email_input);
        password = v.findViewById(R.id.password_input);


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                System.out.println(email.getText());
//                System.out.println("password: " + password.getText().toString());
//
//                Log.d("e","clicked");
                String pathUrl = "/login";
                String params = "{\"email\":\"" + email.getText() + "\",\"password\":\""+ password.getText() +"\"}";
//
                LoginRequest login = new LoginRequest(pathUrl, params, v.getContext());
                login.execute();
//                OkHttpHandler request = new OkHttpHandler("/login", );
//                request.execute();
//                System.out.println(request.getResult());
            }
        });
    }

    public void redirectToRegisterPage (View v) {
        suggest = v.findViewById(R.id.redirect_to_register_page);
        suggest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

                ft.replace(R.id.main_content, new RegisterFragment());
                ft.commit();
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.login, container, false);

        suggest = view.findViewById(R.id.redirect_to_register_page);

//        SpannableString str = new SpannableString("Bạn chưa có tài khoản ? Đăng ký ngay !");
        SpannableString str = new SpannableString( getString(R.string.suggest_register));
        str.setSpan(new ForegroundColorSpan(Color.rgb(244, 67, 54)), 24, 31, 0);
        suggest.setText(str);

        loginHandle(view);

        redirectToRegisterPage(view);

        return view;
    }
}

