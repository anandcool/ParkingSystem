package com.example.eschoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    TextView forgotpassword;
    Dialog mDialog;
    EditText email,password;
    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()) {
                    email.setError("Email Can't be blank");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Password Can't be blank");
                }
                else if(email.getText().toString().equalsIgnoreCase("admin@gmail.com") && password.getText().toString().equalsIgnoreCase("admin@123") ){
                    Intent in = new Intent(getApplicationContext(),Dashboard.class);
                    startActivity(in);
                }
                else {
                    LoginUser(email.getText().toString(), password.getText().toString());
                }
            }
        });

    }

    private void LoginUser(final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LOGUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(in);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",""+email);
                params.put("password",""+password);
                return params; // {email: 'shivnag@gmail.com',pass:'124'}
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}