package egor_ind.apps.quakereport;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuakeInfoAdapter extends RecyclerView.Adapter<QuakeInfoAdapter.MyViewHolder> {

    private ArrayList<QuakeInfo> quakeInfoList;

    public QuakeInfoAdapter(ArrayList<QuakeInfo> quakeInfoList) {
        this.quakeInfoList = quakeInfoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quake_info_item_view, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mag.setText(String.valueOf(quakeInfoList.get(position).getMag()));
        holder.place.setText(quakeInfoList.get(position).getPlace());
        holder.time.setText(quakeInfoList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return quakeInfoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mag, place, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mag = itemView.findViewById(R.id.mag);
            this.place = itemView.findViewById(R.id.place);
            this.time = itemView.findViewById(R.id.time);
        }
    }
}
