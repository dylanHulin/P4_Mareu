package lamzone.service;


import java.util.ArrayList;
import java.util.List;

import lamzone.mareu.MyMeetingRecyclerViewAdapter;
import lamzone.model.Meeting;

public class DummyApiService implements ApiService {

    private List<Meeting> mMeetings = DummyApiServiceGenerator.generateMeetings();
    private List<Meeting> filterMeetings = new ArrayList<>();

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        mMeetings.add(meeting);

    }

    @Override
    public  List<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public List<Meeting> getFilterMeetings() {
        return filterMeetings;
    }

    @Override
    public List<Meeting> filterRoomMeetings(String room) {
        for(Meeting meeting : mMeetings) {
            if(meeting.getMeetingRoom().equals(room)) {
                filterMeetings.add(meeting);
            }
        }
        return filterMeetings;
    }

    @Override
    public List<Meeting> filterTimeMeetings(int hour, int minute) {
        for(Meeting meeting: mMeetings) {
            if(meeting.getHour() == hour && meeting.getMinute() == minute) {
                filterMeetings.add(meeting);
            }
        }

        return filterMeetings;
    }

    @Override
    public List<Meeting> filterDateMeetings(int day, int month, int year) {
        for(Meeting meeting: mMeetings) {
            if(meeting.getDay() == day && meeting.getMonth() == month && meeting.getYear() == year) {
                filterMeetings.add(meeting);
            }
        }

        return filterMeetings;
    }
    
}
