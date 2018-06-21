package com.example.jingjing.blogv6;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter< MovieListAdapter.TheHolder>{
    private List<Movies> movielists;
    public MovieListAdapter(List<Movies> movielists) {
        this.movielists = movielists;
        notifyDataSetChanged(); //目前不知道這個用途  // 通知資料被變動，更新 ListView 顯示內容。
    }
    @NonNull
    @Override
    public TheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_taba,parent,false);
       // TheHolder theHolder = new TheHolder(view);
        TheHolder theHolder = new TheHolder(view, new TheHolder.TheHolderClick() {
            @Override
            public void clickOnView(View v, int position) {
               // Contact contact = mContacts.get(position);
               // Snackbar.make(v, contact.getName(), Snackbar.LENGTH_LONG).show();
               //Toast.makeText(view.getContext(), "我的清單", Toast.LENGTH_LONG).show();
               Intent intent = new Intent(context,MovieDetailedActivity.class);
               intent.putExtra("position",movielists.get(position).movie);
                //mediaStreamIntent.putExtra("sermon_details", (android.os.Parcelable) mDataset.get(position));
                context.startActivity(intent);
            }
        });
        return theHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TheHolder holder, int position) {
        Movies Movies =movielists.get(position);
        holder.movieTextview.setText(Movies.getMovie());
        holder.timeTextview.setText(Movies.getMovietime());
        holder.scoreTextview.setText(String.valueOf(Movies.getScore()));
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
                holder.pictureImageView.setImageBitmap (result);
                super.onPostExecute(result);
            }
        }.execute(Movies.getPicture());
    /* holder.setOnItemClick(new View.OnClickListener() {
          @Override
        public void onClick(View v) {
             //設定你點擊每個Item後，要做的事情
         }
      });*/
    }

    @Override
    public int getItemCount() {
        return movielists.size();
    }

   /* public  void setfilter(List<Movies> listitem){
    movielists = new ArrayList<>();
    movielists.addAll(listitem);
    notifyDataSetChanged();
    }*/
    private static Bitmap getBitmapFromURL(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public static class TheHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView movieTextview;
        private final TextView timeTextview;
        private final TextView scoreTextview;
        private final ImageView pictureImageView;
        private TheHolderClick mListener;
        private View view;
        public TheHolder(View itemView, TheHolderClick listener) {
            super(itemView);
            view = itemView;
            movieTextview = itemView.findViewById(R.id.movie_name);
            timeTextview = itemView.findViewById(R.id.movie_time);
            scoreTextview = itemView.findViewById(R.id.movie_score);
            pictureImageView=itemView.findViewById(R.id.movie_image);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

   /*  public void setOnItemClick(View.OnClickListener l) {
         this.view.setOnClickListener(l);
    }*/
        @Override
        public void onClick(View v) {
            mListener.clickOnView(v, getLayoutPosition());
        }
        public interface TheHolderClick {
            void clickOnView(View v, int position);
        }
    }
}
