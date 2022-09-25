package com.example.transportapi.entity.enums;

public enum Shift {
    MORNING("08:00", "16:00"),
    AFTERNOON("10:00", "18:00");

    final String startTime;
    final String endTime;

    Shift(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
