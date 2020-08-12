package com.example.eschoolapp;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener{

    private GoogleMap mMap;
    public LatLng currentLocation;
    public String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.SEND_SMS},0);
        ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);
        LocationManager lm =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Sorry GPS is Off", Toast.LENGTH_SHORT).show();
        }else{

            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5,0, (LocationListener) this);
            Toast.makeText(this, "GPS is Working", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GetAllLocation();

    }


    @Override
    public void onLocationChanged(Location location) {
        currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
        Toast.makeText(this, "current"+currentLocation.longitude, Toast.LENGTH_SHORT).show();
        if(currentLocation == null){
            Toast.makeText(this, "Please Wait GPS is Working", Toast.LENGTH_SHORT).show();
        }

    }

    private void GetAllLocation() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL.URL_ALLLOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("alllocation");
                    Toast.makeText(MapsActivity.this, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();

                    for(int i=0;i<jsonArray.length();i++){
                        Log.d("loopkitni",""+i);
                        JSONObject obj = jsonArray.getJSONObject(i);
                        final com.example.eschoolapp.model.Location location = new com.example.eschoolapp.model.Location(obj.getString("latitude"),obj.getString("longtitude"),obj.getString("category"),obj.getString("timing"),obj.getString("space"));
                        try {
                            Geocoder gc = new Geocoder(MapsActivity.this);
                            ArrayList<Address> al  = (ArrayList) gc.getFromLocation(Double.parseDouble(location.getLatitude()),Double.parseDouble(location.getLongtitude()),1);
                            android.location.Address ad = al.get(0);
                            str = ad.getSubLocality() + "," + ad.getLocality();
                            LatLng sydney = new LatLng(Double.parseDouble(location.getLatitude()),Double.parseDouble(location.getLongtitude()));
                            if(location.getSpace().equalsIgnoreCase("Accquired")){
                                Log.d("kyahuabey","hel");
                                Circle circle = mMap.addCircle(new CircleOptions() // make round color at police station area
                                        .center(new LatLng(Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongtitude())))
                                        .radius(200)
                                        .strokeColor(Color.RED)
                                        .fillColor(Color.RED));
                            }
                            else if(location.getSpace().equalsIgnoreCase("Vaccant")) {
                                 mMap.addCircle(new CircleOptions() // make round color at police station area
                                        .center(new LatLng(Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongtitude())))
                                        .radius(50)
                                        .strokeColor(Color.GREEN)
                                        .fillColor(Color.GREEN));
                                mMap.addMarker(new MarkerOptions().position(sydney).title("" + str));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20));
                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        Log.d("merilocation",""+location.getSpace());
                                        Toast.makeText(MapsActivity.this, "toast hua !!!", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(MapsActivity.this, Booking.class);
                                        in.putExtra("location", location);
                                        startActivity(in);
                                        return false;
                                    }



                                });

                            }

                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "msg"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapsActivity.this, "err"+error, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}