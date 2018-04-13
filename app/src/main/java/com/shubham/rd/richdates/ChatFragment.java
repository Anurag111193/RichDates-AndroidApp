package com.shubham.rd.richdates;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.androidchatapp");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }

        return view;
    }

}
