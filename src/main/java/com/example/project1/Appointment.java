package com.example.project1;

class Appointment {
    private String patientName;
    private String date;
    private String time;

    // Constructor
    public Appointment(String patientName, String date, String time) {
        this.patientName = patientName;
        this.date = date;
        this.time = time;
    }

    // Getters and Setters
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

