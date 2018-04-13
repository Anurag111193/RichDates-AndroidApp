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
public class NotificationsFragment extends Fragment {
    RecyclerView recyclerView;
    String yourName,yourEmail;
    RecyclerView.Adapter adapter;
   LinearLayoutManager layoutManager;
    ArrayList<Courses> list;
    ArrayList<String> myEmailList;
    ArrayList<String> yourEmailList;
    private List<Notifications> notificationsList;
    private List<NotificationsGirl> notificationsGirlList;

//    int[] imageId={R.drawable.android_image,R.drawable.php_image,R.drawable.net_image,R.drawable.android_image
//            ,R.drawable.android_image,R.drawable.android_image,R.drawable.android_image,R.drawable.android_image};
//
String[] courseName;
    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_notifications, container, false);
       notificationsList=new ArrayList<>();
         yourName = this.getArguments().getString("Name");
        String contact = this.getArguments().getString("Contact");
         final String email1 = this.getArguments().getString("Email");
         yourEmail=email1;
        //Toast.makeText(getContext(), email1, Toast.LENGTH_SHORT).show();
        String dob = this.getArguments().getString("Dob");
       final String gender = this.getArguments().getString("Gender");
       myEmailList=new ArrayList<>();
       yourEmailList=new ArrayList<>();
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        String url="";
        if(gender.equals("Female"))
        {
            url=Sample.myIp+"richdatesapi/SelectNotificationGirl.php";
        }
        else if(gender.equals("Male"))
        {
            url=Sample.myIp+"richdatesapi/SelectNotification.php";
        }

       StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
 // Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
if(gender.equals("Male"))
{
    JSONObject jsonObject = null;
    try {
        jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        //  Toast.makeText(MapsActivity.this, "" + jsonArray, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String myName = obj.getString("myName");
            String myEmail = obj.getString("myEmail");
           // Toast.makeText(getContext(), myName + myEmail, Toast.LENGTH_SHORT).show();
            Notifications notifications = new Notifications(
              myEmail,myName,yourEmail,yourName
        );
       notificationsList.add(notifications);
        }
        adapter=new CoursesAdapter(getContext(),notificationsList);
                       recyclerView.setAdapter(adapter);
    } catch (JSONException e) {
        e.printStackTrace();
    }
}
else if(gender.equals("Female"))
{
    JSONObject jsonObject = null;
    try {
        jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        //  Toast.makeText(MapsActivity.this, "" + jsonArray, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String myName = obj.getString("myName");

            //Toast.makeText(getContext(), myName , Toast.LENGTH_SHORT).show();
            NotificationsGirl notificationsGirl = new NotificationsGirl(
                    myName
            );
            //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            notificationsGirlList=new ArrayList<>();
           notificationsGirlList.add(notificationsGirl);
        }
        adapter=new CoursesAdapterGirl(getContext(),notificationsGirlList);
        recyclerView.setAdapter(adapter);
    } catch (JSONException e) {
        e.printStackTrace();
    }
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

               params.put("Email",email1);
               return params;
           }
       };
      MySingleton.getMyInstance(getContext()).addToRequestQueue(stringRequest);



        return view;
    }

}
