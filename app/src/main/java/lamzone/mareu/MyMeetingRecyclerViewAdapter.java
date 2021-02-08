package lamzone.mareu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lamzone.events.DeleteMeetingEvent;
import lamzone.model.Meeting;
import lamzone.service.DummyApiServiceGenerator;

import org.greenrobot.eventbus.EventBus;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder>  {

    private List<Meeting> mMeetings;
    private Context context;

    public MyMeetingRecyclerViewAdapter(Context context, List<Meeting> mMeetings) {
        this.context = context;
        this.mMeetings = mMeetings;
    }
    //C
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }
    // Getting information from AddMeetingActivity to Recycler
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);

        String hour = ((meeting.getHour() < 10 ) ? "0" + meeting.getHour() : String.valueOf(meeting.getHour()));
        String minute = ((meeting.getMinute() < 10 ) ? "0" + meeting.getMinute() : String.valueOf(meeting.getMinute()));

        String time = hour + "h" + minute;

        String detail = meeting.getMeetingName() + " - " + time + " - " + meeting.getMeetingRoom();
        holder. mMeetingDetails.setText(detail);

        holder.mColleaguesMail.setText(meeting.getColleagues());

        int positionPlace =  DummyApiServiceGenerator.DUMMY_ROOMS.indexOf(meeting.getMeetingRoom());
        GradientDrawable myGrad = (GradientDrawable)holder.mMeetingImage.getDrawable();
        myGrad.setColor(Color.parseColor(DummyApiServiceGenerator.DUMMY_COLORS.get(positionPlace)));

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new DeleteMeetingEvent(meeting));

                }
            });

    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public void setListMeetings(List<Meeting> listMeetings) {
        this.mMeetings = listMeetings;
        notifyDataSetChanged();
    }
    // Binding views to code
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_circle)
        public ImageView mMeetingImage;
        @BindView(R.id.details_item)
        public TextView mMeetingDetails;
        @BindView(R.id.colleagues_emailTextView)
        public TextView mColleaguesMail;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

