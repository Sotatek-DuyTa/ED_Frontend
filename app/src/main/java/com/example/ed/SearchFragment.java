package com.example.ed;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class SearchFragment extends Fragment {

    // define variable
    GridView listShop;
    EditText key_word;
    Button search_btn;
    TextView messa_text;

    // define method
    public void searchHandle(View v) {
        search_btn = v.findViewById(R.id.search_button);
        key_word = v.findViewById(R.id.search_input);
        messa_text = v.findViewById(R.id.messa_text);
        messa_text.setVisibility(View.INVISIBLE);
        listShop = v.findViewById(R.id.list_shop);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("key word:" + key_word.getText());
                String pathUrl = "/find_name";
                String params = "{\"name\":\"" + key_word.getText() + "\"}";

//                messa_text.setVisibility(View.INVISIBLE);
                GetAllShopRequest get_all_shop = new GetAllShopRequest(pathUrl, params, v.getContext(), listShop);
                get_all_shop.execute();
            }
        });

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.search, container, false);

        searchHandle(view);

        return view;
    }
}
