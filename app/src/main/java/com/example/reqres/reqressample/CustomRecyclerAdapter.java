package com.example.reqres.reqressample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reqres.reqressample.models.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sandeep on 05/04/18.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder> {

    public User[]  users;

    public CustomRecyclerAdapter(User[] users){
        this.users = users;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, null);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        if(users[position].getImageUrl() != null){
            Picasso.get()
                    .load(users[position].getImageUrl())
                    .placeholder(R.drawable.profile_icon)
                    .error(R.drawable.profile_icon)
                    .into(holder.circleImageView);
        }else {
            holder.circleImageView.setImageResource(R.drawable.profile_icon);
        }
        holder.firstName.setText(users[position].getFirstName());
        holder.lastName.setText(users[position].getLastName());
    }

    @Override
    public int getItemCount() {
        return users != null && users.length > 0 ? users.length : 0;
    }

    public void setUsers(User[] users){
        this.users = users;
        notifyDataSetChanged();
    }


    public class CustomViewHolder  extends RecyclerView.ViewHolder{

        public CircleImageView circleImageView;

        public TextView firstName, lastName;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.placeholder);
            firstName = itemView.findViewById(R.id.firstname);
            lastName = itemView.findViewById(R.id.lastname);
        }
    }
}
