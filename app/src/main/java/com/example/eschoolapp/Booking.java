package com.example.eschoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.eschoolapp.services.URL;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Booking extends AppCompatActivity implements PaymentResultListener {

    Dialog mDialog;
    EditText companyName,license,contact;
    Spinner category,timing;
    final String[] ImagePath = new String[1];
    final Uri[] URI = new Uri[1];
     Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Intent intent = getIntent();
        location = intent.getExtras().getParcelable("location");
        companyName = findViewById(R.id.companyName);
        license = findViewById(R.id.license);
        contact = findViewById(R.id.contact);
        category = findViewById(R.id.category);
        timing = findViewById(R.id.timing);
        Button btnnext = findViewById(R.id.btnsubmit);
        mDialog = new Dialog(this);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (companyName.getText().toString().isEmpty()) {
                    companyName.setError("Company Name Can't be blank");
                } else if (license.getText().toString().isEmpty()) {
                    license.setError("License Number Can't be blank");
                } else if (contact.getText().toString().isEmpty()) {
                    contact.setError("Contact Number can't be blank");
                } else if (timing.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(Booking.this, "Timing must be selected", Toast.LENGTH_SHORT).show();
                } else if (category.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(Booking.this, "Category must be selected", Toast.LENGTH_SHORT).show();
                } else {
                    mDialog.setContentView(R.layout.customdialog);
                    mDialog.show();
                    Button customdialogbtn = mDialog.findViewById(R.id.confirmotpbtn);
                    customdialogbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.cancel();
                            startPayment();
                        }
                    });
                }
            }
        });
    }
    public void startPayment () {

        Checkout checkout = new Checkout();  /**   * Set your logo here   */
        checkout.setKeyID("rzp_test_k3OFACxp2Yg4Br");	/**   * Instantiate Checkout   */
        final Activity activity = this;  /**   * Pass your payment options to the Razorpay Checkout as a JSONObject   */
        try {
            JSONObject options = new JSONObject();      /**      * Merchant Name      * eg: ACME Corp || HasGeek etc.      */
            options.put("name", "Merchant Name");      /**      * Description can be anything      * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.      *     Invoice Payment      *     etc.      */
            options.put("description", "Reference No. #123456");
            options.put("currency", "INR");      /**      * Amount is always passed in currency subunits      * Eg: "500" = INR 5.00      */
            options.put("amount", "50000");
            checkout.open(activity, options);
        }
        catch(Exception e){
            Log.e("something", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(this, "Payment is done"+s, Toast.LENGTH_SHORT).show();
        String text = location.getLatitude(); // make text value is equal to seat_number
        if(text!=null){
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = null;
            try {
                bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,1000,1500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//                iv.setImageBitmap(bitmap);
                ImagePath[0] = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "demo_image", "demo_image");
                URI[0] = Uri.parse(ImagePath[0]);
                Toast.makeText(getApplicationContext(), "Image Saved Successfully", Toast.LENGTH_LONG).show();
                AddBooking(companyName.getText().toString(),license.getText().toString(),contact.getText().toString(),category.getSelectedItem().toString(),timing.getSelectedItem().toString(),"200",location.getLatitude(),location.getLongtitude());
            }
            catch (Exception ex){
                Toast.makeText(getApplicationContext(), ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void AddBooking(final String companyName, final String license, final String contact, final String category, final String timing, final String payment,final String latitude,final String longtitude) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_BOOKED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Booking.this, ""+response, Toast.LENGTH_SHORT).show();
//                Intent in = new Intent(getApplicationContext(),MapsActivity.class);
//                startActivity(in);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Booking.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("companyName",companyName);
                params.put("license",license);
                params.put("contact",contact);
                params.put("category",category);
                params.put("timing",timing);
                params.put("payment",payment);
                params.put("latitude",latitude);
                params.put("longtitude",longtitude);
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
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Some Error occur"+s, Toast.LENGTH_SHORT).show();
    }
}