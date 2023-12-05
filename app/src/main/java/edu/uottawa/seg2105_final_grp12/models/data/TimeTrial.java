package edu.uottawa.seg2105_final_grp12.models.data;

public class TimeTrial extends Event {

    private int numberOfIntervals;

    public TimeTrial(String id) {
        super(id);
    }

    public void setNumberOfIntervals(int intervalCount) {
        numberOfIntervals = intervalCount;
    }

    public int getNumberOfIntervals() {
        return numberOfIntervals;
    }

}
