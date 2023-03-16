package com.example.learnlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.learnlistview.models.Country;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Country> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Changing language
/*        Locale locale2 = new Locale("en");
        Locale.setDefault(locale2);
        Configuration config2 = new Configuration();
        config2.locale = locale2;
        getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());*/

        listView = (ListView) findViewById(R.id.listViewCountries);

        try {
            countries = readXMLFileAndParse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] countryNames = countries.stream().map(country -> country.getName()).toArray(size -> new String[size]);

        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, countryNames);
        listView.setAdapter(countriesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), countries.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<Country> readXMLFileAndParse() throws XmlPullParserException, IOException {
        XmlPullParserFactory factory;
        factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        InputStream stream = getApplicationContext().getAssets().open("countries.xml");
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(stream, null);

        return parseXML(parser);
    }

    private ArrayList<Country> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {

        ArrayList<Country> countries = null;
        int eventType = parser.getEventType();
        Country country = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    countries = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("country")) {
                        country = new Country();
                        country.setId(parser.getAttributeValue(null, "name"));
                    } else if (country != null) {
                        if (name.equals("name")) {
                            country.setName(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("country") && country != null) {
                        countries.add(country);
                    }
            }
            eventType = parser.next();
        }
        return countries;
    }
}