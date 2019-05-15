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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ShopDetailAdapter extends BaseAdapter {
    private ViewHolder viewHolder;
    private final Context mContext;
    private ArrayList<ShopDetail> listShopDetail;
//    private ArrayList <Shop> shopList;

    // 1
    public ShopDetailAdapter(Context context, ArrayList<ShopDetail> listShopDetail) {
        this.mContext = context;
        this.listShopDetail = listShopDetail;
    }

    // 2
    @Override
    public int getCount() {
        return listShopDetail.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return listShopDetail.get(position);
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.shop_detail, null);
            viewHolder = new ViewHolder();
            viewHolder.shop_image = convertView.findViewById(R.id.shop_image);
            viewHolder.shop_location = convertView.findViewById(R.id.location);
            viewHolder.sort_title = convertView.findViewById(R.id.sort_title);
            viewHolder.like = convertView.findViewById(R.id.like);
            viewHolder.shop_name = convertView.findViewById(R.id.shop_name);
            viewHolder.type = convertView.findViewById(R.id.shop_type);
//            viewHolder.shop_cuisine = convertView.findViewById(R.id.shop_cuisine);
//            viewHolder.product_grid = convertView.findViewById(R.id.product_grid);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DownloadImageFromInternet difi = new DownloadImageFromInternet(viewHolder.shop_image);
        difi.execute(listShopDetail.get(position).getUrl_image());

        viewHolder.shop_location.setText(listShopDetail.get(position).getLocation());
        viewHolder.sort_title.setText(listShopDetail.get(position).getSort_title());
        viewHolder.like.setText(listShopDetail.get(position).getLikes());
        viewHolder.shop_name.setText(listShopDetail.get(position).getName());
        viewHolder.type.setText(listShopDetail.get(position).getType());
        viewHolder.shop_cuisine.setText(listShopDetail.get(position).getCuisine());
//        viewHolder.product_grid.setText(shopDetail.getLocation());


//        viewHolder.shop_image.setImageDrawable(LoadImageFromWebOperations(shopList.get(position).getUrl_image()));
//        Log.e("testAdapter",shopList.get(position).getName());

        return convertView;
    }

    public class ViewHolder {
        ImageView shop_image;
        TextView shop_location;
        TextView sort_title;
        TextView like;
        TextView shop_name;
        TextView type;
        TextView shop_cuisine;
        GridView product_grid;
    }

//    public static Drawable LoadImageFromWebOperations(String url) {
//        try {
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");
//            return d;
//        } catch (Exception e) {
//            return null;
//        }
//    }

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
