package com.example.jingjing.blogv6;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class tabc extends RelativeLayout {
    private Context myContext;
    private Context mainContext;
    private View view02;
    private Button button1;
    private Button button2;
    int buttonNum;

    RecyclerView recyclerView;
    //ListView listView;
    private SearchView searchView;
    String search;

//抄範例
    ArrayList<Article> articleDB;
    //ArrayList<Attention> attenName;
    ArrayList<Attention> attentionDB;
    ArrayList<Blogger> atten;
    ArrayList<Blogger> bloggerDB;
    ArrayList<String> pics;
    ArrayList<String> atten_name;
    ArrayList<Blogger> unatten;

    private final Lock lock = new ReentrantLock();
    private final Condition dbReady = lock.newCondition();


    public void setMainContext(Context mainContext) {
        this.mainContext = mainContext;
    }
    public tabc(Context context) {
        super(context);
        myContext = context;
        view02 = LayoutInflater.from(myContext).inflate(R.layout.tabc, null);
        addView(view02);

        button1=(Button) findViewById(R.id.atten);
        button2=(Button) findViewById(R.id.unatten);
        searchView=(SearchView) findViewById(R.id.searchtabc);

        //listView=(ListView) findViewById(R.id.attenlist);
        //search = searchView.getQuery().toString();//get string in searchview

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openest();
                getAttention();
                Log.e("ooo", "onClick registered for tabc class");


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openest();
                //getArticleName();
                Log.e("ooo", "onClick registered for tabd class");


            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search=searchView.getQuery().toString();
                //getArticleCFS();
                Log.e("jjjjjj","OMG");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    public void getAttention(){
        Toast.makeText(myContext, "Reading database......", Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        attentionDB = new ArrayList<>();
        db.collection("Attention")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("ooo", "getAttention running");
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("ooo", document.getId() + " => " + document.getData());
                                JSONObject tmpJsonobj = new JSONObject(document.getData());
                                // Convert to Movie class
                                try {
                                    Attention attention = new Attention(
                                            tmpJsonobj.getString("owner"),
                                            tmpJsonobj.getString("blogger name")
                                    );
                                    attentionDB.add(attention);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            getBlogger();
                            //Log.e("ooo", "start before databaseReady1");
                            //databaseReady1();
                            //Log.e("ooo", "after databaseReady1");
                        } else {
                            Log.w("itspeter", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    public void getBlogger(){
        Toast.makeText(myContext, "Reading database ......", Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        bloggerDB = new ArrayList<>();

        //articletest = new ArrayList<>();

        db.collection("Blogger")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("help", document.getId() + " => " + document.getData());
                                JSONObject tmpJsonobj = new JSONObject(document.getData());
                                // Convert to Movie class
                                try {
                                    Blogger blogger = new Blogger(
                                            tmpJsonobj.getString("name"),
                                            tmpJsonobj.getString("account"),
                                            tmpJsonobj.getString("password"),
                                            tmpJsonobj.getString("pic"));
                                    bloggerDB.add(blogger);
                                    Log.e("help", "After adding. bloggerDB size =" + bloggerDB.size());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            databaseReady_atten();
                            //getArticleCFS2();
                        } else {
                            Log.w("itspeter", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void databaseReady_atten(){

        Log.e("help","bloggerDB.size() = "+bloggerDB.size());
        Log.e("help","attentionDB.size() = "+attentionDB.size());


        atten = new ArrayList<>();
        //unatten = new ArrayList<>();
        for(int i=0;i<attentionDB.size();++i) {
            String owner = attentionDB.get(i).getOwner();
            Log.e("help","attentionDB.get(i).getOwner() = " + owner +"|");
            if (owner.equals(LoginActivity.user)) {
                String attenBloggerName = attentionDB.get(i).getBlgger_name();
                Log.e("help","attentionDB.get(i).getBlgger_name() = " + attenBloggerName + "|");
                for (int j=0; j< bloggerDB.size(); ++j) {
                    String bloggerName = bloggerDB.get(j).getName();
                    Log.e("help","bloggerDB.get(j).getName() = " + bloggerName + "|");
                    if  (bloggerName.equals(attenBloggerName)) {
                        atten.add(bloggerDB.get(j));
                    }
                    else {
                        //unatten.add(bloggerDB.get(j));
                    }
                }
            }
        }
        Log.e("help","attensize="+atten.size());
        pics = new ArrayList<>();
        atten_name = new ArrayList<>();
        for(int i=0;i<atten.size();i++){
            pics.add(atten.get(i).getPic());
            atten_name.add(atten.get(i).getName());
        }
        Attenlist adapteratten = new Attenlist(myContext,pics,atten_name);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_myatten);
        recyclerView.setAdapter(adapteratten);
        recyclerView.setLayoutManager(new LinearLayoutManager(myContext));
    }

//    public void databaseReady_unatten(){
//
//        unatten = new ArrayList<>();
//        for(int i=0;i<attentionDB.size();++i) {
//            String owner = attentionDB.get(i).getOwner();
//            Log.e("help","attentionDB.get(i).getOwner() = " + owner +"|");
//            if (owner.equals(LoginActivity.user)) {
//                String attenBloggerName = attentionDB.get(i).getBlgger_name();
//                Log.e("help","attentionDB.get(i).getBlgger_name() = " + attenBloggerName + "|");
//                for (int j=0; j< bloggerDB.size(); ++j) {
//                    String bloggerName = bloggerDB.get(j).getName();
//                    Log.e("help","bloggerDB.get(j).getName() = " + bloggerName + "|");
//                    if  (bloggerName.equals(attenBloggerName)) {
//                        atten.add(bloggerDB.get(j));
//                    }
//                    else {
//                        unatten.add(bloggerDB.get(j));
//                    }
//                }
//            }
//        }
//    }
}

    /*public void databaseReady() {
        Log.e("itspeter", "databaseReady: What's the size ?" + articleDB.size());
        Toast.makeText(myContext, "Database reading done !", Toast.LENGTH_SHORT).show();

        articlePeter = new ArrayList<>();
        articleGinger = new ArrayList<>();
        articletest = new ArrayList<>();


        for (int i=0; i<articleDB.size(); ++i) {
            Log.e("ooo", "|" + articleDB.get(i).getName() + "|");
            if (articleDB.get(i).getName().equals(LoginActivity.user))
                articleGinger.add(articleDB.get(i));
        }

//        for (int i=0; i<articleDB.size(); ++i) {
//            Log.e("ooo", "|" + articleDB.get(i).getName() + "|");
//            if (articleDB.get(i).getName().equals("Peter")) {
//                articlePeter.add(articleDB.get(i));
//            }
//        }

        for (int i=0; i<articleDB.size(); ++i) {
            Log.e("ooo", "|" + articleDB.get(i).getName() + "|");
            if (articleDB.get(i).getName().equals(search)) {
                articletest.add(articleDB.get(i));
            }
        }


//        /*ArrayAdapter adapterDB = new Bloglist_myblog((Activity) myContext, articleDB);
//        ArrayAdapter adapterGinger = new Bloglist_myblog((Activity) myContext, articleGinger);
//        ArrayAdapter adapterPeter = new Bloglist_myblog((Activity) myContext, articlePeter);*/
        //ArrayAdapter adaptertest =  new Bloglist_myblog((Activity) myContext, articletest);

        //mybloglist.setAdapter(adapterDB);
        //mybloglist.setAdapter(adapterGinger);
        //listView.setAdapter(adapterPeter);
//        listView.setAdapter(adaptertest);
//    }

//    public void databaseReady1() {
//        Log.e("ooo", "Thread End. What's the size ?" + attentionDB.size());
//        Toast.makeText(myContext, "Database reading done !", Toast.LENGTH_SHORT).show();
//        attenName = new ArrayList<>();
//        for(int i=0;i<attentionDB.size();++i){
//            Log.e("ooo", "|" + attentionDB.get(i).getOwner() + "|" + attentionDB.get(i).getBlgger_name() + "|");
//            if (attentionDB.get(i).getOwner().equals(LoginActivity.user)){//if user name="Ginger"
//                attenName.add(attentionDB.get(i));
//            }
//        }
//
////        for(int i=0;i<attenName.size();++i){
////            Log.e("ooo", " Found - |" + attenName.get(i).getOwner() + "|" + attenName.get(i).getBlgger_name() + "|");
////        }
//        //ArrayAdapter adapterPeter = new Bloglist_myblog((Activity) myContext, articlePeter);
//
//        ArrayAdapter adapterName = new Attenlist((Activity) myContext,attenName);
//
//        //listView.setAdapter(adapterPeter);
//        listView.setAdapter(adapterName);
//
//    }

//    public void databaseReady2() {
//        //Log.e("itspeter", "databaseReady： What's the articleDB size ?" + articleDB.size());
//        //Log.e("itspeter", "databaseReady： What's the attentionDB size ?" + attentionDB.size());
//
//        Toast.makeText(myContext, "Database reading done !", Toast.LENGTH_SHORT).show();
//
//        attenName = new ArrayList<>();
//
////        for (int i=0; i<articleDB.size(); ++i) {
////            Log.e("ooo", "|" + articleDB.get(i).getName() + "|");
////            if (articleDB.get(i).getName().equals("Ginger")) {
////                articletest.add(articleDB.get(i));
////            }
////        }
//
//        for(int i=0;i<attentionDB.size();++i){
//            Log.e("CCC", "|" + attentionDB.get(i).getOwner() + "|" + attentionDB.get(i).getBlgger_name() + "|");
//            if (attentionDB.get(i).getOwner().equals(LoginActivity.user)){//if user name="Ginger"
//                attenName.add(attentionDB.get(i));
//            }
//        }
//        for(int i=0;i<attenName.size();++i){
//            for (int j=0;j<articleDB.size();++j){
//                if (articleDB.get(j).getName().equals(attenName.get(i).getBlgger_name()) || articleDB.get(j).getName().equals(LoginActivity.user)){//if user name="Ginger"
//                    articletest.remove(articleDB.get(j));
//                }else {
//                    //articletest.add(articleDB.get(j));
//                }
//            }
//        }
//
////        Name=new ArrayList<>();
////        for(int i=0;i<articletest.size();i++){
////            Name.add(articletest.get(i).getName().toString());
////        }
//        //ArrayAdapter adaptername = new Attenlist((Activity) myContext,Name);
//
//        ArrayAdapter adapterName = new UnAttenlist((Activity) myContext,articletest);
//        //ArrayAdapter adapterName = new Attenlist((Activity) myContext,attenName);
//
//
//        //ArrayAdapter adaptertest =  new Bloglist_myblog((Activity) myContext, articletest);
//        listView.setAdapter(adapterName);
//    }



//    public void getArticleCFS() {
//        // Filter example also at: https://firebase.google.com/docs/firestore/query-data/queries
//        // https://medium.com/google-developers/why-are-firebase-apis-asynchronous-callbacks-promises-tasks-e037a6654a93
//        Toast.makeText(myContext, "Reading database......", Toast.LENGTH_SHORT).show();
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        articleDB = new ArrayList<>();
//        db.collection("Article_CFS")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d("itspeter", document.getId() + " => " + document.getData());
//                                JSONObject tmpJsonobj = new JSONObject(document.getData());
//                                // Convert to Movie class
//                                try {
//                                    Article article = new Article(
//                                            tmpJsonobj.getString("name"),
//                                            "標題"+tmpJsonobj.getString("title"),
//                                            "文章"+tmpJsonobj.getString("article"),
//                                            "熱度"+tmpJsonobj.getString("like"));
//                                    articleDB.add(article);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            databaseReady();
//                        } else {
//                            Log.w("itspeter", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//    }

//    public void getArticleCFS1() {
//        // Filter example also at: https://firebase.google.com/docs/firestore/query-data/queries
//        // https://medium.com/google-developers/why-are-firebase-apis-asynchronous-callbacks-promises-tasks-e037a6654a93
//        Toast.makeText(myContext, "Reading database......", Toast.LENGTH_SHORT).show();
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        attentionDB = new ArrayList<>();
//        db.collection("Attention")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("ooo", "getArticleCFS1 running");
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d("ooo", document.getId() + " => " + document.getData());
//                                JSONObject tmpJsonobj = new JSONObject(document.getData());
//                                // Convert to Movie class
//                                try {
//                                    Attention attention = new Attention(
//                                            tmpJsonobj.getString("owner"),
//                                            //tmpJsonobj.getString("article"),
//                                            tmpJsonobj.getString("blogger name")
//                                    );
//                                    attentionDB.add(attention);
//                                    //待註解
//                                    /*Article article = new Article(
//                                            tmpJsonobj.getString("name"),
//                                            "標題"+tmpJsonobj.getString("title"),
//                                            "文章"+tmpJsonobj.getString("article"),
//                                            "熱度"+tmpJsonobj.getString("like"));
//                                    articleDB.add(article);*/
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            Log.e("ooo", "start before databaseReady1");
//                            databaseReady1();
//                            Log.e("ooo", "after databaseReady1");
//                        } else {
//                            Log.w("itspeter", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//    }

//    public void getArticleName(){
//        // Filter example also at: https://firebase.google.com/docs/firestore/query-data/queries
//        // https://medium.com/google-developers/why-are-firebase-apis-asynchronous-callbacks-promises-tasks-e037a6654a93
//        Toast.makeText(myContext, "Reading database ......", Toast.LENGTH_SHORT).show();
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        articleDB = new ArrayList<>();
//        articletest = new ArrayList<>();
//
//        db.collection("Article_CFS")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d("itspeter", document.getId() + " => " + document.getData());
//                                JSONObject tmpJsonobj = new JSONObject(document.getData());
//                                // Convert to Movie class
//                                try {
//                                    Log.e("ppp","omgomg");
//                                    Article article = new Article(
//                                            tmpJsonobj.getString("name"),
//                                            tmpJsonobj.getString("title"),
//                                            tmpJsonobj.getString("article"),
//                                            "\n熱度  ("+tmpJsonobj.getString("like")+")");
//                                    articleDB.add(article);
//                                    articletest.add(article);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                if (articleDB==null){
//                                    Log.e("xxx","omgomg");
//                                }else {
//                                    Log.e("kkk","omgomg");
//                                }
//                            }
//                            getArticleCFS2();
//                        } else {
//                            Log.w("itspeter", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//
//    }
//    public void getArticleCFS2() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        attentionDB =new ArrayList<>();
//        db.collection("Attention")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("ooo", "getArticleCFS1 running");
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d("ooo", document.getId() + " => " + document.getData());
//                                JSONObject tmpJsonobj = new JSONObject(document.getData());
//                                // Convert to Movie class
//                                try {
//                                    Attention attention = new Attention(
//                                            tmpJsonobj.getString("owner"),
//                                            //tmpJsonobj.getString("article"),
//                                            tmpJsonobj.getString("blogger name")
//                                    );
//                                    attentionDB.add(attention);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            //Log.e("ooo", "start before databaseReady1");
//                            databaseReady2();
//                            //Log.e("ooo", "after databaseReady1");
//                        } else {
//                            Log.w("itspeter", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//    }


