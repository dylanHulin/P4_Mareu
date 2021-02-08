package lamzone.mareu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lamzone.di.DI;
import lamzone.events.DeleteMeetingEvent;
import lamzone.model.Meeting;
import lamzone.service.ApiService;
import lamzone.service.DummyApiServiceGenerator;

public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.name_meeting)
    TextInputEditText nameInput;
    @BindView(R.id.time)
    TextInputEditText timeInput;
    @BindView(R.id.date)
    TextInputEditText dateInput;
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView roomInput;
    @BindView(R.id.subject)
    TextInputEditText subjectInput;
    @BindView(R.id.email_nacho_text_view)
    NachoTextView colleaguesInput;
    @BindView(R.id.create_meeting)
    Button addButton;

    private int hourInput;
    private int minuteInput;
    private int dayInput;
    private int monthInput;
    private int yearInput;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private ApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        mApiService = DI.getApiService();

        // Room = AutoCompleteText
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, DummyApiServiceGenerator.DUMMY_ROOMS);
        roomInput.setAdapter(adapter);

        colleaguesInput.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);

        timeInput.setOnClickListener(v -> {
            Calendar calendar= Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timeInput.setText(hourOfDay+":"+minute);
                    hourInput = hourOfDay;
                    minuteInput = minute;
                }
            }, hour, min, true);
            timePickerDialog.setTitle("Heure de la réunion");
            timePickerDialog.show();
        });

        dateInput.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            datePickerDialog = new DatePickerDialog(AddMeetingActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateInput.setText(dayOfMonth+"/"+month+"/"+year);
                    dayInput = dayOfMonth;
                    monthInput = month;
                    yearInput = year;
                }
            }, day, month, year);
            datePickerDialog.setTitle("Date de la réunion");
            datePickerDialog.show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.create_meeting)
    public void addMeeting() {
        if (nameInput.getText().toString().length() == 0 || roomInput.toString().length() == 0 ||
                timeInput.getText().toString().length() == 0 || subjectInput.getText().toString().length() == 0 ||
                colleaguesInput.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Tous les champs sont requis.", Toast.LENGTH_SHORT).show();

        } else {
            Meeting meeting = new Meeting(
                    mApiService.getMeetings().size() + 1,
                    nameInput.getText().toString(),
                    hourInput,
                    minuteInput,
                    dayInput,
                    monthInput,
                    yearInput,
                    roomInput.getText().toString(),
                    subjectInput.getText().toString(),
                    chipsToString());

            mApiService.createMeeting(meeting);

           Intent intent = new Intent(getApplicationContext(), ListMeetingActivity.class);
           startActivity(intent);
           finish();
        }
    }


    public String chipsToString() {
        String text = "";
        for (Chip chip : colleaguesInput.getAllChips()) {
        text = ((text.equals("")) ? text + chip.getText().toString() : text + ", " + chip.getText().toString());
        }
        return text;
        }

}
