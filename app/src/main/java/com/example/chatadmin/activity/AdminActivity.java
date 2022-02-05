package com.example.chatadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.chatadmin.MainActivity;
import com.example.chatadmin.R;
import com.example.chatadmin.adapter.UserAdapter;
import com.example.chatadmin.bean.User;
import com.example.chatadmin.databinding.ActivityAdminBinding;
import com.example.chatadmin.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    DatabaseReference databaseReference;
    ArrayList<User>users;
    UserAdapter userAdapter;
    User admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in = getIntent();
        admin = (User) in.getSerializableExtra("admin");
//        Log.d("admin", "onCreate: "+admin);
        binding = ActivityAdminBinding.inflate(LayoutInflater.from(AdminActivity.this));
        setContentView(binding.getRoot());
        binding.userListView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").orderByChild("type").equalTo("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    users=new ArrayList<>();
                    Iterator<DataSnapshot> itr=snapshot.getChildren().iterator();
//                    Log.d("memeberkey", "onDataChange: loop");
                    while(itr.hasNext()){
                        DataSnapshot ds=itr.next();
                        users.add(ds.getValue(User.class));
//                        Log.d("memeberkey", "onDataChange: "+users);
                    }
//                    binding.textView2.setText("admin : "+users.toString());
//                    Log.d("doubtful", "onDataChange: "+admin);
                    userAdapter=new UserAdapter(AdminActivity.this,users,admin);
                    binding.userListView.setAdapter(userAdapter);
                    userAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}