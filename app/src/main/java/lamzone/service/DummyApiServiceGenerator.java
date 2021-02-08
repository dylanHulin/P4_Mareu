package lamzone.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lamzone.model.Meeting;

public class DummyApiServiceGenerator {

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(0, "Réunion 1", 10, 00, 8,2,2021,"Salle A", "Java", "laurine@lamzone.com, paul@lamzone.com, dylan@lamzone.com"),
            new Meeting(1, "Réunion 2", 11, 30, 2,5,2021,"Salle B", "Kotlin", "megane@lamzone.com, suleyman@lamzone.com, laurent@lamzone.com"),
            new Meeting(2, "Réunion 3", 8, 15, 18,11,2021,"Salle C", "C++", "arthur@lamzone.com, luc@lamzone.com, jean@lamzone.com"),
            new Meeting(3, "Réunion 4", 17, 00, 24,12,2021,"Salle D", "Lua", "catherine@lamzone.com, sarah@lamzone.com, gilbert@lamzone.com"),
            new Meeting(4, "Réunion 5", 18, 45, 15,8,2021,"Salle E", "Python", "theo@lamzone.com, julien@lamzone.com, guy@lamzone.com")

    );

    static List<Meeting> generateMeetings() { return new ArrayList<>(DUMMY_MEETING); }

    public static List<String> DUMMY_ROOMS = Arrays.asList(
            "Salle A",
            "Salle B",
            "Salle C",
            "Salle D",
            "Salle E",
            "Salle F",
            "Salle G",
            "Salle H",
            "Salle I",
            "Salle J");

    public static List<String> DUMMY_COLORS = Arrays.asList(
            "#a83232",
            "#a87132",
            "#b1b52d",
            "#75b52b",
            "#33b02a",
            "#2ab088",
            "#2ab088",
            "#2ab088",
            "#762ab0",
            "#762ab0");
}
