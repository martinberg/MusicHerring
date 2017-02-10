package meber.musicherring;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private String url, newurl, str;
    private Intent intent;
    public ArrayList<String> allURls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_audio_button = (Button) findViewById(R.id.play);
        pause_audio_button = (Button) findViewById(R.id.stop);

        mediaPlayer = new MediaPlayer();
        intent = getIntent();

        if (intent.getScheme() != null) {
            url = intent.getDataString();
            readURLs(url);
            newurl = allURls.get(0);
            playURL(newurl);
        }

        play_audio_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
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

    private ArrayList<String> readURLs(String url_in) {
        if (url_in == null) return null;
        try {
            URL urls = new URL(url_in);
            BufferedReader in = new BufferedReader(new InputStreamReader(urls.openStream()));
            while ((str = in.readLine()) != null) {
                allURls.add(str);
            }
            in.close();
            return allURls;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void playURL(String URL) {
        if (URL != null) {
            try {
                Log.d("URL", URL);
                mediaPlayer.setDataSource(URL);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            play_audio_button.setEnabled(true);
            pause_audio_button.setEnabled(true);
        } else {
            Toast.makeText(getApplicationContext(), "URL:en tom", Toast.LENGTH_SHORT).show();
        }
    }
}