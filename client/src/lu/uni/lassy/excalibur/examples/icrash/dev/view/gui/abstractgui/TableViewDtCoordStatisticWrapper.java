package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordStatistic;

public class TableViewDtCoordStatisticWrapper {

    private String coordId;
    private String state;
    private String time;

    public TableViewDtCoordStatisticWrapper(String coordId, String state, String time) {
        this.coordId = coordId;
        this.state = state;
        this.time = time;
    }

    public TableViewDtCoordStatisticWrapper(DtCoordStatistic statistic) {
        this.coordId = statistic.coordId.toString();
        this.state = statistic.coordState.getValue();
        this.time = statistic.coordTime.toString();
    }

    public String getCoordId() {
        return coordId;
    }

    public void setCoordId(String coordId) {
        this.coordId = coordId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
