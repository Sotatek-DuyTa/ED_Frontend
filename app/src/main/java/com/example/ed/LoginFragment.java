package com.example.ed;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                String pathUrl = "/login";
                String params = "{\"email\":\"" + email.getText() + "\",\"password\":\""+ password.getText() +"\"}";
//
                LoginRequest login = new LoginRequest(pathUrl, params, v.getContext());
                login.execute();
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

class LoginRequest extends AsyncTask {
    private String url;
    private String bodyParams;
    private ArrayList<Shop> shopList = new ArrayList<Shop>();
    private Context ctx;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String ipAddress = "http://35.198.228.3:5000";

    public LoginRequest(String pathUrl, String params, Context ctx) {
        this.url = this.ipAddress + pathUrl;
        this.bodyParams = params;
        this.ctx = ctx;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        OkHttpClient client = new OkHttpClient();

        final RequestBody body = RequestBody.create(JSON, bodyParams);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONObject res = new JSONObject(response.body().string());
                    int user_id = res.getInt("users_id");

                    Activity activity = (Activity) ctx;
                    ((MyApplication) activity.getApplication()).setUserId(user_id);

                    System.out.println(user_id);
//                    String s = ((MyApplication) ((Activity) ctx).getApplication()).getUserId();

                    afterLogin a = new afterLogin(ctx);
                    a.updateUI();

                } catch (Exception e) {
                    Log.e("err",e.getMessage());
                }
            }
        });

        return null;
    }

    class afterLogin {
        private final Handler handler;
        Context ctx;

        public afterLogin(Context context){
            this.ctx = context;
            handler = new Handler(context.getMainLooper());
        }

        public void updateUI() {
            // Do work
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BottomNavigationView navView = ((Activity) ctx).findViewById(R.id.my_toolbar);
                    Menu menu = navView.getMenu();

                    menu.findItem(R.id.user).setVisible(true);
                    menu.findItem(R.id.menu_login).setVisible(false);
                    navView.setSelectedItemId(R.id.user);
                }
            });
        }

        private void runOnUiThread(Runnable r) {
            handler.post(r);
        }
    }
}

