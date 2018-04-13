package com.shubham.rd.richdates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 8/7/2017.
 */

public class CoursesAdapterGirl extends RecyclerView.Adapter<CoursesAdapterGirl.CoursesViewHolder> {

    List<NotificationsGirl> notifications = new ArrayList<NotificationsGirl>();
    Context context;

    Toolbar toolbar;
String gender="";

    public CoursesAdapterGirl(Context context, List<NotificationsGirl> notifications) {

        this.context = context;
        this.notifications = notifications;
    }


    @Override
    public CoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


       


    // view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout_girl, parent, false);

        CoursesViewHolder coursesViewHolder = new CoursesViewHolder(view);
        return coursesViewHolder;
    }

    @Override
    public void onBindViewHolder(CoursesViewHolder holder, int position) {
        NotificationsGirl cour = notifications.get(position);
        holder.myName.setText(cour.getBoyName());



    }

    @Override
    public int getItemCount() {
        Log.d("Size", String.valueOf(notifications.size()));
        return notifications.size();

    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myName;



        public CoursesViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            myName= (TextView) view.findViewById(R.id.myName);



        }


        @Override
        public void onClick(View v) {

        }
    }
}

