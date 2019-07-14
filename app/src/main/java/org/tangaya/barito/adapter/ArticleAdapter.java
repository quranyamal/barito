package org.tangaya.barito.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.tangaya.barito.R;
import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.view.ui.MainActivity;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.CustomViewHolder>
        implements View.OnCreateContextMenuListener {

    private ArrayList<Article> data;
    private Context context;
    private int position;

    public ArticleAdapter(Context context){
        this.context = context;
        this.data = new ArrayList<>();
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        MenuInflater inflater = ((MainActivity) context).getMenuInflater();
        inflater.inflate(R.menu.article_context_menu, contextMenu);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            coverImage = mView.findViewById(R.id.coverImage);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("onCreateViewHolder", "onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_news, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        holder.txtTitle.setText(data.get(position).getTitle());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));

        Picasso.get().setIndicatorsEnabled(true);
        Picasso.get().setLoggingEnabled(true);

        if (data.get(position).getUrlToImage() != null) {
            Picasso.get().load(data.get(position).getUrlToImage()).into(holder.coverImage);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Article> newData) {
        data.clear();
        data.addAll(newData);
    }
}