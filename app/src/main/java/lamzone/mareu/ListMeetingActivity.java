package lamzone.mareu;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lamzone.di.DI;
import lamzone.events.DeleteMeetingEvent;
import lamzone.model.Meeting;
import lamzone.service.ApiService;
import lamzone.service.DummyApiService;


public class ListMeetingActivity extends AppCompatActivity implements RoomDialog.NoticeDialogListener, DateDialog.NoticeDialogListener {

    @BindView(R.id.meetingListRecyclerView)
    RecyclerView rv;
    private ApiService apiService;
    private MyMeetingRecyclerViewAdapter mAdapter;
    private List<Meeting> mMeetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        apiService = DI.getApiService();

        mMeetings = apiService.getMeetings();
        //EventBus.getDefault().register(this);

        mAdapter = new MyMeetingRecyclerViewAdapter(getApplicationContext(), mMeetings);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);


        FloatingActionButton fab = findViewById(R.id.addMeetingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddMeetingActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.roomFilterSubItem:
                apiService.getFilterMeetings().clear();
                openRoomDialog();
                return true;
            case R.id.dateFilterSubItem:
                apiService.getFilterMeetings().clear();
                openDateDialog();
                return true;
            case R.id.listSubItem:
                apiService.getFilterMeetings().clear();
                initList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openRoomDialog() {
        RoomDialog roomDialog = new RoomDialog();
        roomDialog.show(getSupportFragmentManager(), "room dialog");
    }

    @Override
    public void onRoomDialogPositiveClick(List<Meeting> meetingsFilter) {
        mAdapter.setListMeetings(meetingsFilter);
    }

    private void openDateDialog() {
        DateDialog dateDialog = new DateDialog();
        dateDialog.show(getSupportFragmentManager(), "date dialog");
    }

    @Override
    public void onDateDialogPositiveClick(List<Meeting> meetingsFilter) {
        mAdapter.setListMeetings(meetingsFilter);
    }

    private void initList() {
        mMeetings = apiService.getMeetings();
        mAdapter.setListMeetings(mMeetings);
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteMeetingEvent event) {
            apiService.deleteMeeting(event.meeting);
        initList();
    }
}

