package com.example.eschoolapp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eschoolapp.services.URL;

import java.util.HashMap;
import java.util.Map;

public class AddLocation extends AppCompatActivity {

    EditText latitude,longtitude;
    Spinner category,timing;
    Button btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        latitude = findViewById(R.id.latitude);
        longtitude = findViewById(R.id.longtitude);
        category = findViewById(R.id.category);
        timing = findViewById(R.id.timing);
        btnsubmit = findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latitude.getText().toString().isEmpty()) {
                    latitude.setError("Latitude can't be blank");
                } else if (longtitude.getText().toString().isEmpty()) {
                    longtitude.setError("Longtitude can't be blank");
                } else if (category.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(AddLocation.this, "Category must be selected", Toast.LENGTH_SHORT).show();
                } else if (timing.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(AddLocation.this, "Timing must be selected", Toast.LENGTH_SHORT).show();
                } else {
                    SaveLocation(Double.parseDouble(latitude.getText().toString()), Double.parseDouble(longtitude.getText().toString()), category.getSelectedItem().toString(), timing.getSelectedItem().toString());
                }
            }
        });

    }

    private void SaveLocation(final Double latitude, final Double longtitude, final String category, final String timing) {
         StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_ADDLOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddLocation.this, ""+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddLocation.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("latitude",""+latitude);
                params.put("longtitude",""+longtitude);
                params.put("category",category);
                params.put("timing",timing);
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