package com.shubham.rd.richdates;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
EditText etName,etEmail,etDob,etContact,etPassword;
Button btnRegister;
Spinner spinnerGender;
    Calendar myCalendar;
    TextView tvLogin;
    Bitmap bitmap;
    int randomPIN;
    ProgressDialog pd;
    String res="";
int age;

DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getColor(R.color.cardview_dark_background));
            }

        }
        etName=(EditText)findViewById(R.id.etName);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etDob=(EditText)findViewById(R.id.etDob);
        tvLogin=(TextView)findViewById(R.id.tvLogin);
pd=new ProgressDialog(this);
pd.setTitle("Message from Server");
pd.setMessage("Please wait");

         myCalendar = Calendar.getInstance();
        etContact=(EditText)findViewById(R.id.etContact);
        etPassword=(EditText)findViewById(R.id.etPassword);
        datePicker=(DatePicker)findViewById(R.id.datePicker);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();


            }
        });
            spinnerGender=(Spinner)findViewById(R.id.spGender);
            String [] gender=new String[]{"-Please Select Your Gender-","Male","Female"};
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,gender);
            spinnerGender.setAdapter(adapter);



             btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(etName.getText().toString().equals(""))
                {
                    etName.setError("Please Enter Your Name");
                }
                else if(etEmail.getText().toString().equals((""))){
                    etEmail.setError("Please Enter Your Email");

                }
                else if(!isValidEmail(etEmail.getText().toString()))
                {
                    etEmail.setError("Enter valid Email");
                }
                else if(etDob.getText().toString().equals("")){
                    etDob.setError("Please Enter Date of Birth");

                }
                else if(etContact.getText().toString().equals((""))){
                    etContact.setError("Please Enter Contact");

                }
                else if(spinnerGender.getSelectedItem().toString().equals("-Please Select Your Gender-"))
                {
                    Toast.makeText(RegistrationActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
                else if(etContact.getText().toString().equals((""))){
                    etContact.setError("Please Enter Contact");

                }

                else if(etContact.getText().length()!=10)
                {
                    etContact.setError("Please Enter valid Number");
                }
                else if(etDob.getText().toString().equals("")){
                    etDob.setError("Please Enter Date of Birth");

                }
                else if(etPassword.getText().toString().equals("")){
                    etPassword.setError("Please Enter Password");

                }

                else if(age<18)
                {
                    Toast.makeText(RegistrationActivity.this, "Age should be greater than 18", Toast.LENGTH_SHORT).show();
                }

                else{

                        getRegisteredEmail();

                }
            }
        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDob.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int m=month+1;
        etDob.setText( dayOfMonth + "/" + m  +"/" + year);
        String date=etDob.getText().toString();
        String[] items1 = date.split("/");
        String date1=items1[0];
        String month1=items1[1];
        String year1=items1[2];
        Log.d("Year1",year1);
        int yearCurrent=Calendar.getInstance().get(Calendar.YEAR);
        Log.d("Year2",yearCurrent+"");
         age=yearCurrent-Integer.parseInt(year1);

    }
    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }


    class MainSender extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
           // Toast.makeText(RegistrationActivity.this, "Started", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            GMailSender sender = new GMailSender(

                    "sgulhane84@gmail.com",

                    "143@Balaji");

            try {
                sender.sendMail("Email Verification", "Your Verification Code is " + randomPIN,

                        "sgulhane84@gmail.com",

                        etEmail.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            pd.dismiss();
            Intent intent = new Intent(RegistrationActivity.this, VerifyEmailActivity.class);
            intent.putExtra("Name", etName.getText().toString());
            intent.putExtra("Email", etEmail.getText().toString());
            intent.putExtra("Contact", etContact.getText().toString());
            intent.putExtra("Dob", etDob.getText().toString());
            intent.putExtra("Gender", spinnerGender.getSelectedItem().toString());
            intent.putExtra("Password", etPassword.getText().toString());
            intent.putExtra("Otp", randomPIN+"");
            Log.d("Otp",randomPIN+"");

            startActivity(intent);
           // Toast.makeText(RegistrationActivity.this, "Stopped", Toast.LENGTH_SHORT).show();
        }
    }
    private void getRegisteredEmail()
    {
        pd.show();
        String url=Sample.myIp+"richdatesapi/GetEmailData.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Present"))
                {
                    pd.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Email is Already Registered", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    randomPIN = (int) (Math.random() * 9000) + 1000;
                    new MainSender().execute();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(RegistrationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("Email",etEmail.getText().toString());
                return params;
            }
        };

        MySingleton.getMyInstance(this).addToRequestQueue(stringRequest);
    }
    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
