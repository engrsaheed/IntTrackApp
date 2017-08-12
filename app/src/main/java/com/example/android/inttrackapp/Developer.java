package com.example.android.inttrackapp;

/**
 * An {@link Developer} object contains information related to a single developer.
 */
public class Developer {

    /** Username of the developer */
    private String mUsername;

    /** Image resource ID for the developer */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this developer */
    private static final int NO_IMAGE_PROVIDED = -1;

    /** Website URL of the developer */
    private String mUrl;

    /**
     * Constructs a new {@link Developer} object.
     *
     * @param username is the username of the developer on github
     * @param imageResourceId is the drawable resource ID for the image associated with the
     *                        developer
     * @param url is the website URL to find more details about the developer
     */
    public Developer(String username, int imageResourceId, String url) {
        mUsername = username;
        mImageResourceId = imageResourceId;
        mUrl = url;
    }

    /**
     * Returns the username of the developer.
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * Return the image resource ID of the developer.
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this developer.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /**
     * Returns the website URL to find more information about the developer.
     */
    public String getUrl() {
        return mUrl;
    }
}