package com.example.frenchphrasesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    NodeList nodeContainer;
    DocumentBuilder documentBuilder = null;
    InputStream inputStream = null;
    int audioID = 0;
    LinearLayout linearLayoutButtonGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting french phrases from XML
        try {
            inputStream = getAssets().open("french_phrases.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = dbFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element allElement=document.getDocumentElement();
            allElement.normalize();
            nodeContainer = document.getElementsByTagName("phrase");

        }catch (Exception exception){
            Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();
        }

        linearLayoutButtonGroup = findViewById(R.id.linearLayoutButtonGroup);
        Field[] fields = R.raw.class.getFields();

        for(Field f : fields) {
            try {
                audioID = f.getInt(null);
                Button button = new Button(this);
                String audioXMLId = getResources().getResourceEntryName(audioID);

                Element element = getElement(audioXMLId);
                button.setText(getValue("english_text",element));

                button.setOnClickListener(v -> {
                    Toast.makeText(this,getValue("french_text",element),Toast.LENGTH_LONG).show();

                    //int mp3ResId = getResources().getIdentifier(String.valueOf(audioID),"raw",getApplication().getPackageName());

                    MediaPlayer mediaPlayer;
                    try {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), f.getInt(null));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    mediaPlayer.start();
                });
                linearLayoutButtonGroup.addView(button);
            } catch (Exception exception) {
                Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    private Element getElement(String audioID){
        for(int i=0; i< nodeContainer.getLength(); i++){
            Element element = (Element) nodeContainer.item(i);
            if(getValue("id", element).equals(audioID)) return element;
        }
        return null;
    }
}