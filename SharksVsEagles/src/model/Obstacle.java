package model;

public class Obstacle {

    private static String name = "O";
    private Location location;

    public Obstacle(Location location){
        this.location = location;
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public String getName(){
        return name;
    }
}
