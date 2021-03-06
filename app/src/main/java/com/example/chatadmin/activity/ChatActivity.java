package com.example.chatadmin.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.chatadmin.adapter.ChatAdapter;
import com.example.chatadmin.bean.Message;
import com.example.chatadmin.bean.User;
import com.example.chatadmin.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    User sender,receiver;
    DatabaseReference databaseReference;
    ChatAdapter chatAdapter;
    ArrayList<Message>messages = new ArrayList<>();
    StorageReference filepath,storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(LayoutInflater.from(ChatActivity.this));
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Intent in = getIntent();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        filepath= FirebaseStorage.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference("Documents");
        sender = (User) in.getSerializableExtra("sender");
        receiver = (User) in.getSerializableExtra("receiver");
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.message.getText().toString();
                binding.message.setText("");
                if(TextUtils.isEmpty(message)){
                    binding.message.setError("this field can't be empty");
                    return;
                }
                String key = databaseReference.child("Messages").child(sender.getId()).child(receiver.getId()).push().getKey();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sd = new SimpleDateFormat("MMM dd, yyyy");
                String date = sd.format(c.getTime());
                sd = new SimpleDateFormat("hh:mm a");
                String time = sd.format(c.getTime());
                Message obj=new Message(message,key,sender.getId(),receiver.getId(),date,time,c.getTimeInMillis(),false,null);
                sendMessage(key,obj);
            }
        });
        binding.btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Intent.ACTION_GET_CONTENT);
                in.setType("*/*");
                startActivityForResult(Intent.createChooser(in,"select pdf file"),333);
            }
        });
        binding.messageRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        chatAdapter = new ChatAdapter(ChatActivity.this,messages,sender);
        binding.messageRecyclerView.setAdapter(chatAdapter);
        databaseReference.child("Messages").child(sender.getId()).child(receiver.getId()).orderByChild("timestamp").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    Message msg = snapshot.getValue(Message.class);
                    messages.add(msg);
                    chatAdapter.notifyDataSetChanged();
                    binding.messageRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 333 && data!=null && data.getData()!=null){
            Uri fileUri = data.getData();
            ContentResolver cR = this.getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getExtensionFromMimeType(cR.getType(fileUri));
//            Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
            sendDocumentMessage(fileUri,type);
        }
    }

    private void sendDocumentMessage(Uri fileUri, final String type) {
        final String msgid=databaseReference.child("Messages").push().getKey();
        StorageReference filepath=storageReference.child(msgid+"."+type);
        filepath.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String fileurl=uri.toString();
                        Calendar c=Calendar.getInstance();
                        SimpleDateFormat sd=new SimpleDateFormat("MMM dd, yyyy");
                        String date=sd.format(c.getTime());
                        sd=new SimpleDateFormat("hh:mm a");
                        String time=sd.format(c.getTime());
                        Message message = new Message(fileurl,msgid,sender.getId(),receiver.getId(),date,time,c.getTimeInMillis(),true,type);
                        sendMessage(msgid,message);
                    }
                });


            }
        });
    }

    private void sendMessage(String key,Message obj){
        databaseReference.child("Messages").child(sender.getId()).child(receiver.getId()).child(key).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    databaseReference.child("Messages").child(receiver.getId()).child(sender.getId()).child(key).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ChatActivity.this, "message sent...", Toast.LENGTH_SHORT).show();
                            }else Toast.makeText(ChatActivity.this, "An error occured...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else Toast.makeText(ChatActivity.this, "An error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}