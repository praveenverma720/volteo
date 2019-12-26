package com.test.volteo.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EmployeeAdapter mAdapter;
    private List<EmployeeData> employeeDataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView noNotesView;
    private ImageView addEmployeeIV;
    private Toast toast;
    private long lastBackPressTime = 0;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // Database initialisation
        db = new DatabaseHelper(this);
        employeeDataList.addAll(db.getAllEmployee());
        toggleEmptyList();
    }

    public void initViews(){

        recyclerView = findViewById(R.id.recycler_view);

        mAdapter = new EmployeeAdapter(this, employeeDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);


        // add new employee data
        addEmployeeIV = findViewById(R.id.addEmployeeIV);
        addEmployeeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddOrEditEmployeeDataActivity.class);
                startActivity(intent);
            }
        });
    }



    /**
     * Toggling list and empty employee view
     */
    private void toggleEmptyList() {

        if (db.getEmployeeCount() > 0) {
            Toast.makeText(getApplicationContext(),"Add Some Employee Data First",Toast.LENGTH_SHORT).show();
        } else {
           // noemployeeView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        employeeDataList.addAll(db.getAllEmployee());
        mAdapter.notifyDataSetChanged();
    }


    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
                // make layout invisible since last fragment will be removed
                if (this.lastBackPressTime < System.currentTimeMillis() - 3000) {
                    toast = Toast.makeText(this, "Press back again to close app", 3000);
                    toast.show();
                    this.lastBackPressTime = System.currentTimeMillis();

                } else {
                    if (toast != null) {
                        toast.cancel();
                    }
                    super.onBackPressed();
                }


        }
    }

