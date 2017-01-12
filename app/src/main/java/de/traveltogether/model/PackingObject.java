package de.traveltogether.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for PackingObject
 */
public class PackingObject extends AbstractListObject {

    private int number;
    private int itemsPacked;
    private int[] personsAssigned;
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

    public void addPackingItem(PackingItem _packingItem){
        items.add(_packingItem);
    }
}

