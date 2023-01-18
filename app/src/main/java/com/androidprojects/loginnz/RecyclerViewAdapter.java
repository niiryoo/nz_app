package com.androidprojects.loginnz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<JSONObject> items = new ArrayList<>();

    public void addItem(JSONObject jsonObject) {
        items.add(jsonObject);
    }

    // 아이템뷰 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView startTime, endTime, depart;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            depart = itemView.findViewById(R.id.depart_code);
        }

        public void setItem(JSONObject item) throws JSONException  {
            startTime.setText(item.getString("start_time"));
            endTime.setText(item.getString("end_time"));
            depart.setText(item.getString("department_id"));

        }
    }

    //private ArrayList<RecyclerViewItem_ts> mList = null;

    //public RecyclerViewAdapter(mList) {this.mList = mList;}

    //public RecyclerViewAdapter() {}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.timesheet_item2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    // 연결
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        JSONObject item = items.get(position);
        try {
            holder.setItem(item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
