package com.wiseass.postrainer.model.objects;

import java.util.ArrayList;

/**
 * Created by Ryan on 26/04/2016.
 */
public class MovementList {
    private ArrayList<Movement> movements;

    public MovementList(ArrayList<Movement> movements) {
        this.movements = movements;
    }

    public ArrayList<Movement> getMovements() {
        return movements;
    }

    public void setMovements(ArrayList<Movement> movements) {
        this.movements = movements;
    }
}
