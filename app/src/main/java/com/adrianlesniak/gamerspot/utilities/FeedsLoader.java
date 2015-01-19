package com.adrianlesniak.gamerspot.utilities;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.adrianlesniak.gamerspot.models.NewsFeed;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Adrian on 18-Jan-15.
 */
public class FeedsLoader extends AsyncTaskLoader<ArrayList<NewsFeed>> {

    public FeedsLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<NewsFeed> loadInBackground() {

        ArrayList<NewsFeed> result = new ArrayList<>();

        try {
            String s = run("http://1-dot-gamerspot-0914.appspot.com/getall?service=gamespot");
            JSONArray dataJSON = new JSONArray(s);

            JSONObject dataTemp;

            for (int i = 0; i < dataJSON.length(); i++) {

                dataTemp = dataJSON.getJSONObject(i);

                NewsFeed feed = new NewsFeed();
                feed.setGuid(dataTemp.getString("guid"));
                feed.setTitle(dataTemp.getString("title"));
                feed.setLink(dataTemp.getString("link"));
                feed.setDescription(dataTemp.getString("description"));
                feed.setDate(dataTemp.getString("pubDate"));
                feed.setCreator(dataTemp.getString("creator"));
                feed.setProvider(dataTemp.getString("service"));

                result.add(feed);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = Utils.getsOkHttpClient().newCall(request).execute();
        return response.body().string();
    }
}
