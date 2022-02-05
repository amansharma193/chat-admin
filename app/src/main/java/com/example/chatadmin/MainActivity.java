package com.example.chatadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatadmin.activity.AdminActivity;
import com.example.chatadmin.activity.ChatActivity;
import com.example.chatadmin.bean.User;
import com.example.chatadmin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    DatabaseReference rootreference;
    ArrayList<User>users;
    int idx=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =ActivityMainBinding.inflate(LayoutInflater.from(MainActivity.this));
        setContentView(binding.getRoot());

        rootreference= FirebaseDatabase.getInstance().getReference();
//        Log.d("memeberkey", "onCreate: ");
        rootreference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("memeberkey", "snapshot: ");
                if(snapshot.exists()){
                    users=new ArrayList<>();
                    Iterator<DataSnapshot> itr=snapshot.getChildren().iterator();
//                    Log.d("memeberkey", "onDataChange: loop");
                    while(itr.hasNext()){
                        DataSnapshot ds=itr.next();
                        User user=(User) ds.getValue(User.class);
                        users.add(user);
                        if(user.getType().equals("admin")){
                            idx=users.size()-1;
                        }
//                        Log.d("memeberkey", "onDataChange: "+users);
                    }
                    binding.btnuser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "user", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                            intent.putExtra("receiver",users.get(idx));
                            intent.putExtra("sender",users.get(0));
                            startActivity(intent);
                        }
                    });
                    binding.btnadmin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "admin", Toast.LENGTH_SHORT).show();
//                            Log.d("doubtful", idx+"onClick: "+ users.get(idx));
                            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                            intent.putExtra("admin",users.get(idx));
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}