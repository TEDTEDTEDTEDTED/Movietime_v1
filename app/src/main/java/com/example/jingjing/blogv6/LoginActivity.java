package com.example.jingjing.blogv6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private Button button;
    private Button signup;
    private Button newpw;
    private TextView textView;
    private Context myContext;
    private Context mainContext;
    private AutoCompleteTextView accountAutoCompleteTextView;
    private AutoCompleteTextView passwordAutoCompleteTextView;
    ArrayList<Blogger> bloggerDB;
    ArrayList<Blogger> bloggeraccount;
    public static String user=null;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.logincheck);
        setContentView(R.layout.activity_login);
        myContext=this;
        mainContext=this;
//        Object xxx = findViewById(R.id.username);
//        textView=(TextView) findViewById(R.id.username);

        signup=(Button)findViewById(R.id.signup) ;
        newpw=(Button)findViewById(R.id.newpassword);
        button=(Button)findViewById(R.id.button_signin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBlogger();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ppp", "click button");
                Intent intent = new Intent(mainContext, signup_page.class);
                mainContext.startActivity(intent);
                Log.e("ppp", "Button clicked");

            }
        });
    }


    public void getBlogger() {
        // Filter example also at: https://firebase.google.com/docs/firestore/query-data/queries
        // https://medium.com/google-developers/why-are-firebase-apis-asynchronous-callbacks-promises-tasks-e037a6654a93
        Toast.makeText(myContext, "Checking login information ......", Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        bloggerDB = new ArrayList<>();

        db.collection("Blogger")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("itspeter", document.getId() + " => " + document.getData());
                                JSONObject tmpJsonobj = new JSONObject(document.getData());
                                // Convert to Movie class
                                try {
                                    //Log.e("ppp","omgomg");
                                    Blogger blogger = new Blogger(
                                            tmpJsonobj.getString("name"),
                                            tmpJsonobj.getString("account"),
                                            tmpJsonobj.getString("password"),
                                            tmpJsonobj.getString("pic"));
                                    bloggerDB.add(blogger);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            verifyLoginInformation();
                        } else {
                            Log.w("itspeter", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    public void verifyLoginInformation() {
        Toast.makeText(myContext, "Login information checked!", Toast.LENGTH_SHORT).show();
        accountAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.loginAccount);
        passwordAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.loginPassword);
        boolean loginOkay = false;

        for(int i=0;i<bloggerDB.size();++i){
            Log.e("fff", "What's my password => |" + bloggerDB.get(i).getAccount() + "|" + bloggerDB.get(i).getPassword() + "|");
            Log.e("fff", "What's my input => |" + accountAutoCompleteTextView.getText() + "|" + accountAutoCompleteTextView.getText() + "|");
            if (bloggerDB.get(i).getAccount().equalsIgnoreCase(accountAutoCompleteTextView.getText().toString()) &&
                bloggerDB.get(i).getPassword().equalsIgnoreCase(passwordAutoCompleteTextView.getText().toString())){
                //if user name="ggg"
                user = bloggerDB.get(i).getName();
                Log.e("fff","get user name if");
                loginOkay = true;
                break;
            }else{
                Log.e("fff","get user name else");
            }
        }

        if (loginOkay) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(myContext, "辣雞！\n不要偷用別人的帳號!", Toast.LENGTH_SHORT).show();
            //passwordAutoCompleteTextView.clear();
        }
    }

}
