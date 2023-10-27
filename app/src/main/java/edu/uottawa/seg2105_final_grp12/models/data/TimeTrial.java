package edu.uottawa.seg2105_final_grp12.models.data;

public class TimeTrial extends Event {

    private int numberOfIntevals;

    public TimeTrial() {
        // Without defining what requirements an admin MUST specify, its a bit hard to define the constructor
    }

    public void setNumberOfIntevals(int intervalCount) {
        numberOfIntevals = intervalCount;
    }

    public int getNumberOfIntevals() {
        return numberOfIntevals;
    }

}
