package meber.musicherring;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by martin on 2/12/17.
 */

public class ASyncHttp extends AsyncTask<ASyncHttpParamses, Void, Void>{
    String str;
    BufferedReader in;

    @Override
    protected Void doInBackground(ASyncHttpParamses... paramses) {
        String URL = paramses[0].URL;
        ArrayList<String> PlayList = paramses[0].PlayList;

        try {
            URL urls = new URL(URL);
            in = new BufferedReader(new InputStreamReader(urls.openStream()));
            while ((str = in.readLine()) != null) {
                PlayList.add(str);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
