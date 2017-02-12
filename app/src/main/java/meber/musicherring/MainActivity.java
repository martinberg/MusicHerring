package meber.musicherring;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button play_audio_button, pause_audio_button;
    private String url, newurl, str, str2;
    private Intent intent;
    private URL urls;
    private ArrayList<String> PlayList;
    private BufferedReader in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_audio_button = (Button) findViewById(R.id.play);
        pause_audio_button = (Button) findViewById(R.id.stop);

        mediaPlayer = new MediaPlayer();
        PlayList = new ArrayList<String>();

        intent = getIntent();

        if (intent.getScheme() != null) {
            url = intent.getDataString();
            //readURLs(url);
            //newurl = allURls.get(0);
            //playURL(newurl);
            /*if (url.contains("m3u")) {
                readURLs(url);
            } else {
                addToPlaylist(url);
            }*/
            //addToPlaylist(url);

            //url = "http://music.h4xxel.org/Eminem/2003%20-%20The%20Eminem%20Show/allrecursive.m3u";

            m3uToPlayList(url);
            startPlayList();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                if (getPlayList().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Spellistan är tom", Toast.LENGTH_SHORT).show();
                } else {
                    delFirstFromPlayList();
                    startPlayList();
                }
            }
        });

        play_audio_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Spelar ljud", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();
            }
        });

        pause_audio_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausar ljud", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
            }
        });
    }

    private void m3uToPlayList(String URL) {
        if (URL == null) {
            Toast.makeText(getApplicationContext(), "URL:en är tom", Toast.LENGTH_SHORT).show();
        } else {
            ASyncHttpParamses paramses = new ASyncHttpParamses(url, PlayList);
            ASyncHttp aSyncHttp = new ASyncHttp();
            aSyncHttp.execute(paramses);
        }
    }

    private void addToPlaylist(String URL){
        PlayList.add(URL);
    }

    private ArrayList<String> getPlayList(){
        return PlayList;
    }

    private void delFirstFromPlayList(){
        PlayList.remove(0);
    }

    private void startPlayList(){
        if (!getPlayList().isEmpty()){
            try {
                mediaPlayer.setDataSource(getPlayList().get(0));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Spellistan är tom", Toast.LENGTH_SHORT).show();
        }
    }
}