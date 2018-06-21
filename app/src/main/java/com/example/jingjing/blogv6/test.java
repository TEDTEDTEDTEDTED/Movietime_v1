package com.example.jingjing.blogv6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class test extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button button_enter;
    EditText editText_title;
    EditText editText_article;
    Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendoutpage);button_enter = (Button) findViewById(R.id.button_enter);
        myContext=this;
        editText_title=(EditText)  findViewById(R.id.editText_title);
        editText_article=(EditText)  findViewById(R.id.editText_article);

        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBlog();
                Toast.makeText(myContext, "Data Added!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBlog(){
        String name=LoginActivity.user;
        String title=editText_title.getText().toString().trim();
        String article=editText_article.getText().toString().trim();
        String like="0";
        String atten="0";
        String fans="0";

        if (!TextUtils.isEmpty(title)) {
//Create a list
            Map<String, Object> entryToPush = new HashMap<>();
            entryToPush.put("name", name);
            entryToPush.put("title", title);
            entryToPush.put("article", article);
            entryToPush.put("like", like);

// Add a new document with a generated ID

            db.collection("Article_CFS")
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
