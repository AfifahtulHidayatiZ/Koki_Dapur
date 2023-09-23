package com.example.kokidapur.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kokidapur.R;
import com.example.kokidapur.model.DataMRB;

import java.util.List;

public class AdapterMRB extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataMRB> listMrb;

    public AdapterMRB(Activity activity, List<DataMRB> listMrb) {
        this.activity = activity;
        this.listMrb = listMrb;
    }

    @Override
    public int getCount() {
        return listMrb.size();
    }

    @Override
    public Object getItem(int position) {
        return listMrb.get(position);
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

        if (convertView == null && inflater !=null){
            convertView = inflater.inflate(R.layout.list_menu_layout, null);
        }

        if (convertView != null){
            TextView nama_menu = convertView.findViewById(R.id.NamaMenu_List);

            DataMRB dataMRB = listMrb.get(position);
            nama_menu.setText(dataMRB.getNama_menu());
        }
        return convertView;
    }
}
