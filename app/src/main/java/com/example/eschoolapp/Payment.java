package com.example.eschoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eschoolapp.model.Location;
import com.example.eschoolapp.services.ParkingInfoAdapter;
import com.example.eschoolapp.services.PaymentInfoAdapter;
import com.example.eschoolapp.services.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Payment extends AppCompatActivity {


    List<com.example.eschoolapp.model.Payment> paymentList;
    RecyclerView rv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        rv1 = findViewById(R.id.rv1);
        rv1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        paymentList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL.URL_PAYMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("alllocation");
                    Toast.makeText(Payment.this, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();

                    for(int i=0;i<jsonArray.length();i++){
                        Log.d("loopkitni",""+i);
                        JSONObject obj = jsonArray.getJSONObject(i);
                        final com.example.eschoolapp.model.Payment payment = new com.example.eschoolapp.model.Payment(obj.getString("companyName"),obj.getString("license"),obj.getString("contact"),obj.getString("payment"));
                        paymentList.add(payment);
                        rv1.setAdapter(new PaymentInfoAdapter(getApplicationContext(),paymentList));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Payment.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params; // {email: 'shivnag@gmail.com',pass:'124'}
            }
        };
        //10000 is the time in milliseconds adn is equal to 10 sec
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}