package com.example.jingjing.blogv6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup_page extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button button_signup;
    AutoCompleteTextView userid;
    AutoCompleteTextView useraccount;
    AutoCompleteTextView userpassword;
    Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        button_signup = (Button) findViewById(R.id.button_signup);
        myContext=this;
        userid = (AutoCompleteTextView)findViewById(R.id.userid);
        useraccount = (AutoCompleteTextView)findViewById(R.id.useraccount);
        userpassword = (AutoCompleteTextView)findViewById(R.id.userpassword);
//        editText_title=(EditText)  findViewById(R.id.editText_title);
//        editText_article=(EditText)  findViewById(R.id.editText_article);

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addblogger();
                Toast.makeText(myContext, "Data Added!", Toast.LENGTH_SHORT).show();

                //註冊完到登入畫面
                Intent intent = new Intent( myContext,LoginActivity.class);
                myContext.startActivity(intent);
            }
        });
    }

    private void addblogger(){
        String name = userid.getText().toString().trim();
        String account = useraccount.getText().toString().trim();
        String password = userpassword.getText().toString().trim();
        String atten = "0";
        String fan ="0";
        String money="0";
//        String name=LoginActivity.user;
//        String title=editText_title.getText().toString().trim();
//        String article=editText_article.getText().toString().trim();
//        String like="0";
//        String atten="0";
//        String fans="0";

        if (!TextUtils.isEmpty(name)) {
//Create a list
            Map<String, Object> entryToPush = new HashMap<>();
            entryToPush.put("name", name);
            entryToPush.put("account", account);
            entryToPush.put("password", password);
            entryToPush.put("atten", atten);
            entryToPush.put("fan", fan);
            entryToPush.put("money", money);


// Add a new document with a generated ID

            db.collection("Blogger")
                    .add(entryToPush)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String debugMsg = "DocumentSnapshot added with ID: " + documentReference.getId();
                            Log.d("ginger", debugMsg);
                            Toast.makeText(myContext,debugMsg, Toast.LENGTH_LONG );//彈跳視窗
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String debugMsg = "Error adding document" + e.toString();
                            //Toast.makeText(myContext,debugMsg, Toast.LENGTH_LONG );
                            Log.w("ginger", debugMsg);
                        }
                    });

        } else {
            Toast.makeText(this, "You should eat shit", Toast.LENGTH_LONG).show();
        }
    }
}
