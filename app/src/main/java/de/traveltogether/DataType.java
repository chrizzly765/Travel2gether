package de.traveltogether;

/**
 * Enum for the data type of a http request
 */
public enum DataType {
    TRIP{
        @Override
        public String toString(){
            return "Trip";
        }
    },
    TASK{
        @Override
        public String toString(){
            return "Task";
        }
    },
    EXPENSE
    {
        @Override
        public String toString(){
        return "Expense";
    }
    },
    PACKINGOBJECT {
        @Override
        public String toString() {
            return "PackingObject";
        }
    },
    ACTIVITY {
        @Override
        public String toString(){
            return "Activity";
        }
    },
    PARTICIPANT {
        @Override
        public String toString() {
            return "Participant";
        }
    },
    REGISTRATION {
        @Override
        public String toString() {
            return "Registration";
        }
    },
    LOGIN {
        @Override
        public String toString() {
            return "Login";
        }
    },
    COMMENT {
        @Override
        public String toString() {
            return "Comment";
        }
    },
    CHAT {
        @Override
        public String toString() {
            return "Chat";
        }
    },
    INVITATION{
        @Override
        public String toString() {
            return "Invitation";
        }
    },
    PERSON{
        @Override
        public String toString(){
            return "Person";
        }
    },
    NOTIFICATION{
        @Override
        public String toString(){return "Notification";}
    },
    STATISTIC{
        @Override
        public String toString(){return "Statistic";}
    },
    PACKINGITEM{
        @Override
        public String toString(){return "PackingItem";}
    }
}
