package com.example.eschoolapp.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eschoolapp.R;
import com.example.eschoolapp.model.Location;
import com.example.eschoolapp.model.Payment;

import java.util.List;

public class PaymentInfoAdapter extends RecyclerView.Adapter<PaymentInfoAdapter.ProgrammingViewHolder> {
    private List<Payment> paymentList;
    private Context mctx;


    public PaymentInfoAdapter(Context ctx,List<Payment> paymentList)
    {
        this.mctx = ctx;
        this.paymentList = paymentList;
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
        Payment payment = paymentList.get(position);

        //binding the data with the viewholder views
        holder.category.setText(payment.getContact());
        holder.location.setText(payment.getCompanyName()+"--License No"+payment.getLicense());
        holder.space.setText(payment.getPayment());

    }

    @Override
    public int getItemCount() {
        return paymentList.size();
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
