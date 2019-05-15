package com.example.ed;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class UserFragment extends Fragment {

    // define variable
    GridView favourite_shop;
    Button logout;

    // define method
    public void initFavouriteShop(View v) {
        Context ctx = v.getContext();
        int user_id = ((MyApplication) ((Activity) ctx).getApplication()).getCurrentUserId();

        String pathUrl = "/get_favourite_shop";
        String params = "{\"users_id\":"+ user_id +"}";

        GetAllShopRequest get_all_shop = new GetAllShopRequest(pathUrl,params, ctx, favourite_shop);
        get_all_shop.execute();
    }

    public void initLogout(View v) {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = v.getContext();
                ((MyApplication) ((Activity) v.getContext()).getApplication()).setUserId(-1);
                logoutUpdateUI l = new logoutUpdateUI(ctx);
                l.goLogin();
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.user, container, false);

        favourite_shop = view.findViewById(R.id.favourite_shop);
        logout = view.findViewById(R.id.button__logout);
        initFavouriteShop(view);
        initLogout(view);

        favourite_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(UserFragment.this.getActivity(), ShopDetailFragment.class);
                intent.putExtra("shop_id", Integer.parseInt(GetAllShopRequest.shopList.get(position).getShop_id()) );
                startActivity(intent);
//                Log.e("errrr", "asdasdsadasda");
            }
        });

        return view;
    }


    class logoutUpdateUI {
        private final Handler handler;
        Context ctx;

        public logoutUpdateUI(Context context){
            this.ctx = context;
            handler = new Handler(context.getMainLooper());
        }

        public void goLogin() {
            // Do work
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BottomNavigationView navView = ((Activity) ctx).findViewById(R.id.my_toolbar);
                    Menu menu = navView.getMenu();

                    menu.findItem(R.id.user).setVisible(false);
                    menu.findItem(R.id.menu_login).setVisible(true);
                    navView.setSelectedItemId(R.id.user);

                    final Activity activity = (Activity) ctx;
                    FragmentManager transaction = activity.getFragmentManager();
                    FragmentTransaction ft = transaction.beginTransaction();
                    ft.replace(R.id.main_content, new LoginFragment());
                    ft.commit();
                }
            });
        }

        private void runOnUiThread(Runnable r) {
            handler.post(r);
        }
    }
}
