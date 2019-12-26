package com.test.volteo.database.models;

public class EmployeeData {

    public static final String TABLE_NAME = "EmployeeData";

    public static final String EMPLOYEE_ID = "id";
    public static final String EMPLOYEE_NAME = "name";
    public static final String EMPLOYEE_QUALIFICATION = "qualification";
    public static final String PASSING_YEAR = "year";
    public static final String BRANCH = "branch";
    public static final String COLLEGE_NAME = "college_name";
    public static final String PERCENTAGE = "percentage";

    private int id;
    private String name,qualification,year,branch,college_name,percentage;

    // Create table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + EMPLOYEE_NAME + " TEXT,"
                    + EMPLOYEE_QUALIFICATION + " TEXT, "
                    + PASSING_YEAR + " TEXT, "
                    + BRANCH + " TEXT, "
                    + COLLEGE_NAME + " TEXT, "
                    + PERCENTAGE + " TEXT "
                    + ")";

    public EmployeeData(){

    }

    public EmployeeData(int id,String name,String qualification,String year,String branch,String college_name,String percentage){

        this.id  = id;
        this.name = name;
        this.qualification = qualification;
        this.year = year;
        this.branch = branch;
        this.college_name = college_name;
        this.percentage = percentage;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
