package egor_ind.apps.quakereport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import android.text.TextUtils;
import android.util.Log;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static ArrayList<QuakeInfo> fetchQuakeData(String urlStr) {
        URL url = QueryUtils.createURL(urlStr);
        ArrayList<QuakeInfo> quakeInfoList = null;
        try {
            String jsonResponse = QueryUtils.makeHttpRequest(url);
//                Log.d("YO", jsonResponse);
            quakeInfoList = QueryUtils.jsonDataExtractor(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quakeInfoList;
    }

    private static URL createURL(String urlStr) {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error while creating url", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = getJsonResponse(inputStream);
            }
//                Log.d("JO", jsonResponse);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String getJsonResponse(InputStream inputStream) {
        StringBuilder jsonStrBuild = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String line = bufferedReader.readLine();
                while (line != null) {
                    jsonStrBuild.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonStrBuild.toString();
    }

    private static ArrayList<QuakeInfo> jsonDataExtractor(String quakeJSON) {
        if (TextUtils.isEmpty(quakeJSON)) {
            return null;
        }

        ArrayList<QuakeInfo> extractedList = null;
        try {
            JSONObject root = new JSONObject(quakeJSON);
            JSONArray quakeInfoArray = root.getJSONArray("features");

            extractedList = new ArrayList<>();
            for (int i=0; i<quakeInfoArray.length(); i++) {
                JSONObject quakeInfoItem = quakeInfoArray.getJSONObject(i).getJSONObject("properties");
                extractedList.add(new QuakeInfo(quakeInfoItem.getDouble("mag"),
                        quakeInfoItem.getString("place"), quakeInfoItem.getLong("time"), quakeInfoItem.getString("url")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return extractedList;
    }
}
