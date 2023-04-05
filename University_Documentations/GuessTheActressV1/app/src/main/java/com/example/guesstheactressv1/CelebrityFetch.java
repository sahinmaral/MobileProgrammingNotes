package com.example.guesstheactressv1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class CelebrityFetch extends AsyncTask<Void, Void, ArrayList<Celebrity>> {
    private final WeakReference<Context> context;

    CelebrityFetch(Context context){
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Celebrity> celebrities) {
        super.onPostExecute(celebrities);

        AppCompatActivity activity = (AppCompatActivity) context.get();

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, new StartingFragment(celebrities,activity));
        fragmentTransaction.commit();
    }

    @Override
    protected ArrayList<Celebrity> doInBackground(Void... voids) {
        ArrayList<Celebrity> celebrityList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.imdb.com/list/ls052816682/").get();

            Elements celebrityCardElements = doc.select(".lister-item.mode-detail");
            for (Element celebrityCardElement : celebrityCardElements) {
                Element imageElement = celebrityCardElement.selectFirst(".lister-item-image img");

                String name = imageElement.absUrl("alt").replace("https://www.imdb.com/list/ls052816682/","");
                String imageUrl = imageElement.absUrl("src");

                InputStream input = new java.net.URL(imageUrl).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);

                celebrityList.add(new Celebrity(name,bitmap));
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return celebrityList;
    }
}
