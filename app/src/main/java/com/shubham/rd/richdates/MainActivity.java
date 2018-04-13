package com.shubham.rd.richdates;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
String email,contact,dob,name,gender,imageName;
    private TextView mTextMessage;
    Bundle bundle;
    String getEmail;
    IntroManager introManager;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    bundle=new Bundle();
                    bundle.putString("Email",email);
                    bundle.putString("Contact",contact);
                    bundle.putString("Dob",dob);
                    bundle.putString("Name",name);
                    bundle.putString("Gender",gender);
                    //  Toast.makeText(MainActivity.this, email+contact, Toast.LENGTH_SHORT).show();
                    HomeFragment3 homeFragment3=new HomeFragment3();
                    homeFragment3.setArguments(bundle);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainContainer,homeFragment3);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_chat:

                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainContainer, new ChatFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    bundle=new Bundle();
                    bundle.putString("Email",email);
                    bundle.putString("Contact",contact);
                    bundle.putString("Dob",dob);
                    bundle.putString("Name",name);
                    bundle.putString("Gender",gender);
                    NotificationsFragment notificationsFragment=new NotificationsFragment();
                    notificationsFragment.setArguments(bundle);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainContainer, notificationsFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_profile:
                   // Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    bundle=new Bundle();
                    bundle.putString("Email",email);
                    bundle.putString("Contact",contact);
                    bundle.putString("Dob",dob);
                    bundle.putString("Name",name);
                    bundle.putString("Gender",gender);
                    bundle.putString("ImageName",imageName);
                  //  Toast.makeText(MainActivity.this, email+contact, Toast.LENGTH_SHORT).show();
                    ProfileFragment profileFragment=new ProfileFragment();
                    profileFragment.setArguments(bundle);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainContainer, profileFragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences=getSharedPreferences("first",0);
        editor=sharedPreferences.edit();
        sharedPreferences.getString("first",null);
            editor.putString("first","logout");
            editor.commit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getColor(R.color.statusBarColor));
            }

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        getEmail= prefs.getString("Email","");
        //Toast.makeText(this, getEmail, Toast.LENGTH_SHORT).show();
        String url=Sample.myIp+"richdatesapi/GetUserDataFromEmail.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                if(response.equals("Error"))
                {

                   // Toast.makeText(MainActivity.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("response");
                        //  Toast.makeText(MapsActivity.this, "" + jsonArray, Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                             name = obj.getString("name");
                             contact = obj.getString("contact");
                            email = obj.getString("email");
                            dob = obj.getString("dob");
                             gender = obj.getString("gender");
                          //  Toast.makeText(MainActivity.this, gender, Toast.LENGTH_SHORT).show();
                             imageName=obj.getString("image");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                       // progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("Email",getEmail);

                return  params;
            }

        };
        MySingleton.getMyInstance(MainActivity.this).addToRequestQueue(stringRequest);

        //Toast.makeText(this, "Welcome "+name, Toast.LENGTH_SHORT).show();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bundle=new Bundle();
        bundle.putString("Email",email);
        bundle.putString("Contact",contact);
        bundle.putString("Dob",dob);
        bundle.putString("Name",name);
        bundle.putString("Gender",gender);
//        Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
//          Toast.makeText(MainActivity.this, email+contact, Toast.LENGTH_SHORT).show();
       WelcomeFragment welcomeFragment=new WelcomeFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer,welcomeFragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            bundle=new Bundle();
            bundle.putString("Email",email);
            bundle.putString("Contact",contact);
            bundle.putString("Dob",dob);
            bundle.putString("Name",name);
            bundle.putString("Gender",gender);
            bundle.putString("ImageName",imageName);
            //  Toast.makeText(MainActivity.this, email+contact, Toast.LENGTH_SHORT).show();
            FriendListFragment friendListFragment=new FriendListFragment();
            friendListFragment.setArguments(bundle);
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainContainer, friendListFragment);
            fragmentTransaction.commit();
//            editor.putString("first",null);
//            editor.commit();
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);

            //            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//            startActivity(intent);
            return true;

        }
        else if(id==R.id.action_Logout)
        {
            editor.putString("first",null);
            editor.commit();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

            Intent intent2=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Exit");
        alertDialog.setMessage("Do you really want to Exit");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.create();
        alertDialog.show();

    }

}
