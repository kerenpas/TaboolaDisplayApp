package com.example.tabooladisplayapp.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tabooladisplayapp.domain.model.CellColorUpdate;
import com.example.tabooladisplayapp.domain.model.FeedItem;
import com.example.tabooladisplayapp.domain.usecase.GetFeedUseCase;
import com.example.tabooladisplayapp.domain.usecase.ObserveCellsColorAndVisibilityUseCase;
import com.example.tabooladisplayapp.presentation.model.Cell;
import com.example.tabooladisplayapp.presentation.model.DisplayListBuilder;
import com.example.tabooladisplayapp.utils.Callback;

import dagger.hilt.android.lifecycle.HiltViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@HiltViewModel
public class FeedViewModel extends ViewModel {
    private final GetFeedUseCase getFeedUseCase;
    private final ObserveCellsColorAndVisibilityUseCase observePatchesUseCase;
    private final DisplayListBuilder displayListBuilder;

    private final MediatorLiveData<UiState> uiState = new MediatorLiveData<>(UiState.loading());
    private final MutableLiveData<List<Cell>> baseCells = new MutableLiveData<>();

    public LiveData<UiState> getUiState() {
        return uiState;
    }

    @Inject
    public FeedViewModel(GetFeedUseCase getFeedUseCase, ObserveCellsColorAndVisibilityUseCase observePatchesUseCase1) {
        this.getFeedUseCase = getFeedUseCase;
        this.observePatchesUseCase = observePatchesUseCase1;
        this.displayListBuilder = new DisplayListBuilder();
        uiState.addSource(baseCells, cells -> {
            if (cells != null) {
                uiState.setValue(UiState.success(cells));
            }
        });
        loadFeed();
        LiveData<List<CellColorUpdate>> patchLive = observePatchesUseCase.execute();
        uiState.addSource(patchLive, updates -> {
            if (updates == null || updates.isEmpty()) return;
            applyColorVisibilityUpdates(updates);
        });
    }

    private void loadFeed() {
        getFeedUseCase.invoke(10, new Callback<List<FeedItem>>() {
            @Override
            public void onSuccess(List<FeedItem> value) {
                List<Cell> cells = displayListBuilder.build(value);
                baseCells.setValue(cells);
            }

            @Override
            public void onError(Throwable error) {
                uiState.setValue(UiState.error(error.getMessage()));
            }
        });
    }


    private void applyColorVisibilityUpdates(List<CellColorUpdate> updates) {
        // 1) get the current list we’re showing
        UiState currentState = uiState.getValue();
        List<Cell> current = (currentState != null && currentState.getCells() != null)
                ? currentState.getCells()
                : baseCells.getValue();

        if (current == null || current.isEmpty()) return;

        for(CellColorUpdate update : updates) {
            if (update.getPosition() < 0 || update.getPosition() >= current.size()) {
                continue; // skip invalid index
            }
            Cell oldCell = current.get(update.getPosition());
            oldCell.setBackgroundColor(update.getColor());
            oldCell.setVisible(update.isVisable());
        }
        uiState.setValue(UiState.success(java.util.Collections.unmodifiableList(current)));
    }

    public static class UiState {
        private final boolean loading;
        private final List<Cell> cells;


        private final Map<Integer, Integer> cellColors;
        private final String error;

        private UiState(boolean loading, List<Cell> cells, Map<Integer, Integer> cellColors, String error) {
            this.loading = loading;
            this.cells = cells;
            this.cellColors = cellColors;
            this.error = error;
        }

        public static UiState loading() {
            return new UiState(true, null, null, null);
        }

        public static UiState success(List<Cell> cells) {
            return new UiState(false, cells, null, null);
        }

        public static UiState error(String error) {
            return new UiState(false, null,null, error);
        }

        public boolean isLoading() { return loading; }
        public List<Cell> getCells() { return cells; }
        public String getError() { return error; }
    }
}
