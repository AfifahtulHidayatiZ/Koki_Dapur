package com.example.kokidapur.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kokidapur.R;
import com.example.kokidapur.TambahMenuActivity;

import java.util.ArrayList;

public class AdapterTanggal extends BaseAdapter {
    private Activity activity;
    LayoutInflater inflater;
    private ArrayList<String> days;
    private ArrayList<String> dates;

    public AdapterTanggal(Activity activity, ArrayList<String> days, ArrayList<String> dates) {
        this.activity = activity;
        this.days = days;
        this.dates = dates;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null && inflater != null){
            convertView = inflater.inflate(R.layout.list_menu_mingguini_layout, null);

        }

        TextView dayView = convertView.findViewById(R.id.Nama_hari);
        TextView dateView = convertView.findViewById(R.id.Tanggal);

        dayView.setText(days.get(position));
        dateView.setText(dates.get(position));

        //Agar list hari bisa di klik
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectionDay = days.get(position);
                String selectionDate = dates.get(position);
                Intent intent = new Intent(activity, TambahMenuActivity.class);
                intent.putExtra("selectionDay", selectionDay);
                intent.putExtra("selectionDate", selectionDate);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }
}
