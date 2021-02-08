package lamzone.mareu;

import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import lamzone.mareu.utils.DeleteViewAction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static lamzone.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MeetingListInstrumentedTest {

    private int ITEMS_COUNT = 5;
    private ListMeetingActivity mActivity;

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        onView(withId(R.id.meetingListRecyclerView))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem() {
        onView(withId(R.id.meetingListRecyclerView)).check(withItemCount(ITEMS_COUNT));

        onView(withId(R.id.meetingListRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        onView(withId(R.id.meetingListRecyclerView)).check(withItemCount(ITEMS_COUNT - 1));
    }


    /**
     * Run AddMeetingActivity
     */
    @Test
    public void addMeetingActivity_hasToRunWhenYouClickOnTheButton() {
        onView(withId(R.id.addMeetingButton)).perform(click());
        onView(withId(R.id.addMeetingLayout));
    }


    /**
     * Filter meeting room
     */
    @Test
    public void myMeetingList_filterRoom(){
        onView(withId(R.id.filterItem)).perform(click());
        onView(withText("Salles")).perform(click());
        onView(withId(R.id.dialog_room_AutoCompleteTextView)).perform(replaceText("Salle A"));
        onView(withText("OK")).perform(doubleClick());
        onView(withId(R.id.meetingListRecyclerView)).check(withItemCount(1));
    }

    /**
     * Filter meeting time
     */
    @Test
    public void myMeetingList_filterTime(){
        int hour = 10;
        int minutes = 00;
        onView(withId(R.id.filterItem)).perform(click());
        onView(withText("Heure")).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minutes));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.meetingListRecyclerView)).check(withItemCount(1));
    }

    /**
     * Display the list
     */
    @Test
    public void myMeetingList_displayMeetingsList(){
        onView(withId(R.id.filterItem)).perform(click());
        onView(withText("Afficher la liste")).perform(click());
        onView(withId(R.id.meetingListRecyclerView)).check(withItemCount(ITEMS_COUNT));
    }


}

