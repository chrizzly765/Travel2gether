package de.traveltogether.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class PackingObject extends AbstractListObject {

    //protected String category = "";
    protected int number;
    protected int itemsPacked;
    protected int[] personsAssigned;
    private List<PackingItem> items = new ArrayList<PackingItem>();

    public int getPackingItemsNumber(){
        return number;
    }
    public List<PackingItem> getItems(){return items;}

    public int getItemsPacked(){
        return itemsPacked;
    }

    public int[] getPersonsAssigned(){return personsAssigned;}
    public PackingObject(String _title, long _id, long _tripId, String _description, int _author, int count) {
        super(_title,_id, _tripId, _description, _author);
        number=count;
    }

    //public void addPackingItem(int personAssigned){items.add(new PackingItem(personAssigned));    }
    //public void addPackingItem(int personAssigned, boolean status, long id, int number){ items.add(new PackingItem(personAssigned, status, id, number));}
    public void addPackingItem(PackingItem _packingItem){
        //number++;
        items.add(_packingItem);
    }
}

