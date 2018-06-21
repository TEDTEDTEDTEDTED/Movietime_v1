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

public class new2 extends RelativeLayout {
    private Context myContext;
    private Context mainContext;
    private View view02;
    private SearchView mSearchView ;
    private RecyclerView recyclerView;
    private NewAdapter adapter;
    private   String  text;
    private DividerItemDecoration mDivider;//分隔线
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public new2 (Context context) {
        super(context);
        myContext = context;
        view02 = LayoutInflater.from(myContext).inflate(R.layout.news2, null);
        addView(view02);
        mSearchView = (SearchView) findViewById(R.id.searchview_new2);
        recyclerView =(RecyclerView) findViewById(R.id.recycler_new2);

        recyclerView.setHasFixedSize(true);
        setRecyclerview2();

        //原本點擊SearchIcon才會出現搜尋的那個icon，改成在整條searchView前方顯示
        mSearchView.setIconifiedByDefault(false);
        //設置true打了文字後旁會邊出現箭頭，代表submit
        mSearchView.setSubmitButtonEnabled(true);

        mDivider = new DividerItemDecoration( myContext ,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
        //刷新
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.new_refresh_layout2);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRecyclerview2();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast.makeText(myContext, "搜尋結果為：" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("當文字改變時", "改變的文字 ：" + newText);
                text=newText;
                setRecyclerview();
                return true;
            }
        });



    }

    public void setRecyclerview() {
        ArrayList<News> NewArrayList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("news")
                .whereEqualTo("genre","電影新聞");
        query.get().addOnCompleteListener(task -> {
            QuerySnapshot querySnapshot = task.isSuccessful() ? task.getResult() : null;
            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                News news = documentSnapshot.toObject(News.class);
                NewArrayList.add(news);
                adapter = new NewAdapter(NewArrayList);
                //recyclerView.setAdapter(adapter);
                recyclerView.setAdapter(new NewAdapter(OnFilter(NewArrayList, text)));
                recyclerView.setLayoutManager(new LinearLayoutManager(myContext));
                //  setupRecyclerView(movieArrayList);
            }
        });

    }

    public void setRecyclerview2() {
        ArrayList<News> NewArrayList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("news")
                .whereEqualTo("genre","電影新聞");


        query.get().addOnCompleteListener(task -> {
            QuerySnapshot querySnapshot = task.isSuccessful() ? task.getResult() : null;

            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                News news = documentSnapshot.toObject(News.class);
                NewArrayList.add(news);
                adapter = new NewAdapter(NewArrayList);

                recyclerView.setAdapter(adapter);
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
