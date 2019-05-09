package com.example.ed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ShopAdapter extends BaseAdapter {
    private ViewHolder viewHolder;
    private final Context mContext;
    private ArrayList <Shop> shopList;

    // 1
    public ShopAdapter(Context context, ArrayList <Shop> shopList) {
        this.mContext = context;
        this.shopList = shopList;
    }

    // 2
    @Override
    public int getCount() {
        return shopList.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return shopList.get(position);
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.shop_item, null);
            viewHolder = new ViewHolder();
            viewHolder.shop_image = convertView.findViewById(R.id.shop_image);
            viewHolder.shop_location = convertView.findViewById(R.id.shop_location);
            viewHolder.shop_name = convertView.findViewById(R.id.shop_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.shop_name.setText(shopList.get(position).getName());
        viewHolder.shop_location.setText(shopList.get(position).getLocation());

        DownloadImageFromInternet difi = new DownloadImageFromInternet(viewHolder.shop_image);
        difi.execute(shopList.get(position).getUrl_image());
//        viewHolder.shop_image.setImageDrawable(LoadImageFromWebOperations(shopList.get(position).getUrl_image()));
//        Log.e("testAdapter",shopList.get(position).getName());

        return convertView;
    }

    public class ViewHolder {
        ImageView shop_image;
        TextView shop_location;
        TextView shop_name;
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
//            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
