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

public class DateDialog extends AppCompatDialogFragment {

    private List<Meeting> mFilterMeetings;
    private ApiService mApiService;
    private NoticeDialogListener listener;
    private DatePicker picker;
    private int day;
    private int month;
    private int year;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_date, null);

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
                        day = picker.getDayOfMonth();
                        month = picker.getMonth();
                        year =picker.getYear();
                        mFilterMeetings = mApiService.filterDateMeetings(day, month, year);
                        listener.onDateDialogPositiveClick(mFilterMeetings);
                    }
                });

        picker = view.findViewById(R.id.dialog_datePicker);

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
       public void onDateDialogPositiveClick(List<Meeting> meetingsFilter);
    }

}

