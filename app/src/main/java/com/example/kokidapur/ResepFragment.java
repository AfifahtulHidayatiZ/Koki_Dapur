package com.example.kokidapur;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kokidapur.adapter.Adapter;
import com.example.kokidapur.helper.Helper;
import com.example.kokidapur.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResepFragment extends Fragment {
    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> lists = new ArrayList<>();
    Adapter adapter;
    Helper dbhelper;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResepFragment newInstance(String param1, String param2) {
        ResepFragment fragment = new ResepFragment();
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_resep, container, false);

        dbhelper = new Helper(getActivity().getApplicationContext());
        FloatingActionButton fabResep = (FloatingActionButton) root.findViewById(R.id.floatingResep);
        fabResep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TambahResepActivity.class);
                startActivity(intent);
            }
        });

        listView = root.findViewById(R.id.LV_ListResep);
        adapter = new Adapter(getActivity(), lists);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                final String id = lists.get(position).getId();
                final String recipe_name = lists.get(position).getRecipe_name();
                final String material_name = lists.get(position).getMaterial_name();
                final String instruction = lists.get(position).getInstruction();
                Intent intent = new Intent(getActivity(), LihatResepActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("recipe_name", recipe_name);
                intent.putExtra("material_name", material_name);
                intent.putExtra("instruction", instruction);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                final String id = lists.get(position).getId();
                final String recipe_name = lists.get(position).getRecipe_name();
                final String material_name = lists.get(position).getMaterial_name();
                final String instruction = lists.get(position).getInstruction();
                final CharSequence[] dialoguItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(getActivity());
                dialog.setItems(dialoguItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent = new Intent(getActivity(),TambahResepActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("recipe_name", recipe_name);
                                intent.putExtra("material_name", material_name);
                                intent.putExtra("instruction", instruction);
                                startActivity(intent);
                                break;
                            case 1:
                                dbhelper.delete(Integer.parseInt(id));
                                lists.clear();
                                getData();
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
        getData();
        return root;
    }

    private void getData(){
        ArrayList<HashMap<String, String>> rows = dbhelper.getAll();
        for (int i = 0; i<rows.size(); i++){
            String id = rows.get(i).get("id");
            String recipe_name = rows.get(i).get("recipe_name");
            String material_name = rows.get(i).get("material_name");
            String instruction = rows.get(i).get("instruction");

            Data data = new Data();
            data.setId(id);
            data.setRecipe_name(recipe_name);
            data.setMaterial_name(material_name);
            data.setInstruction(instruction);

            lists.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        lists.clear();
        getData();
    }
}