package com.example.mangalibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MangaLibrary extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MangaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MangaItem> mMangaList;
    private EditText mEditTextSearch;
    private ImageButton mImageButtonSearch;
    private ProgressBar mProgressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the recycler view
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mMangaList = new ArrayList<>();
        mAdapter = new MangaAdapter(mMangaList);

        // Set the adapter and layout manager for the recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Initialize the search bar, button, and progress bar
        mEditTextSearch = findViewById(R.id.edit_text_search);
        mImageButtonSearch = findViewById(R.id.image_button_search);
        mProgressBarLoading = findViewById(R.id.progress_bar_loading);

        // Set an on click listener for the search button
        mImageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchManga();
            }
        });
    }

    private void searchManga() {
        // Get the search query from the edit text
        String query = mEditTextSearch.getText().toString().trim();

        // Clear the current manga list
        mMangaList.clear();

        // Show the progress bar
        mProgressBarLoading.setVisibility(View.VISIBLE);

        // Perform the search using an API or local database
        // Here, we'll just add some dummy data for testing purposes
        mMangaList.add(new MangaItem("Manga 1", "Author 1", "https://example.com/manga1"));
        mMangaList.add(new MangaItem("Manga 2", "Author 2", "https://example.com/manga2"));
        mMangaList.add(new MangaItem("Manga 3", "Author 3", "https://example.com/manga3"));
        mMangaList.add(new MangaItem("Manga 4", "Author 4", "https://example.com/manga4"));

        // Hide the progress bar
        mProgressBarLoading.setVisibility(View.GONE);

        // Notify the adapter that the data has changed
        mAdapter.notifyDataSetChanged();
    }
}
