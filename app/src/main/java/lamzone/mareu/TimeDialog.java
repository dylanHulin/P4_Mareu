package lamzone.mareu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.List;

import lamzone.di.DI;
import lamzone.model.Meeting;
import lamzone.service.ApiService;

public class TimeDialog extends AppCompatDialogFragment {

    private List<Meeting> mFilterMeetings;
    private ApiService mApiService;
    private NoticeDialogListener listener;
    private TimePicker picker;
    private int hour;
    private int minute;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_time, null);

        mApiService = DI.getApiService();


        builder.setView(view)
                .setTitle("Salle :")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hour = picker.getCurrentHour();
                        minute = picker.getCurrentMinute();
                        mFilterMeetings = mApiService.filterTimeMeetings(hour, minute);
                        listener.onTimeDialogPositiveClick(mFilterMeetings);
                    }
                });

        picker = view.findViewById(R.id.dialog_timePicker);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface NoticeDialogListener {
        void onTimeDialogPositiveClick(List<Meeting> meetingsFilter);
    }

}

