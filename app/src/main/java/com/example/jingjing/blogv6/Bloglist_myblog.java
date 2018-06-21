package com.example.jingjing.blogv6;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Bloglist_myblog extends ArrayAdapter<Article> {

    FirebaseFirestore db= FirebaseFirestore.getInstance();

    private Activity context;
    private List<Article> Articles;

    public Bloglist_myblog(Activity context,List<Article> Articles){
        super(context,R.layout.listview_myblog, Articles);
        this.context=context;
        this.Articles = Articles;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listviewItem=inflater.inflate(R.layout.listview_myblog,null,true);
        TextView myblog_1=(TextView)listviewItem.findViewById(R.id.myblog_1);
        TextView myblog_2=(TextView)listviewItem.findViewById(R.id.myblog_2);
        TextView myblog_3=(TextView)listviewItem.findViewById(R.id.myblog_3);
        TextView myblog_4=(TextView)listviewItem.findViewById(R.id.myblog_4);

        Article Article = Articles.get(position);
        myblog_1.setText(Article.getName());
        myblog_2.setText(Article.getTitle());
        myblog_3.setText(Article.getArticle());
        myblog_4.setText(Article.getLike());

        return listviewItem;
    }
}
