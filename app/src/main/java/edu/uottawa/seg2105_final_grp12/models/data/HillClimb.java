package edu.uottawa.seg2105_final_grp12.models.data;

public class HillClimb extends Event{

    private int inclineAngle;

    public HillClimb(String id){
        super(id);
    }

    public void setInclineAngle(int inclineAngle) {this.inclineAngle = inclineAngle;}
    public int getInclineAngle() {return inclineAngle;}
}
