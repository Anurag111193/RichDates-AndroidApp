package com.shubham.rd.richdates;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

/**
 * Created by DELL on 8/7/2017.
 */

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {

    List<Notifications> notifications = new ArrayList<Notifications>();
    Context context;
android.support.v4.app.FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
String gender="";
String yourName;
String yourEmail;
String myEmail;

    public CoursesAdapter(Context context, List<Notifications> notifications) {

        this.context = context;
        this.notifications = notifications;
    }


    @Override
    public CoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;




     view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);


       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);

        CoursesViewHolder coursesViewHolder = new CoursesViewHolder(view);
        return coursesViewHolder;
    }

    @Override
    public void onBindViewHolder(CoursesViewHolder holder, int position) {
        Notifications cour = notifications.get(position);
        holder.myName.setText(cour.getMyName());
     //   holder.yourEmail.setText(cour.getMyEmail());
        yourEmail=cour.getYourEmail();
        yourName=cour.getYourName();
        myEmail=cour.getMyEmail();

    }

    @Override
    public int getItemCount() {
        Log.d("Size", String.valueOf(notifications.size()));
        return notifications.size();

    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myName;
        Button btnConfirm;


        public CoursesViewHolder(final View view) {
            super(view);
            view.setOnClickListener(this);

            myName = (TextView) view.findViewById(R.id.myName);
           // yourEmail=(TextView)view.findViewById(R.id.yourEmail);
            btnConfirm=(Button)view.findViewById(R.id.btnConfirm);

            btnConfirm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String url=Sample.myIp+"richdatesapi/InsertNotifications.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                if (response.equals("Confirmed")) {
                   ///Toast.makeText(context, yourEmail.getText().toString()+girlmail, Toast.LENGTH_SHORT).show();
                    deleteNotifications(yourEmail, myEmail);

                    Bundle bundle=new Bundle();
                    bundle.putString("Email",myEmail);
                    //  Toast.makeText(MainActivity.this, email+contact, Toast.LENGTH_SHORT).show();
                     GirlProfileFragment girlProfileFragment=new GirlProfileFragment();
                    girlProfileFragment.setArguments(bundle);
                    fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainContainer,girlProfileFragment);
                    fragmentTransaction.commit();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("MyEmail",yourEmail);
                params.put("YourEmail",myEmail);
                params.put("MyName",yourName);
                return params;
            }
        };
        MySingleton.getMyInstance(context).addToRequestQueue(stringRequest);

    }
});

        }


        @Override
        public void onClick(View v) {

        }
    }
    public void deleteNotifications(final String myEmail, final String yourEmail)
    {
        String url=Sample.myIp+"richdatesapi/DeleteNotification.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Deleted"))
                {

                   CoursesAdapter.this.notifyDataSetChanged();
                }
                //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("MyEmail",myEmail);
                params.put("YourEmail",yourEmail);
                return params;
            }
        };
        MySingleton.getMyInstance(context).addToRequestQueue(stringRequest);
    }
}

