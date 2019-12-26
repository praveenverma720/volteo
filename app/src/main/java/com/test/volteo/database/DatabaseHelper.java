package com.test.volteo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.volteo.database.models.EmployeeData;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "employee_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create Employee table
        db.execSQL(EmployeeData.CREATE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + EmployeeData.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    // Inserting data into db
    public long insertEmployeeData(String name,String qualification,String year,String branch,String college_name,String percentage) {

        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EmployeeData.EMPLOYEE_NAME, name);
        values.put(EmployeeData.EMPLOYEE_QUALIFICATION,qualification);
        values.put(EmployeeData.PASSING_YEAR,year);
        values.put(EmployeeData.BRANCH,branch);
        values.put(EmployeeData.COLLEGE_NAME,college_name);
        values.put(EmployeeData.PERCENTAGE,percentage);

        // insert row
        long id = db.insert(EmployeeData.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    //Getting Data from DB
    public EmployeeData getData(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EmployeeData.TABLE_NAME,
                new String[]{EmployeeData.EMPLOYEE_ID, EmployeeData.EMPLOYEE_NAME, EmployeeData.EMPLOYEE_QUALIFICATION,EmployeeData.PASSING_YEAR,
                        EmployeeData.BRANCH,EmployeeData.COLLEGE_NAME,EmployeeData.PERCENTAGE},
                EmployeeData.EMPLOYEE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare employee object
        EmployeeData employee = new EmployeeData(
                cursor.getInt(cursor.getColumnIndex(EmployeeData.EMPLOYEE_ID)),
                cursor.getString(cursor.getColumnIndex(EmployeeData.EMPLOYEE_NAME)),
                cursor.getString(cursor.getColumnIndex(EmployeeData.EMPLOYEE_QUALIFICATION)),
                cursor.getString(cursor.getColumnIndex(EmployeeData.PASSING_YEAR)),
                cursor.getString(cursor.getColumnIndex(EmployeeData.BRANCH)),
                cursor.getString(cursor.getColumnIndex(EmployeeData.COLLEGE_NAME)),
                cursor.getString(cursor.getColumnIndex(EmployeeData.PERCENTAGE)));

        // close the db connection
        cursor.close();

        return employee;
    }

    // geting the all employee data in list
    public List<EmployeeData> getAllEmployee() {
        List<EmployeeData> employeeData = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + EmployeeData.TABLE_NAME + " ORDER BY " +
                EmployeeData.EMPLOYEE_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EmployeeData employee = new EmployeeData();
                employee.setId(cursor.getInt(cursor.getColumnIndex(EmployeeData.EMPLOYEE_ID)));
                employee.setName(cursor.getString(cursor.getColumnIndex(EmployeeData.EMPLOYEE_NAME)));
                employee.setQualification(cursor.getString(cursor.getColumnIndex(EmployeeData.EMPLOYEE_QUALIFICATION)));
                employee.setYear(cursor.getString(cursor.getColumnIndex(EmployeeData.PASSING_YEAR)));
                employee.setBranch(cursor.getString(cursor.getColumnIndex(EmployeeData.BRANCH)));
                employee.setCollege_name(cursor.getString(cursor.getColumnIndex(EmployeeData.COLLEGE_NAME)));
                employee.setPercentage(cursor.getString(cursor.getColumnIndex(EmployeeData.PERCENTAGE)));

                employeeData.add(employee);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return employee list
        return employeeData;
    }

    //Update Empolyee Data
    public int updateData(EmployeeData employeeData,long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EmployeeData.EMPLOYEE_NAME, employeeData.getName());
        values.put(EmployeeData.EMPLOYEE_QUALIFICATION,employeeData.getQualification());
        values.put(EmployeeData.PASSING_YEAR,employeeData.getYear());
        values.put(EmployeeData.BRANCH,employeeData.getBranch());
        values.put(EmployeeData.COLLEGE_NAME,employeeData.getCollege_name());
        values.put(EmployeeData.PERCENTAGE,employeeData.getPercentage());

        // updating row
        return db.update(EmployeeData.TABLE_NAME, values, EmployeeData.EMPLOYEE_ID + " = ?",
                new String[]{String.valueOf(id)});
    }


    public int getEmployeeCount() {
        String countQuery = "SELECT  * FROM " + EmployeeData.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }


}
