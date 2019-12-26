package com.test.volteo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.test.volteo.R;
import com.test.volteo.database.DatabaseHelper;
import com.test.volteo.database.models.EmployeeData;

import java.util.ArrayList;
import java.util.List;

public class AddOrEditEmployeeDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String TAG = "AddOrEditEmployeeDataAc";

    private EditText eNameET, eCollegeET, eBranchET, ePerctengeET;
    private Spinner eYearSpinner;
    private CheckBox ch1, ch2, ch3;

    private DatabaseHelper db;
    private List<EmployeeData> employeeDataList = new ArrayList<>();

    private String name, qualification, employeeYear, branch, college, marks;
    private int id;

    private EmployeeData data = new EmployeeData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_or_edit_employee_data_activity);

        id = getIntent().getIntExtra("DB_ID", 0);

        // Database initialisation
        db = new DatabaseHelper(this);
        employeeDataList.addAll(db.getAllEmployee());

        initViews();
    }

    public void initViews() {

        eNameET = findViewById(R.id.eNameET);
        eCollegeET = findViewById(R.id.eCollegeET);
        eBranchET = findViewById(R.id.eBranchET);
        ePerctengeET = findViewById(R.id.ePerctengeET);
        eYearSpinner = findViewById(R.id.eYearSpinner);
        ch1 = findViewById(R.id.checkBox1);
        ch2 = findViewById(R.id.checkBox2);
        ch3 = findViewById(R.id.checkBox3);
        findViewById(R.id.submitBtn).setOnClickListener(this);

        ch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ch1.isChecked()){
                    ch2.setChecked(false);
                    ch3.setChecked(false);
                    qualification = "B.Tech";
                }
            }
        });

        ch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ch2.isChecked()){
                    ch1.setChecked(false);
                    ch3.setChecked(false);
                    qualification = "M.Tech";
                }
            }
        });

        ch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ch3.isChecked()){
                    ch1.setChecked(false);
                    ch2.setChecked(false);
                    qualification = "Phd";
                }
            }
        });

        // if employee is already exist
        if (id > 0) {

            updateUI();
        }

        eYearSpinner.setOnItemSelectedListener(this);

        // Year Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("2019");
        categories.add("2018");
        categories.add("2017");
        categories.add("2016");
        categories.add("2015");
        categories.add("2014");
        categories.add("2013");
        categories.add("2012");
        categories.add("2011");
        categories.add("2010");
        categories.add("2009");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        eYearSpinner.setAdapter(dataAdapter);


    }

    //Updating UI with data
    public void updateUI() {

        data = db.getData(Long.valueOf(id));

        if (data.getName().length() > 0) {
            eNameET.setFocusable(false);
            eNameET.setEnabled(false);
            name = data.getName();
            eNameET.setText(name);
        }

        if (!(data.getCollege_name() == null || data.getCollege_name().equals(""))) {
            eCollegeET.setFocusable(false);
            eCollegeET.setEnabled(false);
            college = data.getCollege_name();
            eCollegeET.setText(college);
        }

        if (!(data.getBranch() == null || data.getBranch().equals(""))) {
            eBranchET.setFocusable(false);
            eBranchET.setEnabled(false);
            branch = data.getBranch();
            eBranchET.setText(branch);
        }


        if (!(data.getQualification() == null || data.getQualification().equals(""))) {

            if (data.getQualification().equals("B.Tech")) {
                ch1.setChecked(true);
                ch1.setEnabled(false);
                ch2.setEnabled(false);
                ch3.setEnabled(false);
                qualification = data.getQualification();
            } else if (data.getQualification().equals("M.Tech")) {
                ch2.setChecked(true);
                ch1.setEnabled(false);
                ch2.setEnabled(false);
                ch3.setEnabled(false);
                qualification = data.getQualification();
            } else if (data.getQualification().equals("Phd")) {
                ch3.setChecked(true);
                ch1.setEnabled(false);
                ch2.setEnabled(false);
                ch3.setEnabled(false);
                qualification = data.getQualification();
            }
        }


        if (!(data.getYear() == null || data.getYear().equals(""))) {
            eYearSpinner.setFocusable(false);
            eYearSpinner.setEnabled(false);
            employeeYear = data.getYear();

            List<String> categories = new ArrayList<String>();
            categories.add(employeeYear);
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
            // attaching data adapter to spinner
            eYearSpinner.setAdapter(dataAdapter);

        }

        if (!(data.getPercentage() == null || data.getPercentage().equals(""))) {
            ePerctengeET.setFocusable(false);
            ePerctengeET.setEnabled(false);
            marks = data.getPercentage();
            ePerctengeET.setText(marks);
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
// On selecting a spinner item
        employeeYear = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.submitBtn:
                if (id > 0) {
                    //getting data from view
                    name = eNameET.getText().toString();
                    college = eCollegeET.getText().toString();
                    branch = eBranchET.getText().toString();
                    marks = ePerctengeET.getText().toString();

                    updateEmployeeProfile(name, qualification, employeeYear, branch, college, marks);
                } else {
                    if (eNameET.getText().toString() == null || eNameET.getText().toString().equals("")) {
                        eNameET.setError("Enter Name First");
                    } else {

                        //getting data from view
                        name = eNameET.getText().toString();
                        college = eCollegeET.getText().toString();
                        branch = eBranchET.getText().toString();
                        marks = ePerctengeET.getText().toString();

                        Log.d(TAG, name + " >> " + qualification + " >> " + employeeYear + " >> " + branch + " >> " + college + " >> " + marks);
                        createNote(name, qualification, employeeYear, branch, college, marks);
                    }
                }
                break;
        }
    }


    /**
     * Inserting new employee in db
     * and refreshing the list
     */
    private void createNote(String name, String qualification, String year, String branch, String college_name, String percentage) {
        // inserting employee in db and getting
        // newly inserted employee id
        long id = db.insertEmployeeData(name, qualification, year, branch, college_name, percentage);

        Log.d(TAG, String.valueOf(id));
        // get the newly inserted employee from db
        EmployeeData n = db.getData(id);

        if (n != null) {

            Toast.makeText(getApplicationContext(), "New Employee Added succesfully", Toast.LENGTH_SHORT).show();
            // adding new employee to array list at 0 position
            employeeDataList.add(0, n);

            Intent intent = new Intent(AddOrEditEmployeeDataActivity.this, ShowUserDataActivity.class);
            intent.putExtra("DB_ID", id);
            startActivity(intent);
            finish();

        }
    }


    /**
     * Updating employee in db and updating
     * item in the list by its position
     */
    private void updateEmployeeProfile(String name, String qualification, String year, String branch, String college_name, String percentage) {
        EmployeeData n = new EmployeeData();
        // updating employee text
        n.setName(name);
        n.setQualification(qualification);
        n.setYear(year);
        n.setBranch(branch);
        n.setCollege_name(college_name);
        n.setPercentage(percentage);

        Log.d(TAG,">>>"+String.valueOf(id));

        // updating employee in db
        db.updateData(n, id);

        Toast.makeText(getApplicationContext(), " Employee Data Updated succesfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddOrEditEmployeeDataActivity.this, ShowUserDataActivity.class);
        intent.putExtra("DB_ID", Long.valueOf(id));
        startActivity(intent);
        finish();

    }
}
