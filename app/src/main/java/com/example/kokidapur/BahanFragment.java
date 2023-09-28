package com.example.kokidapur;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kokidapur.adapter.AdapterBahan;
import com.example.kokidapur.helper.Helper;
import com.example.kokidapur.model.DataBahan;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BahanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BahanFragment extends Fragment {
    ListView listView;
    AlertDialog.Builder dialog;
    List<DataBahan> listBahan = new ArrayList<>();
    AdapterBahan adapterBahan;
    Helper dbhelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BahanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BahanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BahanFragment newInstance(String param1, String param2) {
        BahanFragment fragment = new BahanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bahan, container, false);

        //pindah ke daftar belanja
        Toolbar toolbar = root.findViewById(R.id.toolbarBahan);
        toolbar.inflateMenu(R.menu.action_menu_shopping);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() ==R.id.item_daftar_belanja){
                    Intent intent = new Intent(getActivity(), BelanjaActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        dbhelper = new Helper(getActivity().getApplicationContext());
        FloatingActionButton fabBahan = (FloatingActionButton) root.findViewById(R.id.floatingBahan);
        fabBahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TambahBahanActivity.class);
                startActivity(intent);
            }
        });

        listView = root.findViewById(R.id.LV_ListBahan);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapterBahan = new AdapterBahan(getActivity(), listBahan);
        listView.setAdapter(adapterBahan);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String id_bahan = listBahan.get(position).getId_bahan();
                final String nama_bahan = listBahan.get(position).getNama_bahan();
                final String jumlah = listBahan.get(position).getJumlah();
                final String status = listBahan.get(position).getStatus();
                AlertDialog.Builder alertDB = new AlertDialog.Builder(getActivity());
                View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog_bahan,null);
                alertDB.setView(dialogView);
                AlertDialog alertDialog = alertDB.create();
                TextView dialogtitle = dialogView.findViewById(R.id.Title_dialog_bahan);
                ImageButton tk_belanja = dialogView.findViewById(R.id.BtnTK_Belanja);
                Button editbtn = dialogView.findViewById(R.id.BtnEdit_bahan);
                Button deletebtn = dialogView.findViewById(R.id.BtnHapus_bahan);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogtitle.setText(nama_bahan);

                tk_belanja.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbhelper.updateBelanja(Integer.parseInt(id_bahan), nama_bahan, jumlah);
                        listBahan.clear();
                        getDataBahan();
                        Toast.makeText(getActivity(), "Ditambahkan Ke Daftar Belanja", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });

                editbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), TambahBahanActivity.class);
                        intent.putExtra("id_bahan", id_bahan);
                        intent.putExtra("nama_bahan", nama_bahan);
                        intent.putExtra("status","ada");
                        startActivity(intent);
                        alertDialog.dismiss();
                    }
                });

                deletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbhelper.deleteBahan(Integer.parseInt(id_bahan));
                        listBahan.clear();
                        getDataBahan();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            }

        });
        getDataBahan();
        return root;
    }

    public void getDataBahan(){
        ArrayList<HashMap<String, String>> rows = dbhelper.getAllBahan();
        for (int i=0; i<rows.size(); i++){
            String id_bahan = rows.get(i).get("id_bahan");
            String nama_bahan = rows.get(i).get("nama_bahan");

            DataBahan dataBahan = new DataBahan();
            dataBahan.setId_bahan(id_bahan);
            dataBahan.setNama_bahan(nama_bahan);

            listBahan.add(dataBahan);
        }
        adapterBahan.notifyDataSetChanged();
    }

    public void onResume() {
        super.onResume();
        listBahan.clear();
        getDataBahan();
    }
}