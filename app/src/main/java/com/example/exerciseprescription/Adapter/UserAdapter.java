package com.example.exerciseprescription.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exerciseprescription.MessageActivity;
import com.example.exerciseprescription.R;
import com.example.exerciseprescription.class2.ChatModel;
import com.example.exerciseprescription.class2.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<UserModel> mUsers;
    private boolean ischat;
    String theLastMessage;

    String id;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://exerciseprescription-c1b89-default-rtdb.firebaseio.com/");
    private boolean isDoctor;

    public UserAdapter(Context mContext, List<UserModel> mUsers, boolean ischat, boolean isDoctor) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.ischat = ischat;
        this.isDoctor = isDoctor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel user = mUsers.get(position);
        holder.username.setText(user.getName());

        /*fUser = mAuth.getCurrentUser();
        id = fUser.getUid();
        Query query = databaseReference.child("Doctor").child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    isDoctor = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        String gender = user.getGender();
        if(!isDoctor){
            if(!ischat){
                holder.username.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            }
            if(gender.equals("male")){
                holder.profile_image.setImageResource(R.drawable.doctor_man);
            } else {
                holder.profile_image.setImageResource(R.drawable.doctor_woman);
            }
        }else {
            if(ischat){
                holder.username.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                holder.last_msg.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.teal));

            }
            if (gender.equals("male")) {
                holder.profile_image.setImageResource(R.drawable.user_man);
            } else {
                holder.profile_image.setImageResource(R.drawable.user_woman);

            }
        }

        if(ischat){
            lastMessage(user.getId(), holder.last_msg);
        }else {
            holder.last_msg.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userId",user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView profile_image;
        private TextView last_msg;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            last_msg = itemView.findViewById(R.id.last_msg);

        }
    }

    private void lastMessage(final String userId, final TextView last_msg){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String id = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                    if(chatModel.getReceiver().equals(id) && chatModel.getSender().equals(userId) ||
                            chatModel.getReceiver().equals(userId) && chatModel.getSender().equals(id)){
                        theLastMessage = chatModel.getMessage();
                    }
                }

                switch (theLastMessage){
                    case "default":
                        last_msg.setText("No Message");
                        break;

                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}