package com.example.chatadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatadmin.bean.Message;
import com.example.chatadmin.bean.User;
import com.example.chatadmin.databinding.MessageViewBinding;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    Context context;
    ArrayList<Message> messages;
    User currentUser;

    public ChatAdapter(Context context, ArrayList<Message> messages,User currentUser) {
        this.context = context;
        this.messages = messages;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MessageViewBinding messageViewBinding = MessageViewBinding.inflate(LayoutInflater.from(context));
        return new ChatViewHolder(messageViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = messages.get(position);
        if(message.getFrom().equals(currentUser.getId())){
            holder.binding.frndmsgtime.setVisibility(View.GONE);
            holder.binding.friendMsg.setVisibility(View.GONE);
            holder.binding.MyMsg.setVisibility(View.VISIBLE);
            holder.binding.MyMsgTime.setVisibility(View.VISIBLE);
            holder.binding.MyMsgTime.setText(message.getTime() + " " + message.getDate());
            holder.binding.MyMsg.setText(message.getMessage());
        }else{
            holder.binding.MyMsgTime.setVisibility(View.GONE);
            holder.binding.MyMsg.setVisibility(View.GONE);
            holder.binding.friendMsg.setVisibility(View.VISIBLE);
            holder.binding.frndmsgtime.setVisibility(View.VISIBLE);
            holder.binding.frndmsgtime.setText(message.getTime() + " " + message.getDate());
            holder.binding.friendMsg.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ChatViewHolder extends RecyclerView.ViewHolder {
        MessageViewBinding binding;
        public ChatViewHolder(@NonNull MessageViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
