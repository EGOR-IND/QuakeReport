package egor_ind.apps.quakereport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            setRecyclerView(jsonDataExtracter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setRecyclerView(ArrayList<QuakeInfo> quakeInfoList) {
        RecyclerView.Adapter adapter = new QuakeInfoAdapter(MainActivity.this, quakeInfoList);

        RecyclerView recyclerView = findViewById(R.id.quakeInfoRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<QuakeInfo> jsonDataExtracter() throws JSONException {
        String quakeJSON = "{\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":7.2,\"place\":\"88km N of Yelizovo, Russia\",\"time\":1454124312220,\"updated\":1594162166283,\"tz\":720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20004vvx\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004vvx&format=geojson\",\"felt\":3,\"cdi\":3.4,\"mmi\":6.719,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":799,\"net\":\"us\",\"code\":\"20004vvx\",\"ids\":\",gcmt20160130032510,pt16030050,at00o1qxho,us20004vvx,gcmt20160130032512,atlas20160130032512,\",\"sources\":\",gcmt,pt,at,us,gcmt,atlas,\",\"types\":\",associate,cap,dyfi,finite-fault,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.958,\"rms\":1.19,\"gap\":17,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.2 - 88km N of Yelizovo, Russia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[158.5463,53.9776,177]},\"id\":\"us20004vvx\"}]}";

        JSONObject root = new JSONObject(quakeJSON);
        JSONArray quakeInfoArray = root.getJSONArray("features");

        ArrayList<QuakeInfo> extractedList = new ArrayList<>();
        for (int i=0; i<quakeInfoArray.length(); i++) {
            JSONObject quakeInfoItem = quakeInfoArray.getJSONObject(i).getJSONObject("properties");
            extractedList.add(new QuakeInfo(quakeInfoItem.getDouble("mag"),
                    quakeInfoItem.getString("place"), quakeInfoItem.getLong("time")));
        }

        return extractedList;
    }
}