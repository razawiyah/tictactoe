package com.razawiyahdev.exerciseprescription;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.razawiyahdev.exerciseprescription.R;
import com.razawiyahdev.exerciseprescription.class2.DoctorEPModel;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<DoctorEPModel> list;
    public static final String PATIENT_ID = "PATIENTID";
    public static final String TIMESTAMP = "TIMESTAMP";


    public RecyclerAdapter(Context context, ArrayList<DoctorEPModel> list) {
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
//        EPmodel model = list.get(position);
        DoctorEPModel model = list.get(position);
        holder.teamname.setText(model.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(context,AdminProgressChartOption.class);
                intent2.putExtra(PATIENT_ID,model.getId());
//                intent2.putExtra(TIMESTAMP,model.getTimeStamp());
                context.startActivity(intent2);
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView teamname;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            teamname = itemView.findViewById(R.id.teamname);
        }
    }
}
