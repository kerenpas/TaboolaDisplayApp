package com.example.tabooladisplayapp.presentation.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tabooladisplayapp.R;
import com.example.tabooladisplayapp.presentation.model.Cell;
import com.taboola.android.TBLClassicPage;
import com.taboola.android.TBLClassicUnit;
import com.taboola.android.Taboola;
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE;
import com.taboola.android.listeners.TBLClassicListener;

public class FeedAdapter extends ListAdapter<Cell, RecyclerView.ViewHolder> {

    public static final class TaboolaConfig {
        @androidx.annotation.IdRes public final int containerResId;
        public final String pageUrl;
        public final String pageType;
        public final String placementName; // e.g., "Below Article", "Feed without video"
        public final String mode;          // e.g., "alternating-widget-without-video", "thumbs-feed-01"
        public final int placementType;

        public TaboolaConfig(
                @IdRes int containerResId,
                String pageUrl,
                String pageType,
                String placementName,
                String mode,
                int placementType
        ) {
            this.containerResId = containerResId;
            this.pageUrl = pageUrl;
            this.pageType = pageType;
            this.placementName = placementName;
            this.mode = mode;
            this.placementType = placementType;
        }
    }

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
        Cell item = getItem(position);
        if (item instanceof Cell.DataCell) {
            return CellViewType.DATA.getValue();
        } else if (item instanceof Cell.TaboolaWidgetCell) {
            return CellViewType.TABOOLA_WIDGET.getValue();
        } else if (item instanceof Cell.TaboolaFeedCell) {
            return CellViewType.TABOOLA_WIDGET_FEED.getValue();
        } else {
            return CellViewType.EMPTY.getValue();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CellViewType type = CellViewType.fromValue(viewType);

        return switch (type) {
            case DATA -> new DataViewHolder(
                    inflater.inflate(R.layout.item_feed, parent, false)
            );

            case EMPTY -> new EmptyViewHolder(
                    inflater.inflate(R.layout.item_empty, parent, false)
            );

            case TABOOLA_WIDGET -> {
                View v = inflater.inflate(R.layout.item_taboola_widget, parent, false);
                TaboolaConfig widgetCfg = new TaboolaConfig(
                        R.id.taboola_container,
                        "https://www.example.com",
                        "article",
                        "Below Article",
                        "alternating-widget-without-video",
                        TBL_PLACEMENT_TYPE.PAGE_BOTTOM
                );
                yield new TaboolaViewHolder(v, widgetCfg);
            }

            case TABOOLA_WIDGET_FEED -> {
                View v = inflater.inflate(R.layout.item_taboola_widget, parent, false);
                TaboolaConfig feedCfg = new TaboolaConfig(
                        R.id.taboola_container,
                        "https://www.google.com/",
                        "article",
                        "Feed without video",
                        "thumbs-feed-01",
                        TBL_PLACEMENT_TYPE.FEED
                );
                yield new TaboolaViewHolder(v, feedCfg);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CellViewType type = CellViewType.fromValue(getItemViewType(position));
        Cell item = getItem(position);
        switch (type) {
            case DATA -> {
                ((DataViewHolder) holder).bind((Cell.DataCell)item);
            }

            case EMPTY -> {
                // No binding needed for empty view
            }
            case TABOOLA_WIDGET, TABOOLA_WIDGET_FEED -> {
                ((TaboolaViewHolder) holder).bind();
            }
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
                    .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(imageView);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    static class TaboolaViewHolder extends RecyclerView.ViewHolder {
        private final FrameLayout container;
        private final TBLClassicUnit classicUnit;
        private boolean loaded;

        TaboolaViewHolder(@NonNull View itemView, @NonNull TaboolaConfig config) {
            super(itemView);
            container = itemView.findViewById(config.containerResId);
            classicUnit = buildUnit(container.getContext(), config);
            container.addView(classicUnit);
        }

        private TBLClassicUnit buildUnit(Context context, TaboolaConfig cfg) {
            TBLClassicPage page = Taboola.getClassicPage(cfg.pageUrl, cfg.pageType);
            return page
                    .build(context, cfg.placementName, cfg.mode, cfg.placementType, new TBLClassicListener() {})
                    .setTargetType("mix");
        }

        void bind() {
            if (!loaded) {
                classicUnit.fetchContent();
                loaded = true;
            }
        }
    }
}
