package com.androidprojects.loginnz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class adminESTAdapter extends RecyclerView.Adapter<adminESTAdapter.ViewHolder> {
    ArrayList<JSONObject> items = new ArrayList<>();
    @NonNull
    @Override
    public adminESTAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cardview, parent, false);
        return new adminESTAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adminESTAdapter.ViewHolder holder, int position) {
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

    public void addItem(JSONObject jsonObject) {
        items.add(jsonObject);
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, phone;
        String ID;
        ImageView userimg;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.cv_email);
            phone = itemView.findViewById(R.id.phone_cv);
            userimg = itemView.findViewById(R.id.userimg);

            cardView = itemView.findViewById(R.id.cardview);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity activity = (MainActivity) itemView.getContext();
                    activity.tempid(ID);
                    activity.FragmentView(9);
                }
            });
        }



        public void setItem(JSONObject item) throws JSONException {
            ID = item.getString("user_id");
            name.setText(item.getString("first_name")+" "+item.getString("last_name"));
            email.setText(item.getString("email"));
            phone.setText(item.getString("phone"));
        }
    }
}

