package com.example.jingjing.blogv6;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetailedActivity extends AppCompatActivity {

    private TextView nameTextview;
    private TextView kindTextview;
    private TextView dateTextview;
    private TextView runtimeTextview;
    private TextView plotTextview;
    private ImageView pictureImageView;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private ActorAdapter adapter;
    private TrailerAdapter adapter1;
    private WebView webView;
    private FirebaseFirestore db;
    private Query query;

    public void findviews() {
        nameTextview = (TextView) findViewById(R.id.movie_name);
        kindTextview = (TextView) findViewById(R.id.movie_type);
        dateTextview = (TextView) findViewById(R.id.movie_date);
        runtimeTextview = (TextView) findViewById(R.id.movie_length);
        plotTextview = (TextView) findViewById(R.id.movie_introd);
        pictureImageView = (ImageView) findViewById(R.id.movie_image);
        recyclerView = (RecyclerView) findViewById(R.id.actor_recycler);
        recyclerView1 = (RecyclerView) findViewById(R.id.trailer_recycler);
        webView = (WebView) findViewById(R.id.trailer_webview);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detailed);
        findviews();
        //顯示電影名稱
        Toast.makeText(MovieDetailedActivity.this, "電影名稱:" + getIntent().getStringExtra("position"), Toast.LENGTH_LONG).show();
        //電影的詳細資料
        setMovieDetailed();
        //演員
        setActorRecyclerview();
        //預告片
        setTrailerRecyclerview();
    }

    //預告片
    public void setTrailerRecyclerview() {
        ArrayList<Trailer> TrailerArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        query = db.collection("trailer")
                .whereEqualTo("movie", getIntent().getStringExtra("position"));
        query.get().addOnCompleteListener(task -> {
            QuerySnapshot querySnapshot = task.isSuccessful() ? task.getResult() : null;

            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                Trailer trailer = documentSnapshot.toObject(Trailer.class);
                TrailerArrayList.add(trailer);
                // Toast.makeText(this, trailer.getTrailer()+"HAHAGG", Toast.LENGTH_LONG).show();
                adapter1 = new TrailerAdapter(TrailerArrayList);
                recyclerView1.setAdapter(adapter1);
                //recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView1.setLayoutManager(new LinearLayoutManager(this));
                //  setupRecyclerView(movieArrayList);
            }
        });
    }

    //演員
    public void setActorRecyclerview() {
        ArrayList<Actor> ActorArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        query = db.collection("actor")
                .whereEqualTo("movie", getIntent().getStringExtra("position"));
        query.get().addOnCompleteListener(task -> {
            QuerySnapshot querySnapshot = task.isSuccessful() ? task.getResult() : null;
            //Toast.makeText(this, getIntent().getStringExtra("position"), Toast.LENGTH_LONG).show();
            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                Actor actor = documentSnapshot.toObject(Actor.class);
                ActorArrayList.add(actor);
                adapter = new ActorAdapter(ActorArrayList);
                recyclerView.setAdapter(adapter);
                //recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                //  setupRecyclerView(movieArrayList);
            }
        });

    }

     //電影的詳細資料
    public void setMovieDetailed() {
        ArrayList<MoviesDetailed> MoviesDetailedArrayList = new ArrayList<>();
        // Intent intent = new Intent();
        // DocumentReference dbb =db.collection("MovieDetailed");
        query = db.collection("MovieDetailed")
                .whereEqualTo("name", getIntent().getStringExtra("position"));
        query.get().addOnCompleteListener(task -> {
            QuerySnapshot querySnapshot = task.isSuccessful() ? task.getResult() : null;
            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                MoviesDetailed moviesDetailed = documentSnapshot.toObject(MoviesDetailed.class);
                MoviesDetailedArrayList.add(moviesDetailed);
                //adapter = new NewAdapter(NewArrayList);
                nameTextview.setText(moviesDetailed.getName());
                kindTextview.setText(moviesDetailed.getKind());
                dateTextview.setText(moviesDetailed.getDate());
                runtimeTextview.setText(String.valueOf(moviesDetailed.getRuntime()));
                plotTextview.setText(moviesDetailed.getPlot());
                new AsyncTask<String, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(String... params) {
                        String url = params[0];
                        return getBitmapFromURL(url);
                    }

                    @Override
                    protected void onPostExecute(Bitmap result) {
                        pictureImageView.setImageBitmap(result);
                        super.onPostExecute(result);
                    }
                }.execute(moviesDetailed.getPicture());
                //recyclerView.setAdapter(adapter);
                // recyclerView.setAdapter(new NewAdapter(OnFilter(NewArrayList, text)));
                //recyclerView.setLayoutManager(new LinearLayoutManager(myContext));
                //  setupRecyclerView(movieArrayList);
            }
        });
    }
    //原本要試能不能把電影的詳細資料放在子集合
  /*  query.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                          MoviesDetailed moviesDetailed = document.toObject(MoviesDetailed.class);
                            MoviesDetailedArrayList.add(moviesDetailed);
                            nameTextview.setText(moviesDetailed.getName());
                            kindTextview.setText(moviesDetailed.getKind());
                            dateTextview.setText(moviesDetailed.getDate());
                            runtimeTextview.setText(moviesDetailed.getRuntime());
                            plotTextview.setText(moviesDetailed.getPlot());
                        new AsyncTask<String, Void, Bitmap>()
                        {
                            @Override
                            protected Bitmap doInBackground(String... params)
                            {
                                String url = params[0];
                                return getBitmapFromURL(url);
                            }

                            @Override
                            protected void onPostExecute(Bitmap result)
                            {
                                pictureImageView.setImageBitmap (result);
                                super.onPostExecute(result);
                            }
                        }.execute(moviesDetailed.getPicture());
                        Toast.makeText(MovieDetailedActivity.this, "Reading database ......", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MovieDetailedActivity.this, "失敗", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MovieDetailedActivity.this, "失敗1", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

      /*  dbb.get().addOnCompleteListener(task -> {
            QuerySnapshot querySnapshot = task.isSuccessful() ? task.getResult() : null;

        });*/
    // }
    //有關取得圖片
    private static Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}








