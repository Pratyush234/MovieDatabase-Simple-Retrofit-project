package com.example.praty.moviedatabase.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.example.praty.moviedatabase.R;
import com.example.praty.moviedatabase.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private Context mContext;
    private List<Movie> mMovieList;
    private List<Movie> mFilteredList;

    public MyAdapter(List<Movie> mMovieList, Context mContext) {
        this.mContext = mContext;
        this.mMovieList = mMovieList;
        this.mFilteredList=mMovieList;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.mTitle.setText(mFilteredList.get(position).getTitle());
        String release="Release Date: "+mFilteredList.get(position).getReleaseDate();
        holder.mReleaseDate.setText(release);
        holder.mContent.setText(mFilteredList.get(position).getOverview());
        String imdb="IMDB: "+mFilteredList.get(position).getVoteAverage();
        holder.mImdb.setText(imdb);
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public Filter getFilter(){
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString=constraint.toString();
                if(charString.isEmpty()){
                    //do nothing
                }else{
                    List<Movie> mFiltered=new ArrayList<>();
                    for(Movie row: mMovieList){
                        if(row.getTitle().toLowerCase().contains(charString.toLowerCase())){
                            mFiltered.add(row);
                        }
                    }
                    mFilteredList=mFiltered;
                }
                FilterResults results=new FilterResults();
                results.values=mFilteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList=(ArrayList<Movie>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mReleaseDate;
        TextView mContent;
        TextView mImdb;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle=(TextView) itemView.findViewById(R.id.titleView);
            mReleaseDate=(TextView) itemView.findViewById(R.id.releaseView);
            mContent=(TextView) itemView.findViewById(R.id.contentView);
            mImdb= (TextView) itemView.findViewById(R.id.imdbView);
        }
    }
}
