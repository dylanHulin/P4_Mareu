package lamzone.mareu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.List;

import lamzone.di.DI;
import lamzone.model.Meeting;
import lamzone.service.ApiService;
import lamzone.service.DummyApiServiceGenerator;


public class RoomDialog extends AppCompatDialogFragment {

    private AutoCompleteTextView dialog_room_AutoCompleteTextView;
    private List<Meeting> mFilterMeetings;
    private ApiService mApiService;
    private NoticeDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_room, null);

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
                        String room = dialog_room_AutoCompleteTextView.getText().toString();
                        mFilterMeetings = mApiService.filterRoomMeetings(room);
                        listener.onRoomDialogPositiveClick(mFilterMeetings);
                    }
                });

        dialog_room_AutoCompleteTextView = view.findViewById(R.id.dialog_room_AutoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, DummyApiServiceGenerator.DUMMY_ROOMS);
        dialog_room_AutoCompleteTextView.setAdapter(adapter);

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
        public void onRoomDialogPositiveClick(List<Meeting> meetingsFilter);
    }

}