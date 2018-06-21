package com.example.jingjing.blogv6;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter< NewAdapter.TheHolder> {
    private List<News> newlists;
    public NewAdapter(List<News> newlists) {
        this.newlists = newlists;
    }

    @NonNull
    @Override
    public TheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_new,parent,false);
        // TheHolder theHolder = new TheHolder(view);
        NewAdapter.TheHolder theHolder = new NewAdapter.TheHolder(view, new NewAdapter.TheHolder.TheHolderClick() {
            @Override
            public void clickOnView(View v, int position) {
                // Contact contact = mContacts.get(position);
                // Snackbar.make(v, contact.getName(), Snackbar.LENGTH_LONG).show();
                Toast.makeText(view.getContext(), "新聞", Toast.LENGTH_LONG).show();
              //  Intent mediaStreamIntent = new Intent(context,MovieDetailedActivity.class);
                //mediaStreamIntent.putExtra("sermon_details", (android.os.Parcelable) mDataset.get(position));
               // context.startActivity(mediaStreamIntent);
            }
        });
        return theHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TheHolder holder, int position) {
        News news =newlists.get(position);
        holder.NewTextview.setText(news.getTitle());
        holder.TextTextview.setText(news.getText());
        holder.WriterTextview.setText(news.getWriter());
        holder. DateTextview.setText(news.getDate());
    }

    @Override
    public int getItemCount() {
        return newlists.size();
    }


    public static class TheHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView NewTextview;
        //private final TextView GenreTextview;
        private final TextView TextTextview;
        private final TextView WriterTextview;
        private final TextView DateTextview;
        private NewAdapter.TheHolder.TheHolderClick mListener;
        private View view;

        public TheHolder(View itemView, NewAdapter.TheHolder.TheHolderClick listener) {
            super(itemView);
            view = itemView;
            NewTextview = itemView.findViewById(R.id.new_name);
            //GenreTextview = itemView.findViewById(R.id.new);
            TextTextview = itemView.findViewById(R.id.new_content);
            WriterTextview=itemView.findViewById(R.id.new_article);
            DateTextview=itemView.findViewById(R.id.new_date);
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
