package com.example.tabooladisplayapp.presentation.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tabooladisplayapp.R;
import com.example.tabooladisplayapp.presentation.model.Cell;

public class FeedAdapter extends ListAdapter<Cell, RecyclerView.ViewHolder> {
    public FeedAdapter() {
        super(new DiffUtil.ItemCallback<Cell>() {
            @Override
            public boolean areItemsTheSame(@NonNull Cell oldItem, @NonNull Cell newItem) {
                if (oldItem instanceof Cell.DataCell && newItem instanceof Cell.DataCell) {
                    return ((Cell.DataCell) oldItem).getThumbnailUrl()
                        .equals(((Cell.DataCell) newItem).getThumbnailUrl());
                }
                return oldItem.getType() == newItem.getType();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Cell oldItem, @NonNull Cell newItem) {
                if (oldItem instanceof Cell.DataCell && newItem instanceof Cell.DataCell) {
                    Cell.DataCell oldData = (Cell.DataCell) oldItem;
                    Cell.DataCell newData = (Cell.DataCell) newItem;
                    return oldData.getName().equals(newData.getName())
                        && oldData.getDescription().equals(newData.getDescription())
                        && oldData.getThumbnailUrl().equals(newData.getThumbnailUrl());
                }
                return true;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            return new DataViewHolder(inflater.inflate(R.layout.item_feed, parent, false));
        } else {
            return new EmptyViewHolder(inflater.inflate(R.layout.item_empty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cell item = getItem(position);
        if (holder instanceof DataViewHolder && item instanceof Cell.DataCell) {
            ((DataViewHolder) holder).bind((Cell.DataCell) item);
        }
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView titleView;
        private final TextView descriptionView;

        DataViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView = itemView.findViewById(R.id.titleView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
        }

        void bind(Cell.DataCell item) {
            titleView.setText(item.getName());
            descriptionView.setText(item.getDescription());
            Glide.with(imageView)
                .load(item.getThumbnailUrl())
                .centerCrop()
                .into(imageView);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
