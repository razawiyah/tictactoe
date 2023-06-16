package com.razawiyahdev.exerciseprescription.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.razawiyahdev.exerciseprescription.R;
import com.razawiyahdev.exerciseprescription.class2.ChatModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<ChatModel> mChat;
    private boolean isDoctor;
    private String gender;
    FirebaseUser fUser;

    public MessageAdapter(Context mContext, List<ChatModel> mChat, boolean isDoctor, String gender) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.isDoctor = isDoctor;
        this.gender = gender;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatModel chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());

        if(!isDoctor){
            if(gender.equals("male")){
                holder.profile_image.setImageResource(R.drawable.doctor_man);
            } else {
                holder.profile_image.setImageResource(R.drawable.doctor_woman);
            }
        }else {
            if (gender.equals("male")) {
                holder.profile_image.setImageResource(R.drawable.user_man);
            } else {
                holder.profile_image.setImageResource(R.drawable.user_woman);

            }
        }


    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        if (mChat.get(position).getSender().equals(fUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}