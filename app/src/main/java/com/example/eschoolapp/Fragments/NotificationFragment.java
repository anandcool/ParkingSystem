package com.example.eschoolapp.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.eschoolapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment implements AdapterView.OnItemClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView lv = view.findViewById(R.id.listview);
        String [] functionality = {"Send Feedback","Contact Us","Terms and Conditions","Privacy Policy","Pricing and Refund Policy","Reset Password","Logout"};
        ArrayAdapter<String> functionalityAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,functionality);
        lv.setAdapter(functionalityAdapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Dialog mDialog;
        String position1 = parent.getItemAtPosition(position).toString();
        mDialog = new Dialog(getActivity());
        mDialog.setContentView(R.layout.customdialog3);
        mDialog.show();
    }
}
