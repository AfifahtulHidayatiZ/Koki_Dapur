package com.example.kokidapur.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kokidapur.R;
import com.example.kokidapur.model.DataBahan;
import com.example.kokidapur.model.DataBahanMenu;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterMenuBahan extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataBahanMenu> listMenuBahan;

    public AdapterMenuBahan(Activity activity, List<DataBahanMenu> listMenuBahan) {
        this.activity = activity;
        this.listMenuBahan = listMenuBahan;
    }

    @Override
    public int getCount() {
        return listMenuBahan.size();
    }

    @Override
    public Object getItem(int position) {
        return listMenuBahan.get(position);
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
            convertView = inflater.inflate(R.layout.list_detail_menu_bahan_layout, null);
        }

        if (convertView != null){
            TextView nama_bahan = convertView.findViewById(R.id.ListDetail_Bahan);
            TextView status = convertView.findViewById(R.id.ListDetail_Status);

            DataBahanMenu dataBahan = listMenuBahan.get(position);
            nama_bahan.setText(dataBahan.getNama_bahan());
            status.setText(dataBahan.getStatus());
        }
        return convertView;
    }
}
