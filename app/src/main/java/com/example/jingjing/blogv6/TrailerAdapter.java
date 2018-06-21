package com.example.jingjing.blogv6;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter< TrailerAdapter.TheHolder> {

    private List<Trailer> trailerlists;

    public TrailerAdapter(List<Trailer> trailerlists) {

        this.trailerlists = trailerlists;

    }

    @NonNull
    @Override
    public TheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_trailer,parent,false);
        // TheHolder theHolder = new TheHolder(view);
        TrailerAdapter.TheHolder theHolder = new  TrailerAdapter.TheHolder(view, new  TrailerAdapter.TheHolder.TheHolderClick() {
            @Override
            public void clickOnView(View v, int position) {
                // Contact contact = mContacts.get(position);
                // Snackbar.make(v, contact.getName(), Snackbar.LENGTH_LONG).show();
                Toast.makeText(view.getContext(), "我的清單", Toast.LENGTH_LONG).show();
                //Intent mediaStreamIntent = new Intent(context,MovieDetailedActivity.class);
                //mediaStreamIntent.putExtra("sermon_details", (android.os.Parcelable) mDataset.get(position));
               // context.startActivity(mediaStreamIntent);

            }
        });


        return theHolder;



    }

    @Override
    public void onBindViewHolder(@NonNull TheHolder holder, int position) {
       Trailer trailer =trailerlists.get(position);

        holder.TrailerWebView.loadData(trailer.getTrailer(),"text/html; charset=UTF-8", null);
//holder.TrailerWebView.loadData(trailer.getTrailer(),"text/html","utf8");
        //loadData(data, "text/html; charset=UTF-8", null);
    }

    @Override
    public int getItemCount() {
        return trailerlists.size();






    }

    public static class TheHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TrailerAdapter.TheHolder.TheHolderClick mListener;
        private View view;
        private final WebView TrailerWebView;
        public TheHolder(View itemView, TrailerAdapter.TheHolder.TheHolderClick listener) {
            super(itemView);
            view = itemView;
            mListener = listener;
            TrailerWebView = itemView.findViewById(R.id.trailer_webview);
            TrailerWebView.getSettings().setJavaScriptEnabled(true);
            TrailerWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mListener.clickOnView(v, getLayoutPosition());

        }


        public interface TheHolderClick {
            void clickOnView(View v, int position);
        }


    }




























}
