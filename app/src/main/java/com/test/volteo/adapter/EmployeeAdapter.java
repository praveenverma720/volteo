package com.test.volteo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.volteo.R;
import com.test.volteo.database.models.EmployeeData;
import com.test.volteo.view.AddOrEditEmployeeDataActivity;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {

    private Context context;
    private List<EmployeeData> employeeDataList;

    // Constructor to get data
    public EmployeeAdapter(Context context, List<EmployeeData> employeeDataList) {
        this.context = context;
        this.employeeDataList = employeeDataList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.employee_recycler_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        final EmployeeData employeeData = employeeDataList.get(position);

        myViewHolder.eNameTV.setText(employeeData.getName());
        myViewHolder.eCourseTV.setText(employeeData.getBranch());
        myViewHolder.eCollegeNameTV.setText(employeeData.getCollege_name());
        myViewHolder.ePassingYearTV.setText(employeeData.getYear());

        myViewHolder.eMainCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,AddOrEditEmployeeDataActivity.class);
                intent.putExtra("DB_ID",employeeData.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return employeeDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eNameTV, eCourseTV,eCollegeNameTV,ePassingYearTV;
        public CardView eMainCV;

        public MyViewHolder(View view) {
            super(view);
            eNameTV = view.findViewById(R.id.eNameTV);
            eCourseTV = view.findViewById(R.id.eCourseTV);
            eCollegeNameTV = view.findViewById(R.id.eCollegeNameTV);
            ePassingYearTV = view.findViewById(R.id.ePassingYearTV);
            eMainCV = view.findViewById(R.id.eMainCV);
        }
    }
}
