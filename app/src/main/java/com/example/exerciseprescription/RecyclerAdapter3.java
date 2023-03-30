package com.example.exerciseprescription;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseprescription.class2.EPmodel;

import java.util.ArrayList;

public class RecyclerAdapter3 extends RecyclerView.Adapter<RecyclerAdapter3.MyViewHolder> {
    Context context;
    ArrayList<EPmodel> list;
    public static final String PATIENT_ID = "PATIENTID";
    public static final String TIMESTAMP = "TIMESTAMP";


    public RecyclerAdapter3(Context context, ArrayList<EPmodel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_data_cont,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EPmodel model = list.get(position);
        holder.dateTV.setText(model.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(context,AdminProgressChartOption.class);
                intent2.putExtra(PATIENT_ID,model.getPtId());
                intent2.putExtra(TIMESTAMP,model.getTimeStamp());
                context.startActivity(intent2);
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView dateTV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.teamname);
        }
    }
}
