package lamzone.model;

import java.util.Objects;
;

public class Meeting {

    private int id;
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;

    private String meetingName;
    private String meetingRoom;
    private String subject;
    private String colleagues;

    public Meeting(int id, String meetingName, int hour, int minute, int day, int month, int year, String meetingRoom, String subject, String colleagues) {
        this.id = id;
        this.meetingName = meetingName;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.meetingRoom = meetingRoom;
        this.subject = subject;
        this.colleagues = colleagues;
    }


    public long getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getMeetingName() { return meetingName; }

    public void setMeetingName(String meetingName) { this.meetingName = meetingName; }

    public int getHour() { return hour; }

    public void setHour(int hour) { this.hour = hour; }

    public int getMinute() { return minute;}

    public void setMinute(int minute) { this.minute = minute; }

    public String getMeetingRoom() { return meetingRoom; }

    public void setMeetingRoom(String meetingRoom) { this.meetingRoom = meetingRoom; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getColleagues() { return colleagues; }

    public void setColleagues(String colleagues) { this.colleagues = colleagues; }

    public int getDay() { return day; }

    public void setDay(int day) { this.day = day; }

    public int getMonth() { return month; }

    public void setMonth(int month) { this.month = month; }

    public int getYear() { return year; }

    public void setYear(int year) { this.year = year; }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Meeting meeting = (Meeting) o;
//        return Objects.equals(id, meeting.id);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }

}
