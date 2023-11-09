package edu.uottawa.seg2105_final_grp12.models.data;

// Could use this to make an EventFactory in the future
public enum EventStyle {
    // Event styles
    HILL_CLIMB("Hill Climb"),
    TIME_TRIAL("Time Trial"),
    GROUP_RIDE("Group Ride"),
    ROAD_STAGE_RACE("Road Stage Race"),
    ROAD_RACE("Road Race");

    // methods to convert from event style to displayable string
    private final String style;
    private EventStyle(String styleName) {
        style = styleName;
    }

    public String toString() {
        return style;
    }

}