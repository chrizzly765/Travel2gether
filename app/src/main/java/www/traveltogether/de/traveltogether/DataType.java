package www.traveltogether.de.traveltogether;

/**
 * Created by Anna-Lena on 16.05.2016.
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
    }, PACKING {
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
    }
}
