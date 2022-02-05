package com.example.chatadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatadmin.activity.ChatActivity;
import com.example.chatadmin.bean.User;
import com.example.chatadmin.databinding.UserListViewBinding;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    Context context;
    ArrayList<User>users;
    User admin;


    public UserAdapter(Context context, ArrayList<User> users,User admin) {
        this.context = context;
        this.users = users;
        this.admin = admin;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserListViewBinding binding=UserListViewBinding.inflate(LayoutInflater.from(context));
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user=users.get(position);
        holder.binding.username.setText(user.getName());
        holder.binding.type.setText(user.getType());
        holder.binding.layout.setOnClickListener(view -> {
            Toast.makeText(context, user.getName(), Toast.LENGTH_SHORT).show();
            Intent in = new Intent(context, ChatActivity.class);
            in.putExtra("sender",admin);
            in.putExtra("receiver",user);
            context.startActivity(in);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        UserListViewBinding binding;
        public UserViewHolder(@NonNull UserListViewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
