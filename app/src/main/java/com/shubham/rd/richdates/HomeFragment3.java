package com.shubham.rd.richdates;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment3 extends Fragment {

    String url = Sample.myIp+"richdatesapi/GetUserData.php";
    TextView tvYourName,tvIntrests,tvAge,textIntrest,tvNothing,textAge,textInterests;
    ImageView imgPic;
    RadioGroup rgOptions;
    RadioButton rdOption1,rdOption2,rdOption3,rdOption4;
    Button btnNext, btnPrevious,btnRequest;
    JSONArray jsonArray = null;
    ImageButton imgBtnLike;
    String gender,myEmail,myName,yourEmail,yourName;
    int index = 0;
   AlertDialog.Builder alertDialog;

Group group;
    ProgressBar progress_Bar;
    public HomeFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_fragment3, container, false);
        progress_Bar=(ProgressBar)view.findViewById(R.id.progressBar);
        textAge=(TextView)view.findViewById(R.id.textAge);
        textIntrest=(TextView)view.findViewById(R.id.textIntrest);

      alertDialog=new AlertDialog.Builder(getContext());
        progress_Bar.setVisibility(View.VISIBLE);
        group=(Group)view.findViewById(R.id.groupHome);
       tvNothing=(TextView)view.findViewById(R.id.tvNothing);
        myName = this.getArguments().getString("Name");
        String contact = this.getArguments().getString("Contact");
        myEmail = this.getArguments().getString("Email");
       // Toast.makeText(getContext(), myEmail, Toast.LENGTH_SHORT).show();
        String dob = this.getArguments().getString("Dob");
        tvYourName = (TextView) view.findViewById(R.id.tvYourName);
        tvAge=(TextView)view.findViewById(R.id.tvAge);
        textIntrest=(TextView)view.findViewById(R.id.textIntrest);
        btnRequest=(Button)view.findViewById(R.id.btnRequest);
        tvIntrests=(TextView)view.findViewById(R.id.tvInterests);
        imgPic=(ImageView)view.findViewById(R.id.imgPic);
        gender = this.getArguments().getString("Gender");
        progress_Bar=(ProgressBar)view.findViewById(R.id.progressBar);
        btnNext = (Button) view.findViewById(R.id.btnNext);
        btnPrevious = (Button) view.findViewById(R.id.btnPrevious);
        imgBtnLike=(ImageButton) view.findViewById(R.id.imgBtnLike);
       if(gender.equals("Male"))
       {

           imgPic.setVisibility(View.GONE);
           btnRequest.setVisibility(View.GONE);

       }
       else if(gender.equals("Female"))
       {
           //textIntrest.setVisibility(View.VISIBLE);
           imgBtnLike.setVisibility(View.GONE);
           textIntrest.setVisibility(view.GONE);
           tvIntrests.setVisibility(View.GONE);
           btnRequest.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String url3=Sample.myIp+"richdatesapi/InsertIntoNotificationBoy.php";
                   StringRequest stringRequest2=new StringRequest(Request.Method.POST,url3, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                         Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                       }
                   }){
                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {
                           Map<String,String> params=new HashMap<>();
                           params.put("MyEmail",myEmail);
                           params.put("YourEmail",yourEmail);
                           params.put("MyName",myName);
                           return params;
                       }
                   };
                   MySingleton.getMyInstance(getContext()).addToRequestQueue(stringRequest2);
               }
           });
       }

        imgBtnLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Liked", Toast.LENGTH_SHORT).show();
                String url2=Sample.myIp+"richdatesapi/InsertLikes.php";
                StringRequest stringRequest2=new StringRequest(Request.Method.POST,url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(),"Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("MyEmail",myEmail);
                        params.put("GirlsEmail",yourEmail);
                        return params;
                    }
                };
                MySingleton.getMyInstance(getContext()).addToRequestQueue(stringRequest2);
            }
        });
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                     jsonArray = jsonObject.getJSONArray("response");
                    //  Toast.makeText(MapsActivity.this, "" + jsonArray, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(index);
                        yourName=obj.getString("name");
                        yourEmail=obj.getString("email");
                        String imageName=obj.getString("image");
                        Log.d("ImageName", yourEmail);
                        String interests=obj.getString("intrests");
                        progress_Bar.setVisibility(View.GONE);
                        String dob = obj.getString("dob");
                        String date=dob;
                        String[] items1 = date.split("/");
                        String date1=items1[0];
                        String month=items1[1];
                        String year=items1[2];
                        Log.d("Year1",year);
                        int yearCurrent=Calendar.getInstance().get(Calendar.YEAR);
                        Log.d("Year2",yearCurrent+"");
                        int age=yearCurrent-Integer.parseInt(year);
                        tvAge.setText(age+"");
                        tvYourName.setText(yourName);
                        tvIntrests.setText(interests);
                        if(gender.equals("Female"))
                        {
                            imgPic.setVisibility(View.VISIBLE);
                            tvIntrests.setVisibility(View.GONE);
                            Glide.with(getContext())
                                    .load(Sample.myIp+"richdatesapi/Uploads/"+imageName)
                                    .into(imgPic);
                        }





                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                    progress_Bar.setVisibility(View.GONE);
                   tvAge.setVisibility(View.GONE);
                    tvIntrests.setVisibility(View.GONE);
                    tvYourName.setVisibility(View.GONE);
                    btnNext.setVisibility(View.GONE);
                    btnPrevious.setVisibility(View.GONE);
