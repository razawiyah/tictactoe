package com.razawiyahdev.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.razawiyahdev.exerciseprescription.Fragments.ChatsFragment;
import com.razawiyahdev.exerciseprescription.Fragments.UsersFragment;
import com.razawiyahdev.exerciseprescription.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class AdminMessage extends AppCompatActivity {

    View toolbar;
    TextView title;
    ImageView logout,menu;
    public SignOut dialog;

    DrawerLayout drawerLayout;
    LinearLayout homepageBtn,messageBtn,aboutBtn,updatePBtn;
    TextView drName,drName2,gender,email;
    String id;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    TabLayout tab_layout;
    ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_message);

        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.title);
        logout = toolbar.findViewById(R.id.logoutBtn);
        menu = toolbar.findViewById(R.id.menu);

        dialog = new SignOut(this);

        title.setText("I-HeLP | Message");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        drawerLayout=findViewById(R.id.drawer);
        drName = drawerLayout.findViewById(R.id.nameTV);
        drName2 = drawerLayout.findViewById(R.id.nameTV2);
        gender = drawerLayout.findViewById(R.id.genderTV);
        email = drawerLayout.findViewById(R.id.emailTV);

        homepageBtn = drawerLayout.findViewById(R.id.homepageBtn);
        messageBtn = drawerLayout.findViewById(R.id.messageBtn);
        aboutBtn = drawerLayout.findViewById(R.id.aboutBtn);
        updatePBtn = drawerLayout.findViewById(R.id.updatePBtn);

        messageBtn.setBackgroundColor(ContextCompat.getColor(this,R.color.teal));

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);

                Query query = FirebaseDatabase.getInstance().getReference("Doctor").child(id);

                query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot snapshot = task.getResult();
                            String name = snapshot.child("name").getValue().toString();
                            String genderDB = snapshot.child("gender").getValue().toString();
                            String emailDB = snapshot.child("email").getValue().toString();

                            drName.setText(name);
                            drName2.setText(name);
                            gender.setText(genderDB);
                            email.setText(emailDB);

                        }
                    }
                });
            }
        });

        homepageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMessage.this, AdminHomepage.class));
                finish();            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMessage.this, AdminAbout.class));
                finish();
            }
        });

        updatePBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMessage.this, AdminUpdateProfile.class));
                finish();
            }
        });

        //tabbed layouts for message feature
        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        viewPageAdapter.addFragment(new ChatsFragment(),"Chats");
        viewPageAdapter.addFragment(new UsersFragment(),"Patients");

        view_pager.setAdapter(viewPageAdapter);
        tab_layout.setupWithViewPager(view_pager);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminMessage.this, AdminHomepage.class));
        finish();
    }

    class ViewPageAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private  ArrayList<String> titles;

        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void  addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}