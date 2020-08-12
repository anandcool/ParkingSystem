package com.example.eschoolapp.model;

public class Course {
    int Course_Id,Total_Fees;
    String Name,Duration,Description;

    public Course(int total_Fees, String name, String duration, String description) {
        Total_Fees = total_Fees;
        Name = name;
        Duration = duration;
        Description = description;
    }



    public int getCourse_Id() {
        return Course_Id;
    }

    public void setCourse_Id(int course_Id) {
        Course_Id = course_Id;
    }

    public int getTotal_Fees() {
        return Total_Fees;
    }

    public void setTotal_Fees(int total_Fees) {
        Total_Fees = total_Fees;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
