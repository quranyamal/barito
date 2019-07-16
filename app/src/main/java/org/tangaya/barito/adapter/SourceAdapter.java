package org.tangaya.barito.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tangaya.barito.R;
import org.tangaya.barito.data.model.Source;

import java.util.ArrayList;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.CustomViewHolder> {

    private ArrayList<Source> data;

    public SourceAdapter() {
        data = new ArrayList<>();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView description;
        private TextView category;

        public CustomViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.source_name);
            description = itemView.findViewById(R.id.source_description);
            category = itemView.findViewById(R.id.source_category);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_source, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.description.setText(data.get(position).getDescription());
        holder.category.setText(data.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Source> sources) {
        data.clear();
        data.addAll(sources);
    }
}
