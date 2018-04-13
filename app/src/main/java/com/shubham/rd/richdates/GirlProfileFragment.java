package com.shubham.rd.richdates;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class GirlProfileFragment extends Fragment {

    TextView tvName,tvAge,tvIntrests;
    ImageView imgProfile;
    Button btnClose;
    String email="";

    public GirlProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_girl_profile, container, false);
         email = this.getArguments().getString("Email");
        tvName=(TextView)view.findViewById(R.id.tvName);
        imgProfile=(ImageView)view.findViewById(R.id.imgProfile);
        String url=Sample.myIp+"richdatesapi/GetGirlProfile.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                  //  Toast.makeText(getContext(), "" + jsonArray, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String name = obj.getString("name");
                        String age = obj.getString("dob");
                        String intrest = obj.getString("intrests");
                        String image=obj.getString("image");
                        Log.d("INtrests",intrest);
                        Log.d("Image",image);
                        tvName.setText(name);

                        Glide.with(getContext()).load(Sample.myIp+"richdatesapi/Uploads/"+image).into(imgProfile);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("Email",email);
                return params;
            }
        };
        MySingleton.getMyInstance(getContext()).addToRequestQueue(stringRequest);
        return view;
    }

}
