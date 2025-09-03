package com.example.tabooladisplayapp.presentation.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
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

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@HiltViewModel
public class FeedViewModel extends ViewModel {
    private final GetFeedUseCase getFeedUseCase;
    private final ObserveCellsColorAndVisibilityUseCase observePatchesUseCase;
    private final DisplayListBuilder displayListBuilder;
    private final MutableLiveData<UiState> _uiState = new MutableLiveData<>(UiState.loading());
    private final CompositeDisposable disposables = new CompositeDisposable();

    private List<CellColorUpdate> cellColorUpdateList = new ArrayList<>();

    public LiveData<UiState> getUiState() {
        return _uiState;
    }

    @Inject
    public FeedViewModel(GetFeedUseCase getFeedUseCase, ObserveCellsColorAndVisibilityUseCase observePatchesUseCase1) {
        this.getFeedUseCase = getFeedUseCase;
        this.observePatchesUseCase = observePatchesUseCase1;
        this.displayListBuilder = new DisplayListBuilder();
        loadFeed();
        disposables.add(
            observePatchesUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    updates -> {
                        if (updates == null || updates.isEmpty()) return;
                        cellColorUpdateList = updates;
                        applyColorVisibilityUpdates(updates);
                    },
                    throwable -> {
                        // Log or handle the error
                        // Example: Log.e("FeedViewModel", "Error observing cell color updates", throwable);
                    }
                )
        );
    }

    private void loadFeed() {
        getFeedUseCase.invoke(10, new Callback<List<FeedItem>>() {
            @Override
            public void onSuccess(List<FeedItem> value) {
                List<Cell> cells = displayListBuilder.build(value);
                Log.d("FeedViewModel", "Updated cells: line 66 " + cells.size());
                _uiState.setValue(UiState.success(cells));
                applyColorVisibilityUpdates(cellColorUpdateList);
            }

            @Override
            public void onError(Throwable error) {
                _uiState.setValue(UiState.error(error.getMessage()));
            }
        });
    }


    private void applyColorVisibilityUpdates(List<CellColorUpdate> updates) {
        // 1) get the current list we’re showing
        UiState currentState = _uiState.getValue();
        List<Cell> current = (currentState != null && currentState.getCells() != null)
                ? currentState.getCells()
                : new ArrayList<>();

        if (current == null || current.isEmpty()) return;

        for(CellColorUpdate update : updates) {
            if (update.getPosition() < 0 || update.getPosition() >= current.size()) {
                continue; // skip invalid index
            }
            Cell oldCell = current.get(update.getPosition());
            oldCell.setBackgroundColor(update.getColor());
            oldCell.setVisible(update.isVisible());
        }
        Log.d("FeedViewModel", "Updated cells: line 95 " + updates.size());
        _uiState.setValue(UiState.success(java.util.Collections.unmodifiableList(current)));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }

    public static class UiState {
        private final boolean loading;
        private final List<Cell> cells;
        private final String error;

        private UiState(boolean loading, List<Cell> cells, String error) {
            this.loading = loading;
            this.cells = cells;
            this.error = error;
        }

        public static UiState loading() {
            return new UiState(true, null, null);
        }

        public static UiState success(List<Cell> cells) {
            return new UiState(false, cells, null);
        }

        public static UiState error(String error) {
            return new UiState(false, null, error);
        }

        public boolean isLoading() { return loading; }
        public List<Cell> getCells() { return cells; }
        public String getError() { return error; }
    }
}
