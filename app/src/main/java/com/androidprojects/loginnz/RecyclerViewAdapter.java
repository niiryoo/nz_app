package com.androidprojects.loginnz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    // 아이템뷰 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timesheet_day;
        TextView startTime_am;
        TextView startTime_pm;
        TextView endTime_am;
        TextView endTime_pm;
        TextView depart_am;
        TextView depart_pm;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            timesheet_day = itemView.findViewById(R.id.timesheet_day);
            startTime_am = itemView.findViewById(R.id.tv_startTime_am);
            startTime_pm = itemView.findViewById(R.id.tv_startTime_pm);
            endTime_am = itemView.findViewById(R.id.tv_endTime_am);
            endTime_pm = itemView.findViewById(R.id.tv_endTime_pm);
            depart_am = itemView.findViewById(R.id.tv_depart_am);
            depart_pm = itemView.findViewById(R.id.tv_depart_pm);

        }
    }

    private ArrayList<RecyclerViewItem_ts> mList = null;

    public RecyclerViewAdapter(ArrayList<RecyclerViewItem_ts> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.timesheet_item, parent, false);
        RecyclerViewAdapter.ViewHolder vh = new RecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        RecyclerViewItem_ts item = mList.get(position);

        holder.timesheet_day.setText(item.getDay());
        holder.startTime_am.setText(item.getStartTime_am());
        holder.startTime_pm.setText(item.getStartTime_pm());
        holder.endTime_am.setText(item.getEndTime_am());
        holder.endTime_pm.setText(item.getEndTime_pm());
        holder.depart_am.setText(item.getDepart_am());
        holder.depart_pm.setText(item.getDepart_pm());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
