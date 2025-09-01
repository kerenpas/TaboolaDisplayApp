package com.example.tabooladisplayapp.presentation.model;

import com.example.tabooladisplayapp.domain.model.FeedItem;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class DisplayListBuilderTest {
    @Test
    public void testBuildList_EmptyAtCorrectPositions() {
        DisplayListBuilder builder = new DisplayListBuilder();
        List<FeedItem> items = Arrays.asList(
            new FeedItem(1L, "Item 1", "Desc 1", "url1"),
            new FeedItem(2L, "Item 2", "Desc 2", "url2"),
            new FeedItem(3L, "Item 3", "Desc 3", "url3"),
            new FeedItem(4L, "Item 4", "Desc 4", "url4"),
            new FeedItem(5L, "Item 5", "Desc 5", "url5")
        );

        List<Cell> result = builder.build(items);

        // Verify total size is 10
        assertEquals(10, result.size());

        // Verify positions 3 and 10 (1-based) are empty
        assertTrue(result.get(2) instanceof Cell.EmptyCell);  // 3rd position (0-based)
        assertTrue(result.get(9) instanceof Cell.EmptyCell);  // 10th position (0-based)

        // Verify other positions contain data when available
        assertTrue(result.get(0) instanceof Cell.DataCell);
        assertTrue(result.get(1) instanceof Cell.DataCell);
        assertTrue(result.get(3) instanceof Cell.DataCell);
    }
}
