package com.tennismate.tennismate.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.tennismate.tennismate.MateMatcher.RecyclerAdapter;
import com.tennismate.tennismate.user.UserContext;

import java.io.InputStream;

/**
 * This class is responsible for download an Image in async operation,
 * and returns the image as a Bitmap.
 */

public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

    private UserContext userContext;
    private RecyclerAdapter mAdapter; // optional. had to do it ugly.

    public ImageDownloader(UserContext userContext){
        this.userContext = userContext;
    }

    public ImageDownloader(UserContext userContext, RecyclerAdapter adapter){
        this.userContext = userContext;
        this.mAdapter = adapter;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        this.userContext.setUserPhoto(bitmap);
        if( mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

}
