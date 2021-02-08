package lamzone.service;

import java.util.List;

import lamzone.model.Meeting;

public interface ApiService {

    List<Meeting> getMeetings();

    List<Meeting> getFilterMeetings();

    List<Meeting> filterRoomMeetings(String room);

    List<Meeting> filterTimeMeetings(int hour, int minute);

    List<Meeting> filterDateMeetings(int day, int month, int year);

    void deleteMeeting(Meeting meeting);

    void createMeeting(Meeting meeting);

}
