package com.example.sala01.doesangue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sala01 on 15/05/2017.
 */

public class DownloadImage extends AsyncTask<String,Integer,Bitmap> {

    private Context context;
    private MaterialDialog materialDialog;
    private DownloadListener listener;
    private String URL;


    public DownloadImage(Context context, DownloadListener listener, String URL) {
        this.context = context;
        this.listener = listener;
        this.URL = URL;
    }

    @Override
    protected void onPreExecute(){
        materialDialog = new MaterialDialog.Builder(context)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();
        Response response = null;

        try{
            response = okHttpClient.newCall(request).execute();
        } catch (Exception e){
            e.printStackTrace();
        }

        if(response != null){
            InputStream inputStream = response.body().byteStream();

            return BitmapFactory.decodeStream(inputStream);
        }else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        if(bitmap != null){
            listener.getImg(bitmap);
        }

        materialDialog.dismiss();
    }

}
