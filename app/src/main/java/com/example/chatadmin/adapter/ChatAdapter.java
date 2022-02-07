package com.example.chatadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatadmin.R;
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
        hideVisibility(holder.binding);
        if(message.getFrom().equals(currentUser.getId())){
            myMessage(holder.binding,message);
        }else{
            friendMessage(holder.binding,message);
        }
    }
    private void friendMessage(MessageViewBinding binding,Message message){
        binding.frndmsgtime.setVisibility(View.VISIBLE);
        binding.frndmsgtime.setText(message.getTime() + " " + message.getDate());
        if(message.isFile()){
            binding.frndmsgimage.setVisibility(View.VISIBLE);
            if(message.getFileType().equals("png") || message.getFileType().equals("jpg") || message.getFileType().equals("jpeg")){
                setImage(binding.frndmsgimage,context.getResources().getDrawable(R.drawable.ic_image_flat, context.getTheme()));
            }else if(message.getFileType().equals("pdf")){
                setImage(binding.frndmsgimage,context.getResources().getDrawable(R.drawable.ic_pdf_flat, context.getTheme()));
            }else if(message.getFileType().equals("doc") || message.getFileType().equals("docx")){
                setImage(binding.frndmsgimage,context.getResources().getDrawable(R.drawable.ic_doc_flat, context.getTheme()));
            }else{
                setImage(binding.frndmsgimage,context.getResources().getDrawable(R.drawable.ic_document_error_flat, context.getTheme()));
            }
            addFileClickListener(binding.frndmsgimage,message.getMessage());
        }else {
            binding.friendMsg.setVisibility(View.VISIBLE);
            binding.friendMsg.setText(message.getMessage());
        }
    }
    private void addFileClickListener(ImageView file,String url){
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(in);
            }
        });
    }

    private void myMessage(MessageViewBinding binding,Message message){
        binding.MyMsgTime.setVisibility(View.VISIBLE);
        binding.MyMsgTime.setText(message.getTime() + " " + message.getDate());
        if(message.isFile()){
            binding.mymsgimage.setVisibility(View.VISIBLE);
            if(message.getFileType().equals("png") || message.getFileType().equals("jpg") || message.getFileType().equals("jpeg")){
                setImage(binding.mymsgimage,context.getResources().getDrawable(R.drawable.ic_image_flat,context.getTheme()));
            }else if(message.getFileType().equals("pdf")){
                setImage(binding.mymsgimage,context.getResources().getDrawable(R.drawable.ic_pdf_flat, context.getTheme()));
            }else if(message.getFileType().equals("doc") || message.getFileType().equals("docx")){
                setImage(binding.mymsgimage,context.getResources().getDrawable(R.drawable.ic_doc_flat, context.getTheme()));
            }else{
                setImage(binding.mymsgimage,context.getResources().getDrawable(R.drawable.ic_document_error_flat, context.getTheme()));
            }
            addFileClickListener(binding.mymsgimage,message.getMessage());
        }else {
            binding.MyMsg.setText(message.getMessage());
            binding.MyMsg.setVisibility(View.VISIBLE);
        }
    }

    private void setImage(ImageView img, Drawable drawable){
        img.setImageDrawable(drawable);
    }

    private void hideVisibility(MessageViewBinding binding){
        binding.frndmsgtime.setVisibility(View.GONE);
        binding.frndmsgimage.setVisibility(View.GONE);
        binding.friendMsg.setVisibility(View.GONE);
        binding.mymsgimage.setVisibility(View.GONE);
        binding.MyMsgTime.setVisibility(View.GONE);
        binding.MyMsg.setVisibility(View.GONE);
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
