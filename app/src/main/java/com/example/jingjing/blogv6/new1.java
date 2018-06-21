package com.example.jingjing.blogv6;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class new1 extends RelativeLayout {
    private Context myContext;
    private Context mainContext;
    private View view02;
    private  SearchView mSearchView ;
    private  RecyclerView recyclerView;
    private NewAdapter adapter;
    private   String  text;  //關鍵字
    private DividerItemDecoration mDivider;//分隔线
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean textChange; //用來判斷有沒有搜尋
    public new1 (Context context) {
        super(context);
        myContext = context;
        view02 = LayoutInflater.from(myContext).inflate(R.layout.news1, null);
        addView(view02);
        mSearchView = (SearchView) findViewById(R.id.searchview_new1);
        recyclerView =(RecyclerView) findViewById(R.id.recycler_new1);
        recyclerView.setHasFixedSize(true);
        setRecyclerview();
        //原本點擊SearchIcon才會出現搜尋的那個icon，改成在整條searchView前方顯示
        mSearchView.setIconifiedByDefault(false);
        //設置true打了文字後旁會邊出現箭頭，代表submit
        mSearchView.setSubmitButtonEnabled(true);
        mDivider = new DividerItemDecoration( myContext ,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
        //刷新
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.new_refresh_layout1);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRecyclerview();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
         //搜尋方法//關鍵字搜尋
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast.makeText(myContext, "搜尋結果為：" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("當文字改變時", "你搜尋的文字：" + newText);
                text=newText;
                textChange=true;
                setRecyclerview();
                return true;
            }
        });
    }

    public void setRecyclerview() {
        ArrayList<News> NewArrayList = new ArrayList<>();//
        FirebaseFirestore db = FirebaseFirestore.getInstance();//連資料庫
        Query query = db.collection("news")  //查詢集合news
                .whereEqualTo("genre","獎項消息"); //篩選獎項消息，而不是電影新聞

        query.get().addOnCompleteListener(task -> {
            QuerySnapshot querySnapshot = task.isSuccessful() ? task.getResult() : null;
            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                News news = documentSnapshot.toObject(News.class);
                NewArrayList.add(news);//取得資料庫的news
                adapter = new NewAdapter(NewArrayList);
                //recyclerView.setAdapter(adapter);
                if(textChange==true) {
                    recyclerView.setAdapter(new NewAdapter(OnFilter(NewArrayList, text))); // 關鍵字搜尋
                }
                else{
                    recyclerView.setAdapter(adapter); //全部內容
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(myContext));
                //  setupRecyclerView(movieArrayList);
            }
        });

    }



    private List<News> OnFilter(List<News> filterLocales, String text) {
        String search = text.toLowerCase();
        List<News> filtered = new ArrayList<>();
        for (News info : filterLocales) {
            final String localeName = info.getTitle().toLowerCase();
            if (localeName.contains(search)) {
                // filtered= Arrays.asList(
                //  new Movies(info.getMovie(),info.getMovietime(),info.getScore(),info.getPicture()));
                filtered.add(info);
            }
        }
        return filtered;
    }















}
