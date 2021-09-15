package model;

public class GameSettings {

    private boolean includeObstacles;

    public GameSettings(){
    }

    /**@return True if obstacles are included on board, otherwise false */
    public boolean getIncludeObstacles() {
        return includeObstacles;
    }

    /**@param includeObstacles True if obstacles included on board, otherwise false */
    public void setIncludeObstacles(boolean includeObstacles) {
        this.includeObstacles = includeObstacles;
    }

}
