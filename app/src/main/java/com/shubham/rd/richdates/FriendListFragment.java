package com.shubham.rd.richdates;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendListFragment extends Fragment {

RecyclerView recyclerView;
RecyclerView.Adapter adapter;
RecyclerView.LayoutManager layoutManager;
private String email,gender;
    private List<FriendList> friendListArray;
    public FriendListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_friend_list, container, false);
friendListArray=new ArrayList<>();
        String contact = this.getArguments().getString("Contact");
         email = this.getArguments().getString("Email");

       // Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();
        //String dob = this.getArguments().getString("Dob");
        gender = this.getArguments().getString("Gender");
      //  Toast.makeText(getContext(), gender, Toast.LENGTH_SHORT).show();
        getFriendList();
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_friend_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }
public void getFriendList()
{
    String url=Sample.myIp+"richdatesapi/SelectFriendList.php";


    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
           // Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                //  Toast.makeText(MapsActivity.this, "" + jsonArray, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String name = obj.getString("name");
                    String imageName = obj.getString("image");
                   // Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    FriendList friendList=new FriendList(name,imageName);
                    friendListArray.add(friendList);
                }
adapter=new FriendListAdapter(getContext(),friendListArray);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
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
            params.put("Gender",gender);
            return params;
        }
    };
    MySingleton.getMyInstance(getContext()).addToRequestQueue(stringRequest);
}
}
