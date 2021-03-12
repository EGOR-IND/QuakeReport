package egor_ind.apps.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuakeInfoAdapter extends RecyclerView.Adapter<QuakeInfoAdapter.MyViewHolder> {

    private ArrayList<QuakeInfo> quakeInfoList;
    private Context context;

    public QuakeInfoAdapter(Context context, ArrayList<QuakeInfo> quakeInfoList) {
        this.quakeInfoList = quakeInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quake_info_item_view, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mag.setText(String.valueOf(quakeInfoList.get(position).getMag()));
        //holder.mag.setBackgroundTintList(ContextCompat.getColorStateList(context, getRangeColor(quakeInfoList.get(position).getMag())));
        holder.mag.getBackground().setTint(context.getResources().getColor(getRangeColor(quakeInfoList.get(position).getMag())));
        holder.direction.setText(quakeInfoList.get(position).getDirection());
        holder.place.setText(quakeInfoList.get(position).getPlace());
        holder.date.setText(quakeInfoList.get(position).getDate());
        holder.time.setText(quakeInfoList.get(position).getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(quakeInfoList.get(position).getUrl())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return quakeInfoList.size();
    }

    private int getRangeColor(double mag) {
        switch ((int) mag) {
            case 1: return R.color.magnitude1;
            case 2: return R.color.magnitude2;
            case 3: return R.color.magnitude3;
            case 4: return R.color.magnitude4;
            case 5: return R.color.magnitude5;
            case 6: return R.color.magnitude6;
            case 7: return R.color.magnitude7;
            case 8: return R.color.magnitude8;
            case 9: return R.color.magnitude9;
            case 10: return R.color.magnitude10plus;
            default: return -1;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mag, direction, place, date, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mag = itemView.findViewById(R.id.mag);
            this.direction = itemView.findViewById(R.id.direction);
            this.place = itemView.findViewById(R.id.place);
            this.date = itemView.findViewById(R.id.date);
            this.time = itemView.findViewById(R.id.time);
        }
    }
}
