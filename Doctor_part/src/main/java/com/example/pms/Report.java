package com.example.pms;

import java.sql.Date;


public class Report {
    public   int report_id;
    private int pat_id;
    private java.sql.Date report_date;
    private String description;
    private String report_path;

    public Report(int pat_id, java.sql.Date report_date, String description, String report_path) {
        report_id=0;
        this.pat_id = pat_id;
        this.report_date = java.sql.Date.valueOf(String.valueOf(report_date));
        this.description = description;
        this.report_path = report_path;
    }

    public Report(int userId, int id, Date d, String des, String path) {
        report_id =  userId;
        pat_id = id;
        report_date = d;
        description = des;
        report_path= path;

    }



    public int getpatID() {
        return pat_id;
    }

    public Date getReport_date() {
        return report_date;
    }

    public String getDescription() {
        return description;
    }

    public String getReport_path() {
        return report_path;
    }
}
