package com.example.android.inttrackapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeveloperActivity extends AppCompatActivity
        implements LoaderCallbacks<List<Developer>> {

    private static final String LOG_TAG = DeveloperActivity.class.getName();

    /** URL for developer data from the GITHUB dataset */
    private static final String GITHUB_REQUEST_URL =
            "https://api.github.com/search/users?q=location:lagos";

    /**
     * Constant value for the developer loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int DEVELOPER_LOADER_ID = 1;

    /** Adapter for the list of developers */
    private DeveloperAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView developerListView = (ListView) findViewById(R.id.list);


        // Create a new adapter that takes an empty list of developers as input
        mAdapter = new DeveloperAdapter(this, new ArrayList<Developer>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        developerListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected developer.
        developerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current developer that was clicked on
                Developer currentDeveloper = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri developerUri = Uri.parse(currentDeveloper.getUrl());

                // Create a new intent to view the developer URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, developerUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(DEVELOPER_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Developer>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new DeveloperLoader(this, GITHUB_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Developer>> loader, List<Developer> developers) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // If there is a valid list of {@link Developer}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (developers != null && !developers.isEmpty()) {
            mAdapter.addAll(developers);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Developer>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}