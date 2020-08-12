package com.example.eschoolapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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


public class Signup extends AppCompatActivity {

    EditText fname,lname,email,pno,pass;
    Button btnsubmit,btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        pno = findViewById(R.id.pno);
        btnsubmit =findViewById(R.id.btnsubmit);
        btnlogin = findViewById(R.id.btnlogin);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().isEmpty()) {
                    fname.setError("First Name Can't be blank");
                } else if (lname.getText().toString().isEmpty()) {
                    lname.setError("Last Name Can't be blank");
                } else if (email.getText().toString().isEmpty()) {
                    email.setError("Email can't be blank");
                } else if (pass.getText().toString().isEmpty()) {
                    pass.setError("Password can't be blank");
                } else if (pno.getText().toString().isEmpty()) {
                    pno.setError("Phone Number can't be blank");
                } else if (pno.getText().toString().length() != 10) {
                    pno.setError("Phone Number must be 10digit");
                } else {
                    AddUser(fname.getText().toString(), lname.getText().toString(), email.getText().toString(), pass.getText().toString(), pno.getText().toString());
                }
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Signup.this,MainActivity.class);
                startActivity(in);
            }
        });
    }

    private void AddUser(final String fname, final String lname, final String email,final String pass, final String pno) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_ADDUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Signup.this, ""+response, Toast.LENGTH_SHORT).show();
                Log.d("respsonse",""+response);
                Intent in = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(in);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Signup.this, "err"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",""+error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("email",email);
                params.put("pass",pass);
                params.put("pno",pno);
//                Toast.makeText(Signup.this, "yy"+params, Toast.LENGTH_SHORT).show();
                Log.d("kyahua",""+params);
                return params; // {email: 'shivnag@gmail.com',pass:'124'}
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
        requestQueue.add(stringRequest);
    }

}