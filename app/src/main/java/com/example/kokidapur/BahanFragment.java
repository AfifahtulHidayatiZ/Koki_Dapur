package com.example.kokidapur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
        //pindah halaman belanja
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bahan, container, false);

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
        adapterBahan = new AdapterBahan(getActivity(), listBahan);
        listView.setAdapter(adapterBahan);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String id_bahan = listBahan.get(position).getId_bahan();
                final String nama_bahan = listBahan.get(position).getNama_bahan();
                final String status = listBahan.get(position).getStatus();
                final CharSequence[] dialogItem = {"Edit", "Hapus","Tambah Keranjang"};
                dialog = new AlertDialog.Builder(getActivity());
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent = new Intent(getActivity(), TambahBahanActivity.class);
                                intent.putExtra("id_bahan", id_bahan);
                                intent.putExtra("nama_bahan", nama_bahan);
                                intent.putExtra("status","ada");
                                startActivity(intent);
                                break;
                            case 1:
                                dbhelper.deleteBahan(Integer.parseInt(id_bahan));
                                listBahan.clear();
                                getDataBahan();
                                break;
                            case 2:
                                dbhelper.updateBelanja(Integer.parseInt(id_bahan), nama_bahan);
                                listBahan.clear();
                                getDataBahan();
                                Intent intenbelanja = new Intent(getActivity(), BelanjaActivity.class);
                                startActivity(intenbelanja);
                        }
                    }
                }).show();
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


    //pindah ke halaman belanja
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_menu_shopping, menu); //toolbar menu pada file menu
        super.onCreateOptionsMenu(menu, inflater);
    }

    //back
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_daftar_belanja){
            Intent intent = new Intent(getActivity(), BelanjaActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}