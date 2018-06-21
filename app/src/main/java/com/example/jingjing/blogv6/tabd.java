package com.example.jingjing.blogv6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class tabd extends RelativeLayout {
    private Button button;
    private Context myContext;
    private Context mainContext;
    private View view01;

    Button button2;
    ListView mybloglist;

    ArrayList<Article> articleDB;
    ArrayList<Article> articleuser;
    ArrayList<Article> articleGinger;



    public void setMainContext(Context mainContext) {
        this.mainContext = mainContext;
    }

    public tabd(Context context) {
        super(context);
        myContext = context;
        view01 = LayoutInflater.from(myContext).inflate(R.layout.tabd, null);
        addView(view01);

        button = (Button)findViewById(R.id.button);
        //button2= (Button)findViewById(R.id.button2);
        mybloglist=(ListView)findViewById(R.id.mybloglist);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openest();
                Log.e("ooo", "onClick registered for tabd class");
            }
        });

        getArticleCFS();
        // FINISH ALL THE THINGS, AND WAIT FOR THE DATABASEREADY
    }

    public void databaseReady() {
        Log.e("itspeter", "Thread End. What's the size ?" + articleDB.size());
        Toast.makeText(myContext, "Database reading done !", Toast.LENGTH_SHORT).show();

        articleuser = new ArrayList<>();




        for (int i=0; i<articleDB.size(); ++i) {
            Log.e("ooo", "|" + articleDB.get(i).getName() + "|");
            if (articleDB.get(i).getName().equals(LoginActivity.user)) {
                articleuser.add(articleDB.get(i));
            }
        }


        //ArrayAdapter adapterDB = new Bloglist_myblog((Activity) myContext, articleDB);
        //ArrayAdapter adapterGinger = new Bloglist_myblog((Activity) myContext, articleGinger);
        ArrayAdapter adapteruser = new Bloglist_myblog((Activity) myContext, articleuser);

        //mybloglist.setAdapter(adapterDB);
        //mybloglist.setAdapter(adapterGinger);
        mybloglist.setAdapter(adapteruser);
    }

    public void openest() {
        Log.e("kkk", "Button clicked");
        //removeAllViews();
        //addView(view02);

        Intent intent = new Intent(myContext, test.class);
        myContext.startActivity(intent);
        //button.setText("I'm clicked!");
    }


    public void getArticleCFS() {
        // Filter example also at: https://firebase.google.com/docs/firestore/query-data/queries
        // https://medium.com/google-developers/why-are-firebase-apis-asynchronous-callbacks-promises-tasks-e037a6654a93
        Toast.makeText(myContext, "Reading database......", Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        articleDB = new ArrayList<>();
        db.collection("Article_CFS")
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
                                    Article article = new Article(
                                            tmpJsonobj.getString("name"),
                                            tmpJsonobj.getString("title"),
                                            tmpJsonobj.getString("article"),
                                            "\n熱度  ("+tmpJsonobj.getString("like")+")");
                                    articleDB.add(article);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            databaseReady();
                        } else {
                            Log.w("itspeter", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}


class tabd1 extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button button2;
    ListView mybloglist;
    List<Article> Article;
    Context myContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabd);


    }
}
