package lamzone.mareu;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static lamzone.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AddMeetingTest {

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
     * Adding a new Meeting
     */
    @Test
    public void myMeetingList_addNewMeeting(){
        onView(withId(R.id.addMeetingButton)).perform(click());
        onView(withId(R.id.name_meeting)).perform(replaceText("Réunion Test"));
        onView(withId(R.id.autoCompleteTextView)).perform(replaceText("Salle A"));

        onView(withId(R.id.time)).perform(doubleClick());
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.subject)).perform(replaceText("Applications"));
        onView(withId(R.id.email_nacho_text_view)).perform(replaceText("alexandre@lamzone.com\n marie@lamzone.com\n"));

        onView(withId(R.id.create_meeting)).perform(click());

        onView(withId(R.id.meetingListRecyclerView)).check(withItemCount(ITEMS_COUNT + 1));
    }


}