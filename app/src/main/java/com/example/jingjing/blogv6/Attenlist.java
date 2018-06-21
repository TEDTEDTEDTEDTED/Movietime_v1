package com.example.jingjing.blogv6;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Attenlist extends RecyclerView.Adapter<Attenlist.ViewHolder>{
    private static final String TAG = "AttenlistAdapter";
    private ArrayList<String> pics = new ArrayList<>();
    private ArrayList<String> blogger_name = new ArrayList<>();
    private Context mycontext;

    public Attenlist(Context mycontext,ArrayList<String> pics, ArrayList<String> blogger_name) {
        this.pics = pics;
        this.blogger_name = blogger_name;
        this.mycontext = mycontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_myatten,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("recycleview test","called");

        Glide.with(mycontext)
                .asBitmap()
                .load(pics.get(position))
                .into(holder.strickers);

        holder.name.setText(blogger_name.get(position));

        holder.myatten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("layoutclick","called");

            }
        });

    }

    @Override
    public int getItemCount() {
        return pics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView strickers;
        TextView name;
        RelativeLayout myatten;

        public ViewHolder(View itemView) {
            super(itemView);
            strickers = itemView.findViewById(R.id.strickers);
            name = itemView.findViewById(R.id.name);
            myatten = itemView.findViewById(R.id.myatten);


        }


//    ArrayAdapter<Attention>
//    FirebaseFirestore db= FirebaseFirestore.getInstance();
//
//    private Activity context;
//    private List<Attention> Attentions;
//
//    public Attenlist(Activity context,List<Attention> Attention){
//        super(context,R.layout.listview_myatten, Attention);
//        this.context=context;
//        this.Attentions = Attention;
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//
//        View listviewItem=inflater.inflate(R.layout.listview_myatten,null,true);
//        TextView attenname=(TextView)listviewItem.findViewById(R.id.attenname);
//        Attention Attention = Attentions.get(position);
//        //Article Article = Articles.get(position);
//        Log.e("ooo", " Found - |" + Attention.getBlgger_name() + "|");
//        attenname.setText(Attention.getBlgger_name());
//
//
//        return listviewItem;
//    }
}
