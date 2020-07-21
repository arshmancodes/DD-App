package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<HomeItem> homeItems;

    public ListAdapter(Context context, int layout, ArrayList<HomeItem> homeItems) {
        this.context = context;
        this.layout = layout;
        this.homeItems = homeItems;
    }

    @Override
    public int getCount() {
        return homeItems.size();
    }

    @Override
    public Object getItem(int position) {
        return homeItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder
    {
        ImageView img;
        TextView name, desc;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder = new ViewHolder();

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.name = (TextView) view.findViewById(R.id.itemtitle);
            holder.img = (ImageView) view.findViewById(R.id.listitem);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        HomeItem homeItem = homeItems.get(position);


       holder.name.setText(homeItem.getName());

        Picasso.get().load(homeItem.getURL()).into(holder.img);

//       byte[] homeimage = homeItem.getImage();
//      Bitmap bitmap = BitmapFactory.decodeByteArray(homeimage, 0, homeimage.length);
//      holder.img.setImageBitmap(bitmap);

        return view;
    }
}
