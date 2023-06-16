package com.razawiyahdev.exerciseprescription.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.razawiyahdev.exerciseprescription.Adapter.UserAdapter;
import com.razawiyahdev.exerciseprescription.R;
import com.razawiyahdev.exerciseprescription.class2.DoctorModel;
import com.razawiyahdev.exerciseprescription.class2.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    String id;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<UserModel> mUsers;
//    List<DoctorModel> mUsers;

    private boolean isDoctor = false;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();

        checkUser();

//        if(isDoctor){
//            readUsers();
//        }else {
//            readDoctors();
//        }

        return view;
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
                        view.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.white_bg));
                        break;
                    }
                }

                // Continue with reading users or doctors after checking the user type
                if (isDoctor) {
                    readUsers();
                } else {
                    readDoctors();
                }
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

        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    UserModel user = dataSnapshot.getValue(UserModel.class);

                    assert user!=null;
                        mUsers.add(user);
                }

                userAdapter = new UserAdapter(getContext(),mUsers,false,isDoctor);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readDoctors() {
        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        databaseReference.child("Doctor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    UserModel user = dataSnapshot.getValue(UserModel.class);

                    assert user!=null;
                        mUsers.add(user);
                }

                userAdapter = new UserAdapter(getContext(),mUsers,false,isDoctor);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}