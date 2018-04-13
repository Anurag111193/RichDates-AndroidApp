package com.shubham.rd.richdates;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham on 4/2/2018.
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {
    List<FriendList> friendLists = new ArrayList<>();
    String name, imageName;
    Context context;

    public FriendListAdapter(Context context, List<FriendList> friendListArray) {
        this.context = context;
        this.friendLists = friendListArray;
    }

    @Override
    public FriendListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;




        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_card_layout, parent, false);


        // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);

        FriendListViewHolder friendListViewHolder = new FriendListViewHolder(view);
        return friendListViewHolder;

    }

    @Override
    public void onBindViewHolder(FriendListViewHolder holder, int position) {
FriendList friendList=friendLists.get(position);
holder.tvName.setText(friendList.getFriendName());
imageName=friendList.getFriendImage();
       // Toast.makeText(context, friendList.getFriendImage(), Toast.LENGTH_SHORT).show();
        Glide.with(context).load(Sample.myIp+"richdatesapi/Uploads/"+friendList.getFriendImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
     return   friendLists.size();
    }


    public class FriendListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        ImageView img;
        public FriendListViewHolder(View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tv_friend_list);
            img=(ImageView)itemView.findViewById(R.id.img_friend_list);
        }

        @Override
        public void onClick(View v) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom);
            dialog.setTitle("Title...");

            // set the custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.text);
            text.setText("Android custom dialog example!");
            ImageView image = (ImageView) dialog.findViewById(R.id.img_dialog);
            Glide.with(context).load(Sample.myIp+"richdatesapi/Uploads/"+imageName).into(image);

//            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//            // if button is clicked, close the custom dialog
//            dialogButton.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });

            dialog.show();
        }
    }
}
