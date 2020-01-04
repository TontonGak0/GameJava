package model;

public class Troop {
    int costProduction,timeProduction,speed,lifePoint,damages;

    public String info() {
        return "lifePoint=" + lifePoint;
    }

    
    public void troopvstroop (Troop troopattack) {
    	this.lifePoint = this.lifePoint - troopattack.damages;
    	troopattack.lifePoint = troopattack.lifePoint - this.damages;
    }
}