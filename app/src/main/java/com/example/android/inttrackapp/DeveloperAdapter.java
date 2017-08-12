package com.example.android.inttrackapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link DeveloperAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Developer} objects.
 */
public class DeveloperAdapter extends ArrayAdapter<Developer>  {

    private Context context;

    /**
     * Create a new {@link DeveloperAdapter} object.
     *  @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param developers is the list of {@link Developer}s to be displayed.
     */
    public DeveloperAdapter(Context context, List<Developer> developers) {
        super(context, 0, developers);
        this.context = context;
    }

    /**
     * Returns a list item view that displays information about the developer at the given position
     * in the list of developers.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.developer_list_item, parent, false);
        }

        // Find the developer at the given position in the list of developers
        Developer currentDeveloper = getItem(position);

        // Find the TextView in the developer_list_item.xml layout with the ID username_text_view.
        TextView usernameTextView = (TextView) listItemView.findViewById(R.id.username_text_view);
        // Get the developer's username from the currentDeveloper object and set this text on
        // the username TextView.
        usernameTextView.setText(currentDeveloper.getUsername());

        // Find the ImageView in the developer_list_item.xml layout with the ID image.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        // Check if an image is provided for this developer or not
        if (currentDeveloper.hasImage()) {
            // If an image is available, use Picasso library to display the provided image based on
            // the resource ID
            Picasso.with(context)
                    .load(currentDeveloper.getImageResourceId())
                    .into(imageView);
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imageView.setVisibility(View.GONE);
        }

        // Return the whole list item layout (containing 2 TextViews and 1 ImageView) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}