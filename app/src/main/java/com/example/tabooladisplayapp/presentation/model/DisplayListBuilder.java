package com.example.tabooladisplayapp.presentation.model;

import com.example.tabooladisplayapp.domain.model.FeedItem;
import java.util.ArrayList;
import java.util.List;

public class DisplayListBuilder {
    private static final int TOTAL_CELLS = 10;

    private static final int TABOOLA_WIDGET_POSITION = 2;
    private static final int TABOOLA_FEED_POSITION = 9;

    public List<Cell> build(List<FeedItem> items) {
        List<Cell> cells = new ArrayList<>(TOTAL_CELLS);
        int itemIndex = 0;

        for (int i = 0; i < TOTAL_CELLS; i++) {
            if (i == TABOOLA_WIDGET_POSITION) {
                cells.add(new Cell.TaboolaWidgetCell());
            } else if (i == TABOOLA_FEED_POSITION) {
                cells.add(new Cell.TaboolaFeedCell());
            } else if (itemIndex < items.size()) {
                FeedItem item = items.get(itemIndex++);
                cells.add(new Cell.DataCell(
                    item.getName(),
                    item.getDescription(),
                    item.getThumbnailUrl()
                ));
            } else {
                cells.add(new Cell.EmptyCell());
            }
        }

        return cells;
    }
}
