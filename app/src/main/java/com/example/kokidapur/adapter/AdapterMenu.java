//package com.example.kokidapur.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.kokidapur.R;
//import com.example.kokidapur.model.DataMenu;
//
//import java.util.List;
//
//public class AdapterMenu extends BaseAdapter {
//    private Activity activity;
//    private LayoutInflater inflater;
//    private List<DataMenu> lists;
//
//    public AdapterMenu(Activity activity, List<DataMenu> lists) {
//        this.activity = activity;
//        this.lists = lists;
//    }
//
//    @Override
//    public int getCount() {
//        return lists.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return lists.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (inflater == null){
//            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        if (convertView == null && inflater !=null){
//            convertView = inflater.inflate(R.layout.list_menu, null);
//        }
//
//        if (convertView !=null){
//            TextView nama_menu = convertView.findViewById(R.id.NamaMenu_List);
//
//            DataMenu dataMenu = lists.get(position);
//            nama_menu.setText(dataMenu.getNama_menu());
//        }
//
//        return convertView;
//    }
//}
