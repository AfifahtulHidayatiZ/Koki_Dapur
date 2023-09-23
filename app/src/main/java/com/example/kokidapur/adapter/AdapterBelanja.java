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

import org.w3c.dom.Text;

import java.util.List;

public class AdapterBelanja extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataBahan> listbelanja;

    public AdapterBelanja(Activity activity, List<DataBahan> listbelanja) {
        this.activity = activity;
        this.listbelanja = listbelanja;
    }

    @Override
    public int getCount() {
        return listbelanja.size();
    }

    @Override
    public Object getItem(int position) {
        return listbelanja.get(position);
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
            convertView = inflater.inflate(R.layout.list_belanja_layout, null);
        }
        if (convertView != null){
            TextView nama_bahan = convertView.findViewById(R.id.Namabelanja_list);
            TextView jumlah = convertView.findViewById(R.id.Jumlahbelanja_list);

            DataBahan dataBahan = listbelanja.get(position);

            if (nama_bahan !=null){
                nama_bahan.setText(dataBahan.getNama_bahan());
            }

            if (jumlah !=null){
                jumlah.setText(dataBahan.getJumlah());
            }
        }
        return convertView;
    }
}
