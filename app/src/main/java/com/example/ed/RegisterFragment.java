package com.example.ed;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterFragment extends Fragment {
    // define variable
    Button button_login;
    EditText name;
    EditText email;
    EditText password;
    EditText password_confirm;
    Button suggest;

    // define method
    public void loginHandle(View view) {
        name = view.findViewById(R.id.name_input);
        email = view.findViewById(R.id.email_input);
        password = view.findViewById(R.id.password_input);
        password_confirm = view.findViewById(R.id.password_confirm_input);
        button_login= view.findViewById(R.id.register_btn);


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pathUrl = "/registration";
                String params = "{\"name\":\"" + name.getText() + "\", \"email\":\"" + email.getText() + "\",\"password\":\"" + password.getText() + "\"}";
                RegisterRequest myregister = new RegisterRequest(pathUrl, params, v.getContext());
                myregister.execute();
            }
        });
    }


    public void redirectToRegisterPage (View v) {
        suggest = v.findViewById(R.id.redirect_to_login_page);
        suggest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

                ft.replace(R.id.main_content, new LoginFragment());
                ft.commit();
            }
        });
    }


    public void validate() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.register, container, false);

        suggest = view.findViewById(R.id.redirect_to_login_page);

//        SpannableString str = new SpannableString("Bạn đã có tài khoản ? Đăng nhập ngay !");
        SpannableString str = new SpannableString( getString(R.string.suggest_login));
        str.setSpan(new ForegroundColorSpan(Color.rgb(244, 67, 54)), 21, 31, 0);
        suggest.setText(str);

        loginHandle(view);

        redirectToRegisterPage(view);

        return view;
    }
}

class RegisterRequest extends AsyncTask {
    private String url;
    private String bodyParams;
    private ArrayList<Shop> shopList = new ArrayList<Shop>();
    private Context ctx;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String ipAddress = "http://192.168.1.10:5000";

    public RegisterRequest(String pathUrl, String params, Context ctx) {
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
                    Log.e("register",response.body().string());
                    changeListShop c = new changeListShop(ctx);
//                    c.showSuccess();

                } catch (Exception e) {
                    Log.e("err",e.getMessage());
                    changeListShop c = new changeListShop(ctx);
//                    c.showError();
                }
            }
        });

        return null;
    }

    class changeListShop {
        private final Handler handler;
        private GridView listShop;
        private  ShopAdapter shopAdapter;

        public changeListShop(Context context){
            handler = new Handler(context.getMainLooper());
        }

        public void showSuccess() {
            // Do work
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog alert = new AlertDialog.Builder(ctx.getApplicationContext()).setTitle("Success").show();
                }
            });
        }

        public void showError() {
            // Do work
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog alert = new AlertDialog.Builder(ctx.getApplicationContext()).setTitle("Error").show();
                }
            });
        }

        private void runOnUiThread(Runnable r) {
            handler.post(r);
        }
    }
}

