package lamzone.events;

import lamzone.model.Meeting;

public class DeleteMeetingEvent {

    //public int action;

        /**
         * Neighbour to delete
         */
        public Meeting meeting;

        /**
         * Constructor.
         * @param meeting
         */

        public DeleteMeetingEvent(Meeting meeting) {

            this.meeting = meeting;
            //this.action = action;
        }
    }

