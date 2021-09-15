package model.interfaces;

import model.BoardPiece;
import model.Location;
import model.enums.TileType;

import java.io.Serializable;

public interface BoardTile extends Serializable, Cloneable {

    public abstract Location getLocation();
    public abstract void setLocation(Location location);
    public abstract TileType getTileType();
    public abstract void setTileType(TileType tileType);
    public abstract boolean getIsOccupied();
    public abstract void setIsOccupied(boolean isOccupied);
    public abstract BoardPiece getOccupiedBy();
    public abstract void setOccupiedBy(BoardPiece occupiedBy);

    /**@return True if the tile is occupied by an obstacle, otherwise false*/
    public abstract boolean getIsOccupiedByObstacle();

    /**@param isOccupiedByObstacle True if the tile is occupied by an obstacle, otherwise false*/
    public abstract void setIsOccupiedByObstacle(boolean isOccupiedByObstacle);

    /**Convert tile type (ie. red or blue) */
    public abstract void convertTileType();

    public abstract BoardTile clone() throws CloneNotSupportedException;
}
