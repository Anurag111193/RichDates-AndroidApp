package com.shubham.rd.richdates;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment2 extends Fragment {

private ViewPager mpager;
private CardStackAdapter mAdapter;
private ArrayList<String> emailList;
    String url = "http://192.168.0.6/richdatesapp/GetUserData.php";
    public HomeFragment2() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_fragment2, container, false);





        mpager=(ViewPager)view.findViewById(R.id.viewPager);
        ArrayList<String> data=  getData();
        mAdapter=new CardStackAdapter(getContext(), data);

//        mpager.setPageTransformer(true,new CardStackTransformer());


        mpager.setAdapter(mAdapter);

        return view;
    }
    public ArrayList<String> getData()
    {
        emailList=new ArrayList<String>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    //  Toast.makeText(MapsActivity.this, "" + jsonArray, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String email=obj.getString("email");
                        emailList.add(email);
                        Log.d("Email",email);
                        Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getMyInstance(getContext()).addToRequestQueue(stringRequest);
        return emailList;
    }
//    private class CardStackTransformer implements ViewPager.PageTransformer {
//        @Override
//        public void transformPage(View page, float position) {
//            if(position>=0)
//            {
//                page.setScaleX(0.8f-0.02f*position);
//                page.setScaleY(0.8f);
//                page.setTranslationX(-page.getWidth()*position);
//                page.setTranslationY(30*position);
//            }
//        }
//    }
}
