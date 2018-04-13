package com.shubham.rd.richdates;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences sharedPreferences;
    EditText etName,etContact,etEmail,etDob,etIntrests;
    Button btnUpdate;
    Bitmap bitmap;
    String imageName="",gender="",interests="",email1="";
    CircleImageView imgProfile;
    ImageView imgicon;
    FloatingActionButton fabImageUpload;
    private static final String CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        imgicon=(ImageView)view.findViewById(R.id.imageView9);
        etName=(EditText)view.findViewById(R.id.etName);
        etContact=(EditText)view.findViewById(R.id.etContact);
        etDob=(EditText)view.findViewById(R.id.etDob);
        etEmail=(EditText)view.findViewById(R.id.etEmail);
        etIntrests=(EditText)view.findViewById(R.id.etIntrests);
        imgProfile=(CircleImageView)view.findViewById(R.id.imgProPic);
//        final String name = this.getArguments().getString("Name");
//        String contact = this.getArguments().getString("Contact");
       email1 = this.getArguments().getString("Email");
//        String dob = this.getArguments().getString("Dob");
        gender = this.getArguments().getString("Gender");
//         final String imageName2=this.getArguments().getString("ImageName");

//        Glide.with(this).load("http://signup.isslearnlabs.com/palloti/Uploads/"+contact+".jpg").asBitmap().
//                into(new SimpleTarget<Bitmap>(300,300) {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        imgProfile.setImageBitmap(resource);
//                    }
//                });
        refreshProfile();
if(gender.equals("Female"))
{
    imgicon.setVisibility(View.VISIBLE);
    etIntrests.setVisibility(View.VISIBLE);
}
else if(gender.equals("Male"))
{
   imgicon.setVisibility(View.GONE);
    etIntrests.setVisibility(View.GONE);
    interests="";
}

//        etName.setText(name);
//        etEmail.setText(email1);
//        etDob.setText(dob);
//        etContact.setText(contact);
        btnUpdate=(Button)view.findViewById(R.id.btnConfirm);
        fabImageUpload=(FloatingActionButton)view.findViewById(R.id.fabUploadImage);

        fabImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

       btnUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(gender.equals("Female"))
{
interests=etIntrests.getText().toString();
}
else if(gender.equals("Male"))
{

    interests="";
}
               String url=Sample.myIp+"richdatesapi/UpdateUserInfo.php";
               StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                       //Glide.with(getContext()).load("http://signup.isslearnlabs.com/palloti/Uploads/"+response).into(imgProfile);
                       if(response.equals("Updated"))
                       {
                           refreshProfile();
                       }
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               })
               {
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {
                       Map<String,String> params=new HashMap<>();
                       params.put("Name",etName.getText().toString());
                       params.put("Email",etEmail.getText().toString());
                       params.put("Contact",etContact.getText().toString());
                       params.put("Dob",etDob.getText().toString());
                       params.put("Gender",gender);

                       params.put("Intrests",interests);


                       bitmap = ((BitmapDrawable)imgProfile.getDrawable().getCurrent()).getBitmap();


                       Log.d("BMp",bitmap.toString());
                       imageName = generateRandomString() + ".jpg";
                       params.put("Image",ImageToString(((BitmapDrawable)imgProfile.getDrawable().getCurrent()).getBitmap()));
                       params.put("ImageName", imageName);
                       Log.d("ImageNameU",imageName);
                       return params;


                   }
               };
               MySingleton.getMyInstance(getContext()).addToRequestQueue(stringRequest);

           }
       });

                return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {

                Uri path = data.getData();

                //imageName = etContact.getText().toString() + ".jpg";

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), path);
                    imgProfile.setImageBitmap(bitmap);


                } catch (IOException e) {
                    //e.printStackTrace();
                }


            }

    }
    private String ImageToString(Bitmap bitmap) {
        bitmap = Bitmap.createScaledBitmap(bitmap, 800, 600, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
    public void refreshProfile()
    {
        String url=Sample.myIp+"richdatesapi/GetRefreshedData.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                     // Toast.makeText(getContext(), "" + jsonArray, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String name = obj.getString("name");
                        String contact = obj.getString("contact");
                        String email = obj.getString("email");
                        String dob = obj.getString("dob");
                       // gender = obj.getString("gender");


                        String imageName = obj.getString("image");
                        Log.d("ImageNameR",imageName);
                        if(gender.equals("Female"))
                        {
                            String intrests=obj.getString("intrests");
                            etIntrests.setText(intrests);
                        }
                        etName.setText(name);
                        etContact.setText(contact);
                        etName.setText(name);
                        etDob.setText(dob);
                        etEmail.setText(email);


                        Glide.with(getContext()).load(Sample.myIp+"richdatesapi/Uploads/"+imageName).asBitmap().
                        into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                imgProfile.setImageBitmap(resource);
                            }
                        });
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
                params.put("Email",email1);
                return params;
            }
        };
        MySingleton.getMyInstance(getContext()).addToRequestQueue(stringRequest);
    }


    public String generateRandomString(){

        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<10; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

}
