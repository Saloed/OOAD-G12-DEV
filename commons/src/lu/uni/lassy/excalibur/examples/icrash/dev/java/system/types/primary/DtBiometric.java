package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import org.bioapi.data.BIR;

public class DtBiometric implements JIntIs {

    public BIR.BiometricType value;
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 229L;
    /**
     * The maximum length a biometric vector can be.
     */
    private int _maxLength = 100;


    /**
     * Instantiates a new datatype biometric.
     *
     * @param bio  type of BiometricType to create the datatype
     */
    public DtBiometric(BIR.BiometricType bio) {
    this.value = bio;
    }
    public PtBoolean is() {
        return new PtBoolean(this.value.containsFacialFeatures());
    }

}
