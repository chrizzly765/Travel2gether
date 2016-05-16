package www.traveltogether.de.traveltogether.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class PackingObject extends AbstractListObject {

    private List<PackingItem> items = new ArrayList<PackingItem>();

    public PackingObject(String _title, int _id, String _description, Participant _author, int count) {
        super(_title,_id, _description, _author);
        for(int i = 0; i<count; i++){
            items.add(new PackingItem());
        }

    }
}

