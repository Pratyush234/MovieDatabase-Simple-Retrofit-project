package com.example.praty.moviedatabase.activity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.praty.moviedatabase.R;
import com.example.praty.moviedatabase.adapter.MyAdapter;
import com.example.praty.moviedatabase.model.Movie;
import com.example.praty.moviedatabase.model.MoviesResponse;
import com.example.praty.moviedatabase.rest.ApiClient;
import com.example.praty.moviedatabase.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY="e0b4188b67944de2fb97538e18f5d38e";

    private RecyclerView mRecycler;
    private List<Movie> movies;
    private SearchView searchView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar myToolbar=(Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Movie Database");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);



        mRecycler=(RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        mRecycler.setLayoutManager(manager);
        ApiInterface apiService= ApiClient.getclient().create(ApiInterface.class);
        Call<MoviesResponse> call=apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                MoviesResponse body=response.body();
                Log.d("Movies","onResponse() called: body="+body);
                if(body!=null) {
                    movies=body.getList();
                    Log.d("Movies", "No. of movies returned:" + movies.size());
                    mAdapter=new MyAdapter(movies,getApplicationContext());
                    mRecycler.setAdapter(mAdapter);
                }

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e("Movies","onFailure() method called, error:"+t.toString());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu,menu);
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHintTextColor(Color.WHITE);
        searchEditText.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if(id==R.id.action_search)
            return true;
        return super.onOptionsItemSelected(item);
    }
}
