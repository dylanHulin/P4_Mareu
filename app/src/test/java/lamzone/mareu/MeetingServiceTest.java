package lamzone.mareu;

import lamzone.di.DI;
import lamzone.model.Meeting;
import lamzone.service.ApiService;
import lamzone.service.DummyApiServiceGenerator;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private ApiService service;


    @Before
    public void setup() { service = DI.getNewInstanceApiService(); }

    /**
     * Get the meeting list
     */
    @Test
    public void getMeetingListWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        assertFalse(meetings.isEmpty());
    }


    /**
     * Filter with room
     */
    @Test
    public void filterRoomMeetingListWithSuccess() {
        String room = DummyApiServiceGenerator.DUMMY_ROOMS.get(0);

        List<Meeting> meetings = service.getMeetings().stream()
                .filter( e -> e.getMeetingRoom().contains(room))
                .collect(Collectors.toList());

        List<Meeting> expectedMeetings = service.filterRoomMeetings(room);

        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }


    /**
     * Filter with time
     */
    @Test
    public void filterTimeMeetingListWithSuccess() {
        int hour = 10;
        int minute = 00;

        List<Meeting> meetings = service.getMeetings().stream()
                .filter( e -> e.getHour() == hour)
                .filter( e -> e.getMinute() == minute)
                .collect(Collectors.toList());

        List<Meeting> expectedMeetings = service.filterTimeMeetings(hour, minute);

        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    /**
     * Add a new meeting
     */
    @Test
    public void addMeetingListWithSuccess() {
        int id = service.getMeetings().size() + 1;
        Meeting meetingToAdd = new Meeting(id, "Réunion 1", 18, 00, "Salle A", "add meeting", "alexandre@lamzone.com, francis@lamzone.com, helena@lamzone.com");
        service.createMeeting(meetingToAdd);
        assertTrue(service.getMeetings().contains(meetingToAdd));

    }


    /**
     * Delete a meeting
     */
    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

}