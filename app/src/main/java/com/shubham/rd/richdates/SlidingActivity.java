package com.shubham.rd.richdates;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlidingActivity extends AppCompatActivity {
private ViewPager viewPager;
    private IntroManager introManager;
    private TextView[] dots;
    private ViewPagerAdapter viewPagerAdapter;
    Button next,skip;
    private LinearLayout dotLayout;
    private int[] layouts;
RelativeLayout container;
LinearLayout linearLayout;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences("first",0);
        editor=sharedPreferences.edit();
        if(sharedPreferences.getString("first",null)!=null){
            Intent intent = new Intent(SlidingActivity.this, MainActivity.class);
            startActivity(intent);

        }
        container=(RelativeLayout)findViewById(R.id.container);
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
      //  linearLayout=(LinearLayout)findViewById(R.id.ll_viewpager);
        if(netInfo==null)
        {
            final AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Internet Connection");
            alertDialogBuilder.setMessage("There is no Internet Connection");
            alertDialogBuilder.setCancelable(false);
           alertDialogBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    final NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
if(netInfo!=null)
{
    dialog.cancel();
}
else
{
    alertDialogBuilder.create();
    alertDialogBuilder.show();
}
                }
            }).setNegativeButton("Quit",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
            alertDialogBuilder.create();
            alertDialogBuilder.show();

        }




//        introManager=new IntroManager(this);
//        if(!introManager.Check())
//        {
//            Intent intent =new Intent(SlidingActivity.this,MainActivity.class);
//            startActivity(intent);
//           finish();
//
//
//        }
//       else
//        {
//          //  introManager.setFirst(false);
//
//        }
//        if(Build.VERSION.SDK_INT>=21)
//        {
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_FULLSCREEN);
//        }
        setContentView(R.layout.activity_sliding);
        if( getIntent().getExtras()!=null && getIntent().getExtras().getBoolean("Exit",false)){
            finish();
        }
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        dotLayout=(LinearLayout)findViewById(R.id.layoutDots);
        skip=(Button)findViewById(R.id.btn_skip);
        next=(Button)findViewById(R.id.btn_next);
        layouts=new int[]{R.layout.activity_screen1, R.layout.activity_screen2, R.layout.activity_screen3};
    addBottomDots(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ChangeStatusBarColor(Color.BLACK);
        }
        viewPagerAdapter=new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viOnPageChangeListener);
skip.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =new Intent(SlidingActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
});
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                int current=getItem(+1);
//                if(current<layouts.length)
//                {
//
//                    viewPager.setCurrentItem(current);
//
//
//                }
//                else
//                {
//                    Intent intent =new Intent(SlidingActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        });

    }
    private void addBottomDots(int position)
    {
                dots=new TextView[layouts.length];
        int[] colorActive=getResources().getIntArray(R.array.dotActive);
        int[] colorInactive=getResources().getIntArray(R.array.dotInactive);
        dotLayout.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotLayout.addView(dots[i]);


        }
        if(dots.length>0)
        {
            dots[position].setTextColor(colorActive[position]);
        }
    }
    private int getItem(int i)
    {

        return viewPager.getCurrentItem()+i;
    }
    ViewPager.OnPageChangeListener viOnPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                //skip.setText("Proceed");
                next.setText("");
            } else {

                next.setText("Next");
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void ChangeStatusBarColor(int color)
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getColor(R.color.colorAccent));
            }
            //Toast.makeText(this, "status bar", Toast.LENGTH_SHORT).show();
        }
    }
    public class ViewPagerAdapter extends PagerAdapter
    {
private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(layouts[position],container,false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            View view=(View)object;
            container.removeView(view);
        }
    }
}
