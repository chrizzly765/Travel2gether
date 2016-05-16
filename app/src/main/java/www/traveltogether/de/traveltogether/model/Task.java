package www.traveltogether.de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Task extends AbstractListObject {
    public Task(String _title, int _id, String _description, Participant _author) {
        super(_title, _id, _description, _author);
    }
}
