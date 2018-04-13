package com.shubham.rd.richdates;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

/**
 * Created by Shubham on 3/17/2018.
 */

@Layout(R.layout.tinder_card_view)
public class TinderCard {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;



    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public TinderCard(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile =  profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
       // Glide.with(mContext).load(mProfile.getImageUrl()).into(profileImageView);
        nameAgeTxt.setText(mProfile.getName());
        locationNameTxt.setText(mProfile.getDob());
        Glide.with(mContext).load("http://signup.isslearnlabs.com/palloti/Uploads/"+mProfile.getImageName())
                .into(profileImageView);
        Log.d("Name",nameAgeTxt.getText().toString());

    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        nameAgeTxt.setText(mProfile.getName());
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
       // nameAgeTxt.setText(mProfile);
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
      //  nameAgeTxt.setText(mProfile.getName());
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
       // nameAgeTxt.setText(mProfile);
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
       //nameAgeTxt.setText(mProfile);
        Log.d("EVENT", "onSwipeOutState");
    }
}
