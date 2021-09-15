package model;

import java.io.Serializable;

public class Location implements Serializable, Cloneable {

    private int xCoordinate;
    private int yCoordinate;

    /**
     *
     * @param xCoordinate
     * @param yCoordinate
     * @pre.condition xCoordinate and yCoordinate is non-negative
     */
    public Location (int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getXCoordinate(){
        return xCoordinate;
    }
    public int getYCoordinate(){
        return yCoordinate;
    }

    @Override
    public Location clone() throws CloneNotSupportedException {
        Location copy = new Location(xCoordinate, yCoordinate);
        return copy;
    }
}
