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

import java.util.List;

public class AdapterBahanPopup extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataBahan> listbahan;

    public AdapterBahanPopup(Activity activity, List<DataBahan> listbahan) {
        this.activity = activity;
        this.listbahan = listbahan;
    }


    @Override
    public int getCount() {
        return listbahan.size();
    }

    @Override
    public Object getItem(int position) {
        return listbahan.get(position);
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
            convertView = inflater.inflate(R.layout.list_popup_bahan_layout, null);
        }

        if (convertView !=null){
            TextView nama_bahan =convertView.findViewById(R.id.NamaBahan_ListPopUp);
            TextView status = convertView.findViewById(R.id.ListDetail_StatusPopUp);

            DataBahan dataBahan = listbahan.get(position);
            nama_bahan.setText(dataBahan.getNama_bahan());
            status.setText(dataBahan.getStatus());
        }

        return convertView;
    }
}
