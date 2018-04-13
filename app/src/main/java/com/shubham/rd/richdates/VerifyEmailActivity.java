package com.shubham.rd.richdates;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class VerifyEmailActivity extends Activity {
    EditText etOtp;
    Button btnVerify;
    ProgressBar progressBar;
    String name,email,contact,dob,gender,password,otp;
    Bitmap bitmap;
    String url=Sample.myIp+"richdatesapi/UserRegistration.php";
    IntroManager introManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getColor(R.color.cardview_dark_background));
            }

        }
        introManager=new IntroManager(this);
       final Intent intent=getIntent();
       name=intent.getExtras().getString("Name");
       email= intent.getExtras().getString("Email");
        contact=intent.getExtras().getString("Contact");
        dob=intent.getExtras().getString("Dob");
        gender=intent.getExtras().getString("Gender");
        password=intent.getExtras().getString("Password");
        otp=intent.getExtras().getString("Otp");
        etOtp = (EditText) findViewById(R.id.etOtp);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etOtp.getText().toString().equals(""))
                {
                    etOtp.setError("Enter Otp");
                }
else if(otp.equals(etOtp.getText().toString())) {


    final ProgressDialog progressDialog = new ProgressDialog(VerifyEmailActivity.this);
    progressDialog.setTitle("Message from Server");
    progressDialog.setMessage("Please Wait");
    progressDialog.show();


    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            progressDialog.dismiss();
            Toast.makeText(VerifyEmailActivity.this, response, Toast.LENGTH_SHORT).show();
            if(response.equals("Registered"))
            {


                Intent intent1=new Intent(VerifyEmailActivity.this,LoginActivity.class);
                startActivity(intent1);
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(VerifyEmailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("Name", name);
            params.put("Email", email);
            params.put("Dob", dob);
            params.put("Contact", contact);
            params.put("Password", password);
            params.put("Gender", gender);
            if (gender.equals("Female")) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl_avatar);
                params.put("Image", ImageToString(bitmap));
            } else if (gender.equals("Male")) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boy_avatar);
                params.put("Image", ImageToString(bitmap));
            }

            // Log.d("ImageTOString",ImageToString(bitmap));
            return params;
        }

    };

    MySingleton.getMyInstance(VerifyEmailActivity.this).addToRequestQueue(stringRequest);
}
else
{
    Toast.makeText(VerifyEmailActivity.this, "Entered Otp is Incorrect", Toast.LENGTH_SHORT).show();
}

            }
        });
    }
    private String ImageToString(Bitmap bitmap) {
        bitmap = Bitmap.createScaledBitmap(bitmap, 800, 600, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
}