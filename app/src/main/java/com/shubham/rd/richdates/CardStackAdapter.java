package com.shubham.rd.richdates;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shubham on 3/19/2018.
 */

public class CardStackAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    String url = "http://192.168.48.200/richdatesapp/GetUserData.php";
    int[] mResources = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f
    };
    ArrayList<String> emailList;

    ArrayList<String> data;



    public CardStackAdapter(Context mContext, ArrayList<String> data) {
        this.mContext = mContext;
this.data=data;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
//        Toast.makeText(mContext, emailList.size(), Toast.LENGTH_SHORT).show();
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
//       emailList= GetData();
        TextView imageView = (TextView) itemView.findViewById(R.id.imageView);
        //  Toast.makeText(mContext, emailList.get(position), Toast.LENGTH_SHORT).show();
        imageView.setText(data.get(position));

        container.addView(itemView);

        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}


