package egor_ind.apps.quakereport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** URL to query the USGS dataset for earthquake information */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-01-01&endtime=2019-01-03&minmagnitude=1";

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
            return QueryUtils.fetchQuakeData(USGS_REQUEST_URL);
        }

        @Override
        protected void onPostExecute(ArrayList<QuakeInfo> quakeInfoList) {
            if (quakeInfoList == null) {
                return;
            }
            setRecyclerView(quakeInfoList);
        }
    }
}