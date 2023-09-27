package com.example.kokidapur.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kokidapur.R;
import com.example.kokidapur.model.DataResepMenu;

import java.util.List;

public class AdapterMenuResep extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataResepMenu> resepMenuList;

    public AdapterMenuResep(Activity activity, List<DataResepMenu> resepMenuList) {
        this.activity = activity;
        this.resepMenuList = resepMenuList;
    }

    @Override
    public int getCount() {
        return resepMenuList.size();
    }

    @Override
    public Object getItem(int position) {
        return resepMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater ==null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null && inflater !=null){
            convertView = inflater.inflate(R.layout.list_menu_resep_layout, null);
        }

        if (convertView !=null){
            TextView nama_resep = convertView.findViewById(R.id.NamaResep_List);

            DataResepMenu dataResepMenu = resepMenuList.get(position);
            nama_resep.setText(dataResepMenu.getRecipe_name());
        }

        return convertView;
    }
}