textAge.setVisibility(View.GONE);
textIntrest.setVisibility(View.GONE);
                    imgBtnLike.setVisibility(View.GONE);
                    btnRequest.setVisibility(View.GONE);
                    imgPic.setVisibility(View.GONE);
                    tvNothing.setVisibility(View.VISIBLE);
                    Log.e("HomeExp",e.getMessage());
                    Toast.makeText(getContext(), "No Profiles To Display", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_Bar.setVisibility(View.GONE);
                imgPic.setVisibility(View.GONE);
                tvAge.setVisibility(View.GONE);
                tvIntrests.setVisibility(View.GONE);
                tvYourName.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                textAge.setVisibility(View.GONE);
                textInterests.setVisibility(View.GONE);
                imgBtnLike.setVisibility(View.GONE);
                btnRequest.setVisibility(View.GONE);
                tvNothing.setVisibility(View.VISIBLE);
                Log.e("HomeError",error.getMessage());
                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("Gender",gender);
                params.put("Email",myEmail);
                return params;
            }
        };

        MySingleton.getMyInstance(getContext()).addToRequestQueue(stringRequest);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(index!=jsonArray.length()-1)
                {
                    Log.d("Length", String.valueOf(jsonArray.length()));
                    index=index+1;
                    Log.d("Index", ((String.valueOf(index))));
                    try {

                        GetExamData(index);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else
                {

                }

            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==0)
                {

                }
                else
                {
                    index=index-1;
                    try {
                        GetExamData(index);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        return view;
    }
    public void GetExamData( int index) throws JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {
            //  JSONObject obj = jsonArray.getJSONObject(i);
            JSONObject Obj = jsonArray.getJSONObject(index);
            String name=Obj.getString("name");
            yourEmail = Obj.getString("email");
            String interests = Obj.getString("intrests");
            String dob = Obj.getString("dob");
            String imageName = Obj.getString("image");
            tvYourName.setText(name);
            tvIntrests.setText(interests);
            String date=dob;
            String[] items1 = date.split("/");
            String date1=items1[0];
            String month=items1[1];
            String year=items1[2];
            Log.d("Year1",year);
            int yearCurrent=Calendar.getInstance().get(Calendar.YEAR);
            Log.d("Year2",yearCurrent+"");
            int age=yearCurrent-Integer.parseInt(year);
            tvAge.setText(age+"");
            if(gender.equals("Female"))
            {
                textIntrest.setVisibility(View.GONE);
                imgPic.setVisibility(View.VISIBLE);
                tvIntrests.setVisibility(View.GONE);
                Glide.with(getContext())
                        .load(Sample.myIp+"richdatesapi/Uploads/"+imageName)
                        .into(imgPic);
            }
           // Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getContext());
           // tvQuestion.startAnimation(hyperspaceJumpAnimation);


        }

    }
}
