package model;

import model.enums.TileType;
import model.interfaces.BoardTile;

public class BoardTileImpl implements BoardTile {

    private Location location;
    private TileType tileType;
    private boolean isOccupied;
    private BoardPiece occupiedBy;
    private boolean isOccupiedByObstacle;

    public BoardTileImpl(TileType tileType, Location location){
        this.tileType = tileType;
        this.location = location;
    }

    /**
     *
     * @return tileType
     */
    @Override
    public TileType getTileType() {
        return tileType;
    }

    /**
     *
     * @param tileType
     */
    @Override
    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    @Override
    public boolean getIsOccupied() {
        return isOccupied;
    }

    @Override
    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    @Override
    public BoardPiece getOccupiedBy() {
        return occupiedBy;
    }

    @Override
    public void setOccupiedBy(BoardPiece occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    @Override
    public boolean getIsOccupiedByObstacle(){
        return isOccupiedByObstacle;
    }

    @Override
    public void setIsOccupiedByObstacle(boolean isOccupiedByObstacle){
        this.isOccupiedByObstacle = isOccupiedByObstacle;
    }

    /**
     *
     * @return Location
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void convertTileType() {
        if(tileType == TileType.LAND){
            setTileType(TileType.WATER);
        }
        else{
           setTileType(TileType.LAND);
        }
    }

    @Override
    public BoardTile clone() throws CloneNotSupportedException {
        BoardTile cloned = new BoardTileImpl(tileType, location.clone());
        cloned.setIsOccupied(isOccupied);
        cloned.setIsOccupiedByObstacle(isOccupiedByObstacle);
        return cloned;
    }
}
