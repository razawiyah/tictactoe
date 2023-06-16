package com.razawiyahdev.exerciseprescription.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.razawiyahdev.exerciseprescription.Adapter.UserAdapter;
import com.razawiyahdev.exerciseprescription.R;
import com.razawiyahdev.exerciseprescription.class2.Chatlist;
import com.razawiyahdev.exerciseprescription.class2.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<UserModel> mUsers;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    private boolean isDoctor=false;

    List<Chatlist> usersList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        usersList = new ArrayList<>();

        Query query = databaseReference.child("Doctor").child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    isDoctor = true;
                    view.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.red_bg));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chatList();

        return view;
    }

    private void chatList(){
        Query query2 = databaseReference.child("ChatList").child(id);
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chatlist chatlist = dataSnapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }
                if(!isDoctor){
                    chatListDoctor();
                }else {
                    chatListUser();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /*private void chatListUser() {
        mUsers = new ArrayList<>();
        Query query2 = databaseReference.child("User");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    for (Chatlist chatlist : usersList) {
                        if (user.getId().equals(chatlist.getId())) {
//                            mUsers.add(user);
                            boolean alreadyExists = false;

                            for (UserModel existingUser : mUsers) {
                                if (existingUser.getId().equals(user.getId())) {
                                    alreadyExists = true;
                                    break;
                                }
                            }

                            if (!alreadyExists) {
                                mUsers.add(user);
                                break; // Once the user is added, exit the inner loop
                            }
                        }
                    }
                }
//                userAdapter = new UserAdapter(getContext(), mUsers, true);
//                recyclerView.setAdapter(userAdapter);

                if (mUsers.size() > 0) {
                    userAdapter = new UserAdapter(getContext(), mUsers, true,isDoctor);
                    recyclerView.setAdapter(userAdapter);
                } else {
                    // Handle the case when there are no users to display
                    Log.d("Tag", "Recycler view error adaa");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void chatListDoctor() {
        mUsers = new ArrayList<>();
        Query query2 = databaseReference.child("Doctor");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    for (Chatlist chatlist : usersList) {
                        if (user.getId().equals(chatlist.getId())) {
//                            mUsers.add(user);
                            boolean alreadyExists = false;

                            for (UserModel existingUser : mUsers) {
                                if (existingUser.getId().equals(user.getId())) {
                                    alreadyExists = true;
                                    break;
                                }
                            }

                            if (!alreadyExists) {
                                mUsers.add(user);
                                break; // Once the user is added, exit the inner loop
                            }
                        }
                    }
                }
//                userAdapter = new UserAdapter(getContext(), mUsers, true);
//                recyclerView.setAdapter(userAdapter);

                if (mUsers.size() > 0) {
                    userAdapter = new UserAdapter(getContext(), mUsers, true,isDoctor);
                    recyclerView.setAdapter(userAdapter);
                } else {
                    // Handle the case when there are no users to display
                    Log.d("Tag", "Recycler view error adaa");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    private void chatListUser() {
        mUsers = new ArrayList<>();
        Query query2 = databaseReference.child("User");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    for (Chatlist chatlist : usersList) {
                        if (user != null && user.getId().equals(chatlist.getId())) {
                            boolean alreadyExists = false;

                            for (UserModel existingUser : mUsers) {
                                if (existingUser != null && existingUser.getId().equals(user.getId())) {
                                    alreadyExists = true;
                                    break;
                                }
                            }

                            if (!alreadyExists) {
                                mUsers.add(user);
                                break; // Once the user is added, exit the inner loop
                            }
                        }
                    }
                }

                if (mUsers.size() > 0) {
                    userAdapter = new UserAdapter(getContext(), mUsers, true, isDoctor);
                    recyclerView.setAdapter(userAdapter);
                } else {
                    // Handle the case when there are no users to display
                    Log.d("Tag", "RecyclerView is empty");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void chatListDoctor() {
        mUsers = new ArrayList<>();
        Query query2 = databaseReference.child("Doctor");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    for (Chatlist chatlist : usersList) {
                        if (user != null && user.getId().equals(chatlist.getId())) {
                            boolean alreadyExists = false;

                            for (UserModel existingUser : mUsers) {
                                if (existingUser != null && existingUser.getId().equals(user.getId())) {
                                    alreadyExists = true;
                                    break;
                                }
                            }

                            if (!alreadyExists) {
                                mUsers.add(user);
                                break; // Once the user is added, exit the inner loop
                            }
                        }
                    }
                }

                if (mUsers.size() > 0) {
                    userAdapter = new UserAdapter(getContext(), mUsers, true, isDoctor);
                    recyclerView.setAdapter(userAdapter);
                } else {
                    // Handle the case when there are no users to display
                    Log.d("Tag", "RecyclerView is empty");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}