package com.test.volteo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.volteo.R;
import com.test.volteo.adapter.EmployeeAdapter;
import com.test.volteo.database.DatabaseHelper;
import com.test.volteo.database.models.EmployeeData;
import com.test.volteo.utils.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ShowUserDataActivity extends AppCompatActivity {
    private static final String TAG = "ShowUserDataActivity";

    private EmployeeAdapter mAdapter;
    private List<EmployeeData> employeeDataList = new ArrayList<>();
    private TextView eNameTv,eQualificationTV,eCollegeTV,eYearTV,eBranchET,ePerctengeET;
    private ImageView addEmployeeIV;

    private DatabaseHelper db;
    private long id;
    private EmployeeData data = new EmployeeData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_employee_data_activity);

        id = getIntent().getLongExtra("DB_ID",0);
        // Database initialisation
        db = new DatabaseHelper(this);
        employeeDataList.addAll(db.getAllEmployee());

        Log.d(TAG,String.valueOf(id));
        data = db.getData(id);

        initViews();
    }

    // inilitiosn of element
    public void initViews(){

        eNameTv = findViewById(R.id.eNameTv);
        eQualificationTV = findViewById(R.id.eQualificationTV);
        eCollegeTV = findViewById(R.id.eCollegeTV);
        eYearTV = findViewById(R.id.eYearTV);
        eBranchET = findViewById(R.id.eBranchET);
        ePerctengeET = findViewById(R.id.ePerctengeET);

        eNameTv.setText(data.getName());
        eQualificationTV.setText(data.getQualification());
        eCollegeTV.setText(data.getCollege_name());
        eYearTV.setText(data.getYear());
        eBranchET.setText(data.getBranch());
        ePerctengeET.setText(data.getPercentage());

    }

    //on back press
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShowUserDataActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
