package com.androidprojects.loginnz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<JSONObject> items = new ArrayList<>();

    public void addItem(JSONObject jsonObject) {
        items.add(jsonObject);
    }

    // 아이템뷰 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView startTime, endTime, depart, note;
        String department_name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.et_startTime);
            endTime = itemView.findViewById(R.id.et_endTime);
            depart = itemView.findViewById(R.id.eT_depart);
            note = itemView.findViewById(R.id.et_note);
        }

        public void setItem(JSONObject item) throws JSONException  {
            String start_time = item.getString("start_time");
            String end_time = item.getString("end_time");
            if(start_time != "null") {
                String form1 = start_time.substring(0, 10);
                String form2 = start_time.substring(11, 16);
                startTime.setText(form1+" "+form2);
            }
            if(end_time != "null") {
                String form3 = end_time.substring(0, 10);
                String form4 = end_time.substring(11, 16);
                endTime.setText(form3+" "+form4);
            }
            codetodepartment(item.getString("department_id"));

            String notes = item.getString("notes");
            note.setText(notes);
        }

        private void codetodepartment(String code){
            Response.Listener<String> timesheetListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        department_name = jsonObject.getString("name");
                        depart.setText(department_name);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            };
            DepartmentGet DepartmentGet = new DepartmentGet(null,code, timesheetListener);
            RequestQueue Departmentqueue = Volley.newRequestQueue(context);
            Departmentqueue.add(DepartmentGet);
        };
    }

    //private ArrayList<RecyclerViewItem_ts> mList = null;

    //public RecyclerViewAdapter(mList) {this.mList = mList;}

    //public RecyclerViewAdapter() {}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.timesheet_item2, parent, false);
        context = parent.getContext();
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
