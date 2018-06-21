package com.example.jingjing.blogv6;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UnAttenlist extends ArrayAdapter<Article> {
    FirebaseFirestore db= FirebaseFirestore.getInstance();

    private Activity context;
    private List<Article> Articles;

    public UnAttenlist(Activity context,List<Article> Articles) {
        super(context, R.layout.listview_myblog, Articles);
        this.context = context;
        this.Articles = Articles;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listviewItem=inflater.inflate(R.layout.listview_myatten,null,true);
        //TextView attenname=(TextView)listviewItem.findViewById(R.id.attenname);
        Article Article = Articles.get(position);
        //Attention Attention = Attentions.get(position);
        //Article Article = Articles.get(position);
        //Log.e("ooo", " Found - |" + Attention.getBlgger_name() + "|");
        //attenname.setText(Article.getName());


        return listviewItem;
    }
}
