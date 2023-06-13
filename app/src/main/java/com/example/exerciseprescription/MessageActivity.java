package com.example.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.exerciseprescription.Adapter.MessageAdapter;
import com.example.exerciseprescription.Adapter.UserAdapter;
import com.example.exerciseprescription.class2.ChatModel;
import com.example.exerciseprescription.class2.DoctorModel;
import com.example.exerciseprescription.class2.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id, userId;
    View toolbar;
    TextView username;
    ImageView back;
    CircleImageView profile_image;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ImageButton send_btn;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<ChatModel> mChat;
    RecyclerView recyclerView;
    private boolean isDoctor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        userId = getIntent().getStringExtra("userId");

        send_btn = findViewById(R.id.send_btn);
        text_send = findViewById(R.id.text_send);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        toolbar = findViewById(R.id.toolbar);
        username = toolbar.findViewById(R.id.username);
        back = toolbar.findViewById(R.id.backBtn);
        profile_image = toolbar.findViewById(R.id.profile_image);

        checkUser();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoctor) {
                    startActivity(new Intent(MessageActivity.this, AdminMessage.class));
                    finish();
                } else {
                    startActivity(new Intent(MessageActivity.this, UserMessage.class));
                    finish();
                }


            }
        });


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString().trim();
                if (!msg.equals("")) {
                    sendMessage(id, userId, msg);
                } else {
                    errorPopup();
                }
                text_send.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isDoctor) {
            startActivity(new Intent(MessageActivity.this, AdminMessage.class));
            finish();
        } else {
            startActivity(new Intent(MessageActivity.this, UserMessage.class));
            finish();
        }
    }

    private void checkUser() {
        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        databaseReference.child("Doctor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DoctorModel user = dataSnapshot.getValue(DoctorModel.class);
                    if (user != null && user.getId().equals(id)) {
                        isDoctor = true;
                        recyclerView.setBackground(ContextCompat.getDrawable(MessageActivity.this, R.drawable.red_bg));

                        break;
                    }
                }

                // Continue with reading users or doctors after checking the user type
                if (isDoctor) {
                    readUsers();
                } else {
                    readDoctors();
                }

//                readMessages(id,userId, imageUrl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    private void readUsers() {
        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        Query query = databaseReference.child("User").child(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String gender = snapshot.child("gender").getValue().toString();
                String name = snapshot.child("name").getValue().toString();

                username.setText(name);
                setProfilePic(gender);

                readMessages(id,userId, isDoctor,gender);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readDoctors() {
        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        Query query = databaseReference.child("Doctor").child(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String gender = snapshot.child("gender").getValue().toString();
                String name = snapshot.child("name").getValue().toString();

                username.setText(name);
                setProfilePic(gender);

                readMessages(id,userId, isDoctor,gender);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setProfilePic(String gender) {
        if (!isDoctor) {
            if (gender.equals("male")) {
                profile_image.setImageResource(R.drawable.doctor_man);
            } else {
                profile_image.setImageResource(R.drawable.doctor_woman);
            }
        } else {
            if (gender.equals("male")) {
                profile_image.setImageResource(R.drawable.user_man);
            } else {
                profile_image.setImageResource(R.drawable.user_woman);

            }
        }
    }

    private void sendMessage(String sender, String receiver, String message) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        databaseReference.child("Chats").push().setValue(hashMap);

        //add user to chat fragment
        Query queryChatList = databaseReference.child("ChatList").child(id).child(userId);
        queryChatList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    databaseReference.child("ChatList").child(id).child(userId).child("id").setValue(userId);
                    databaseReference.child("ChatList").child(userId).child(id).child("id").setValue(id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void errorPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cannot send empty text!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Redirect to the homepage
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void readMessages(final String myId, final String userId, final boolean isDoctor,final String gender) {
        mChat = new ArrayList<>();

        Query query = databaseReference.child("Chats");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    String message = dataSnapshot.child("message").getValue(String.class);
                    String receiver = dataSnapshot.child("receiver").getValue(String.class);
                    String sender = dataSnapshot.child("sender").getValue(String.class);

                    if (message != null && receiver != null && sender != null) {
                        if (receiver.equals(myId) && sender.equals(userId) || receiver.equals(userId) && sender.equals(myId)) {
                            ChatModel chatModel = new ChatModel(sender, receiver, message);
                            mChat.add(chatModel);
                        }
                    }
                }

                messageAdapter = new MessageAdapter(MessageActivity.this, mChat, isDoctor,gender);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}