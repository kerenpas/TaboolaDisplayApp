package com.example.tabooladisplayapp.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.tabooladisplayapp.domain.model.FeedItem;
import com.example.tabooladisplayapp.domain.usecase.GetFeedUseCase;
import com.example.tabooladisplayapp.presentation.model.Cell;
import com.example.tabooladisplayapp.presentation.model.DisplayListBuilder;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class FeedViewModel extends ViewModel {
    private final GetFeedUseCase getFeedUseCase;
    private final DisplayListBuilder displayListBuilder;
    private final MutableLiveData<UiState> _uiState = new MutableLiveData<>(UiState.loading());

    public LiveData<UiState> getUiState() {
        return _uiState;
    }

    @Inject
    public FeedViewModel(GetFeedUseCase getFeedUseCase) {
        this.getFeedUseCase = getFeedUseCase;
        this.displayListBuilder = new DisplayListBuilder();
        loadFeed();
    }

    private void loadFeed() {
        getFeedUseCase.execute(10, new GetFeedUseCase.Callback() {
            @Override
            public void onResult(List<FeedItem> items) {
                List<Cell> cells = displayListBuilder.build(items);
                _uiState.setValue(UiState.success(cells));
            }

            @Override
            public void onError(Throwable error) {
                _uiState.setValue(UiState.error(error.getMessage()));
            }
        });
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
