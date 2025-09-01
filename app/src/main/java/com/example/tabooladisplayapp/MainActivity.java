package com.example.tabooladisplayapp;

import android.os.Bundle;
import android.view.View;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.tabooladisplayapp.databinding.ActivityMainBinding;
import com.example.tabooladisplayapp.presentation.main.FeedAdapter;
import com.example.tabooladisplayapp.presentation.main.FeedViewModel;
import com.example.tabooladisplayapp.shimmer.ShimmerBridge;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends ComponentActivity {
    private ActivityMainBinding binding;
    private FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        setupViewModel();
        setupShimmer();
    }

    private void setupRecyclerView() {
        adapter = new FeedAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        FeedViewModel viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        viewModel.getUiState().observe(this, state -> {
            if (state.isLoading()) {
                binding.shimmerView.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                binding.shimmerView.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                if (state.getCells() != null) {
                    adapter.submitList(state.getCells());
                }
                // Could handle error state here if needed
            }
        });
    }

    private void setupShimmer() {
        ShimmerBridge.mount(binding.shimmerView);
    }
}