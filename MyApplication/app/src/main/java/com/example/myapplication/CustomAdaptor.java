package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdaptor extends BaseAdapter {

    Context context;

    public CustomAdaptor(Context context, ArrayList<Thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Thoitiet> arrayList;

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup ) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.line_list_view,null);

        Thoitiet thoitiet = arrayList.get(i);
        TextView txtDay = view.findViewById(R.id.textviewNgay);
        TextView txtStatus = view.findViewById(R.id.status);
        TextView txtMaxTemp = view.findViewById(R.id.maxTemp);
        TextView txtMinTemp = view.findViewById(R.id.minTemp);
        ImageView imgStatus = (ImageView) view.findViewById(R.id.imageStatus);

        txtDay.setText(thoitiet.StrDay);
        txtStatus.setText(thoitiet.Status);
        txtMaxTemp.setText(thoitiet.maxTemp+"*c");
        txtMinTemp.setText(thoitiet.minTemp+"*c");

        Picasso.with(context).load("http://openweathermap.org/img/w/" + thoitiet.Image + ".png").into(imgStatus);

        return view;

    }
}
