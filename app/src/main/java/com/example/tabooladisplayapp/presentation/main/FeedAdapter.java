package com.example.tabooladisplayapp.presentation.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
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
            case DATA -> new DataViewHolder(inflater.inflate(R.layout.item_feed, parent, false));
            case EMPTY -> new EmptyViewHolder(inflater.inflate(R.layout.item_empty, parent, false));
            case TABOOLA_WIDGET -> new TaboolaWidgetViewHolder(inflater.inflate(R.layout.item_taboola_widget, parent, false));
            case TABOOLA_WIDGET_FEED -> new TaboolaWidgetViewHolder(inflater.inflate(R.layout.item_taboola_widget, parent, false));
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cell item = getItem(position);
        if (holder instanceof DataViewHolder && item instanceof Cell.DataCell) {
            ((DataViewHolder) holder).bind((Cell.DataCell) item);
        } else if (holder instanceof TaboolaWidgetViewHolder) {
            ((TaboolaWidgetViewHolder) holder).bind();
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

    static class TaboolaWidgetViewHolder extends RecyclerView.ViewHolder {
        private final FrameLayout container;
        private final TBLClassicUnit classicUnit;

        TaboolaWidgetViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.taboola_container);
            classicUnit = setupTaboolaWidget();
            container.addView(classicUnit);
        }

        private TBLClassicUnit setupTaboolaWidget() {
            TBLClassicPage tblClassicPage = Taboola.getClassicPage("https://www.example.com", "article");
            return tblClassicPage.build(container.getContext(),"Below Article","alternating-widget-without-video", TBL_PLACEMENT_TYPE.PAGE_BOTTOM, new TBLClassicListener() { });
        }

        void bind() {
            classicUnit.fetchContent();
        }
    }

   /* static class TaboolaFeedViewHolder extends RecyclerView.ViewHolder {
        private final TaboolaWidget taboolaWidget;

        TaboolaFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            taboolaWidget = itemView.findViewById(R.id.taboola_widget_feed);
            setupTaboolaWidget();
        }

        private void setupTaboolaWidget() {
            TaboolaWidget.propertiesBuilder()
                .setPublisherId("sdk-tester")
                .setModeId("thumbnails-feed")
                .setPlacementId("Feed without video")
                .setPageType("article")
                .setTargetType("mix")
                .setPageUrl(TaboolaUrlHelper.getPageUrl("https://www.example.com"))
                .build();
        }

        void bind() {
            taboolaWidget.fetchContent();
        }
    }*/
}
