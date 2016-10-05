package de.traveltogether.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class PackingObject extends AbstractListObject {

    protected String category;
    protected int number;
    protected int itemsPacked;
    protected int[] personsAssigned;


    private List<PackingItem> items = new ArrayList<PackingItem>();

    public int getPackingItemsNumber(){
        return number;
    }

    public int getItemsPacked(){
        return itemsPacked;
    }

    public PackingObject(String _title, int _id, String _description, int _author, int count) {
        super(_title,_id, _description, _author);
        for(int i = 0; i<count; i++){
            items.add(new PackingItem());
        }

    }

    public void addPackingItem(){
        items.add(new PackingItem());
    }
}

