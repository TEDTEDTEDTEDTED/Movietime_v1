package com.example.jingjing.blogv6;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter< ActorAdapter.TheHolder>{
    private List<Actor> actorlists;

    public ActorAdapter(List<Actor> actorlists) {
        this.actorlists = actorlists;

    }

    @NonNull
    @Override
    public TheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.col_moviedetailed,parent,false);
        // TheHolder theHolder = new TheHolder(view);
       ActorAdapter.TheHolder theHolder = new ActorAdapter.TheHolder(view, new ActorAdapter.TheHolder.TheHolderClick() {
            @Override
            public void clickOnView(View v, int position) {

                // Contact contact = mContacts.get(position);
                // Snackbar.make(v, contact.getName(), Snackbar.LENGTH_LONG).show();
                Toast.makeText(view.getContext(), "我的清單", Toast.LENGTH_LONG).show();
                //mediaStreamIntent.putExtra("sermon_details", (android.os.Parcelable) mDataset.get(position));
            }
        });


        return theHolder;





    }

    @Override
    public void onBindViewHolder(@NonNull TheHolder holder, int position) {
        Actor actor =actorlists.get(position);
        holder.ActorTextview.setText(actor.getActor());


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
        }.execute(actor.getPicture());




    }

    @Override
    public int getItemCount() {





        return actorlists.size();

    }


    public static class TheHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView ActorTextview;

        private final ImageView pictureImageView;
        private ActorAdapter.TheHolder.TheHolderClick mListener;
        private View view;

        public TheHolder(View itemView, ActorAdapter.TheHolder.TheHolderClick listener) {
            super(itemView);
            view = itemView;
            ActorTextview = itemView.findViewById(R.id.cast_name);
            pictureImageView=itemView.findViewById(R.id.movie_cast);
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



















}
