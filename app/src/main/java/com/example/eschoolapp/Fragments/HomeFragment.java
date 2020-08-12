package com.example.eschoolapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.eschoolapp.CheckTicket;
import com.example.eschoolapp.AddLocation;
import com.example.eschoolapp.AllLocation;
import com.example.eschoolapp.Payment;
import com.example.eschoolapp.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageSlider img = view.findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1573348722427-f1d6819fdf98?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=667&q=80","1 Image"));
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1590674899484-d5640e854abe?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=747&q=80","2 Image"));
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1509835526446-530ddef363bd?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=376&q=80","3 Image"));
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1559997568-c50cb1ea1dfb?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1575&q=80","4 Image"));
        img.setImageList(slideModels,true);
        CardView payment= view.findViewById(R.id.payment);
        CardView addLocation = view.findViewById(R.id.expenses);
        CardView showlocation = view.findViewById(R.id.showlocation);
        CardView check = view.findViewById(R.id.check);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AddLocation.class);
                startActivity(in);
            }
        });
        showlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AllLocation.class);
                startActivity(in);
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), CheckTicket.class);
                startActivity(in);
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent in = new Intent(getActivity(), Payment.class);
            startActivity(in);
            }
        });
    }

}
