package com.shubham.rd.richdates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GirlProfileActivity extends AppCompatActivity {
String email="";
TextView tvName,tvAge,tvIntrests;
ImageView imgProfile;
Button btnClose;
android.support.v4.app.FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl_profile);
        Intent intent=getIntent();
        email=intent.getExtras().getString("Email");
        tvName=(TextView)findViewById(R.id.tvName);


        btnClose=(Button)findViewById(R.id.btnClose);
        imgProfile=(ImageView)findViewById(R.id.imgProfile);
        String url="http://signup.isslearnlabs.com/palloti/GetGirlProfile.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(GirlProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                try {
                   JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                   // Toast.makeText(GirlProfileActivity.this, "" + jsonArray, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String name = obj.getString("name");
                        String age = obj.getString("dob");
                        String intrest = obj.getString("intrests");
                        String image=obj.getString("image");
                        Log.d("INtrests",intrest);
                        Log.d("Image",image);
                        tvName.setText(name);

                           Glide.with(GirlProfileActivity.this).load("http://signup.isslearnlabs.com/palloti/Uploads/"+image).into(imgProfile);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(GirlProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GirlProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("Email",email);
                return params;
            }
        };
        MySingleton.getMyInstance(GirlProfileActivity.this).addToRequestQueue(stringRequest);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent1=new Intent(GirlProfileActivity.this,MainActivity.class);
               startActivity(intent1);
        }
        });
    }
}
