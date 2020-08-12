package com.example.eschoolapp.services;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eschoolapp.R;
import com.example.eschoolapp.model.Location;

import java.util.List;


public class ParkingInfoAdapter extends RecyclerView.Adapter<ParkingInfoAdapter.ProgrammingViewHolder> {
    private List<Location> locationList;
    private Context mctx;


    public ParkingInfoAdapter(Context ctx,List<Location> locationList)
    {
        this.mctx = ctx;
        this.locationList = locationList;
    }
    @NonNull
    @Override
    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.allparkinginfo,parent,false);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolder holder, int position) {
        Location location = locationList.get(position);

          //binding the data with the viewholder views
        holder.category.setText(location.getCategory());
        holder.location.setText(location.getLatitude()+","+location.getLongtitude());
       holder.space.setText(location.getSpace());

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder {
        ImageView imgicon;
        TextView location;
        TextView category;
        Button space;
        public ProgrammingViewHolder(View itemView) {
            super(itemView);
            imgicon=itemView.findViewById(R.id.imageView5);
            location=itemView.findViewById(R.id.location);
            category=itemView.findViewById(R.id.category);
            space=itemView.findViewById(R.id.space);
        }
    }
}


