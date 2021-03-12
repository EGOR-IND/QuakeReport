package egor_ind.apps.quakereport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** URL to query the USGS dataset for earthquake information */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-01-01&endtime=2019-01-03";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuakeAsyncTask asyncTask = new QuakeAsyncTask();
        asyncTask.execute();
    }

    private void setRecyclerView(ArrayList<QuakeInfo> quakeInfoList) {
        RecyclerView.Adapter adapter = new QuakeInfoAdapter(MainActivity.this, quakeInfoList);

        RecyclerView recyclerView = findViewById(R.id.quakeInfoRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private class QuakeAsyncTask extends AsyncTask<URL, Void, ArrayList<QuakeInfo>> {

        @Override
        protected ArrayList<QuakeInfo> doInBackground(URL... urls) {
            URL url = createURL(USGS_REQUEST_URL);
            String jsonResponse = "";
            ArrayList<QuakeInfo> quakeInfoList = null;
            try {
                jsonResponse = makeHttpRequest(url);
                Log.d("YO", jsonResponse);
                quakeInfoList = jsonDataExtracter(jsonResponse);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return quakeInfoList;
        }

        @Override
        protected void onPostExecute(ArrayList<QuakeInfo> quakeInfoList) {
            if (quakeInfoList == null) {
                return;
            }
            setRecyclerView(quakeInfoList);
        }

        private URL createURL(String urlStr) {
            URL url = null;
            try {
                url = new URL(urlStr);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error while creating url", e);
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = getJsonResponse(inputStream);
                Log.d("JO", jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
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

        private String getJsonResponse(InputStream inputStream) throws IOException {
            StringBuilder jsonStrBuild = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    jsonStrBuild.append(line);
                    line = bufferedReader.readLine();
                }
            }
            return jsonStrBuild.toString();
        }

        private ArrayList<QuakeInfo> jsonDataExtracter(String quakeJSON) throws JSONException {
            Log.d("HO", quakeJSON);
            JSONObject root = new JSONObject(quakeJSON);
            JSONArray quakeInfoArray = root.getJSONArray("features");

            ArrayList<QuakeInfo> extractedList = new ArrayList<>();
            for (int i=0; i<quakeInfoArray.length(); i++) {
                JSONObject quakeInfoItem = quakeInfoArray.getJSONObject(i).getJSONObject("properties");
                extractedList.add(new QuakeInfo(quakeInfoItem.getDouble("mag"),
                        quakeInfoItem.getString("place"), quakeInfoItem.getLong("time"), quakeInfoItem.getString("url")));
            }

            return extractedList;
        }
    }
}