package com.example.exerciseprescription;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseprescription.class2.DoctorEPModel;
import com.example.exerciseprescription.class2.EPmodel;

import java.util.ArrayList;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.MyViewHolder> {
    Context context;
    ArrayList<EPmodel> list;
//    public static final String PATIENT_ID = "PATIENTID";

    public RecyclerAdapter2(Context context, ArrayList<EPmodel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_data_cont2,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EPmodel model = list.get(position);
        holder.drNameTV.setText(model.getDrName());
        holder.drEmailTV.setText(model.getDrEmail());
        holder.weekTV.setText(model.getWeek());
        holder.durationTV.setText(model.getDuration() + "/week");
        holder.intensityTV.setText(model.getIntensity());
        holder.aerobicTV.setText(model.getAerobic());
        holder.strengthTV.setText(model.getStrength());
        holder.flexibilityTV.setText(model.getFlexibility());
        holder.noteTV.setText(model.getNote());
        holder.dateTV.setText(model.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent2 = new Intent(context,AdminProgressChartOption.class);
//                intent2.putExtra(PATIENT_ID,model.getId());
//                context.startActivity(intent2);
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView drNameTV,drEmailTV,weekTV,durationTV,intensityTV,aerobicTV,strengthTV,flexibilityTV,noteTV,dateTV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            drNameTV = itemView.findViewById(R.id.drNameTV);
            drEmailTV = itemView.findViewById(R.id.drEmailTV);
            weekTV = itemView.findViewById(R.id.weekTV);
            durationTV = itemView.findViewById(R.id.durationTV);
            intensityTV = itemView.findViewById(R.id.intensityTV);
            aerobicTV = itemView.findViewById(R.id.aerobicTV);
            strengthTV = itemView.findViewById(R.id.strengthTV);
            flexibilityTV = itemView.findViewById(R.id.flexibilityTV);
            noteTV = itemView.findViewById(R.id.noteTV);
            dateTV = itemView.findViewById(R.id.dateTV);
        }
    }
}
