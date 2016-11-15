package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

import java.io.Serializable;

/**
 * Data type for coordinator timing statistic
 */
public class DtCoordStatistic implements Serializable, JIntIs {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 227L;

    /**
     * The datatype coordId.
     */
    public DtCoordinatorID coordId;

    /**
     * The datatype coordState.
     */
    public PtString coordState;

    /**
     * The datatype coordTime.
     */
    public DtTime coordTime;


    /**
     * Instantiates a new datatype Coord Statistic.
     *
     * @param aCoordId   The datatype of DtCoordinatorId to assign to the Coordinator Statistic
     * @param coordState The datatype of PtString to assign to the Coordinator Statistic
     * @param coordTime  The datatype of DtMinute to assign to the Coordinator Statistic
     */
    public DtCoordStatistic(DtCoordinatorID aCoordId, PtString coordState, DtTime coordTime) {
        this.coordId = aCoordId;
        this.coordState = coordState;
        this.coordTime = coordTime;
    }

    @Override
    public PtBoolean is() {
        return new PtBoolean(coordId.is().getValue() &&
                (coordTime.hour.value.getValue() >= 0 ||
                        coordTime.minute.value.getValue() >= 0 ||
                        coordTime.second.value.getValue() >= 0));
    }
}
