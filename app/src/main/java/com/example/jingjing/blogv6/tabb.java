package com.example.jingjing.blogv6;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class tabb extends RelativeLayout{

    private Context myContext;
    private Context mainContext;
    private View view02;
    ListView listView;
    private SearchView searchView;
    String search;
    //抄範例
    ArrayList<Article> articleDB;
    ArrayList<Article> articletest;
    ArrayList<Attention> attenName;
    ArrayList<Attention> attentionDB;

    public tabb(Context context) {
        super(context);
        myContext = context;
        view02 = LayoutInflater.from(myContext).inflate(R.layout.tabb, null);
        addView(view02);

        searchView=(SearchView) findViewById(R.id.searchtabb);
        //searchView=(SearchView) findViewById(R.id.searchtabc);
        listView=(ListView) findViewById(R.id.listview_tabb);

        getArticleCFS();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search=searchView.getQuery().toString();
                getArticleCFS_search();
                Log.e("jjjjjj","OMG");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }
    public void getArticleCFS() {
        // Filter example also at: https://firebase.google.com/docs/firestore/query-data/queries
        // https://medium.com/google-developers/why-are-firebase-apis-asynchronous-callbacks-promises-tasks-e037a6654a93
        Toast.makeText(myContext, "Reading database ......", Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        articleDB = new ArrayList<>();
        articletest = new ArrayList<>();

        db.collection("Article_CFS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("tabb", document.getId() + " => " + document.getData());
                                JSONObject tmpJsonobj = new JSONObject(document.getData());
                                // Convert to Movie class
                                try {
                                    Log.e("tabb","Chinese desuka? " + tmpJsonobj.getString("name") + "|");
                                    Article article = new Article(
                                            tmpJsonobj.getString("name"),
                                            tmpJsonobj.getString("title"),
                                            tmpJsonobj.getString("article"),
                                            "\n熱度  ("+tmpJsonobj.getString("like")+")");
                                    articleDB.add(article);
                                    articletest.add(article);
                                } catch (JSONException e) {
                                    Log.e("tabb","Why missed one data");
                                    e.printStackTrace();
                                }
                                if (articleDB==null){
                                    Log.e("xxx","omgomg");
                                }else {
                                    Log.e("kkk","omgomg");
                                }
                            }
                            getArticleCFS_attention();
                        } else {
                            Log.w("itspeter", "Error getting documents.", task.getException());
                        }
                    }
                });

    }
    public void getArticleCFS_attention(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        attentionDB =new ArrayList<>();
        db.collection("Attention")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("ooo", "getArticleCFS1 running");
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("ooo", document.getId() + " => " + document.getData());
                                JSONObject tmpJsonobj = new JSONObject(document.getData());
                                // Convert to Movie class
                                try {
                                    Attention attention = new Attention(
                                            tmpJsonobj.getString("owner"),
                                            //tmpJsonobj.getString("article"),
                                            tmpJsonobj.getString("blogger name")
                                    );
                                    attentionDB.add(attention);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            //Log.e("ooo", "start before databaseReady1");
                            databaseReady();
                            //Log.e("ooo", "after databaseReady1");
                        } else {
                            Log.w("itspeter", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void getArticleCFS_search() {
        // Filter example also at: https://firebase.google.com/docs/firestore/query-data/queries
        // https://medium.com/google-developers/why-are-firebase-apis-asynchronous-callbacks-promises-tasks-e037a6654a93
        Toast.makeText(myContext, "Reading database ......", Toast.LENGTH_SHORT).show();

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
                                            "\n熱度"+tmpJsonobj.getString("like"));
                                    articleDB.add(article);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            databaseReady_search();
                        } else {
                            Log.w("itspeter", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void databaseReady() {
        Log.e("itspeter", "databaseReady： What's the articleDB size ?" + articleDB.size());
        Log.e("itspeter", "databaseReady： What's the attentionDB size ?" + attentionDB.size());

        Toast.makeText(myContext, "Database reading done !", Toast.LENGTH_SHORT).show();

        attenName = new ArrayList<>();

//        for (int i=0; i<articleDB.size(); ++i) {
//            Log.e("ooo", "|" + articleDB.get(i).getName() + "|");
//            if (articleDB.get(i).getName().equals("Ginger")) {
//                articletest.add(articleDB.get(i));
//            }
//        }

        for(int i=0;i<attentionDB.size();++i){
            Log.e("CCC", "|" + attentionDB.get(i).getOwner() + "|" + attentionDB.get(i).getBlgger_name() + "|");
            if (attentionDB.get(i).getOwner().equals(LoginActivity.user)){//if user name="Ginger"
                attenName.add(attentionDB.get(i));
            }
        }
        for(int i=0;i<attenName.size();++i){
            for (int j=0;j<articleDB.size();++j){
                if (articleDB.get(j).getName().equals(attenName.get(i).getBlgger_name()) || articleDB.get(j).getName().equals(LoginActivity.user)){//if user name="Ginger"
                    articletest.remove(articleDB.get(j));
                }else {
                    //articletest.add(articleDB.get(j));
                }
            }
        }
        ArrayAdapter adaptertest =  new Bloglist_myblog((Activity) myContext, articletest);
        listView.setAdapter(adaptertest);
    }
    public void databaseReady_search() {
        Log.e("itspeter", "databaseReady_search. What's the size ?" + articleDB.size());
        Toast.makeText(myContext, "Database reading done !", Toast.LENGTH_SHORT).show();
        articletest = new ArrayList<>();
        for (int i=0; i<articleDB.size(); ++i) {
            Log.e("ooo", "|" + articleDB.get(i).getName() + "|");
            if (articleDB.get(i).getName().equals(search)) {
                articletest.add(articleDB.get(i));
            }
        }
        ArrayAdapter adaptertest =  new Bloglist_myblog((Activity) myContext, articletest);
        listView.setAdapter(adaptertest);
    }

}
