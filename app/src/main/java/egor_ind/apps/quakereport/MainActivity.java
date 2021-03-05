package egor_ind.apps.quakereport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void setRecyclerView(ArrayList<QuakeInfo> quakeInfoList) {
        RecyclerView.Adapter adapter = new QuakeInfoAdapter(quakeInfoList);

        RecyclerView recyclerView = findViewById(R.id.quakeInfoRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}