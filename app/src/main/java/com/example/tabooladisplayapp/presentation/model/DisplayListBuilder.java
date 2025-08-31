package com.example.tabooladisplayapp.presentation.model;

import com.example.tabooladisplayapp.domain.model.FeedItem;
import java.util.ArrayList;
import java.util.List;

public class DisplayListBuilder {
    private static final int TOTAL_CELLS = 10;
    private static final int[] EMPTY_POSITIONS = {2, 9}; // 0-based indices

    public List<Cell> build(List<FeedItem> items) {
        List<Cell> cells = new ArrayList<>(TOTAL_CELLS);
        int itemIndex = 0;

        for (int i = 0; i < TOTAL_CELLS; i++) {
            if (isEmptyPosition(i)) {
                cells.add(new Cell.EmptyCell());
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

    private boolean isEmptyPosition(int position) {
        for (int emptyPos : EMPTY_POSITIONS) {
            if (position == emptyPos) return true;
        }
        return false;
    }
}
