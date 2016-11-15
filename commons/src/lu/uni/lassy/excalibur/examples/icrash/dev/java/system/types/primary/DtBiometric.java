package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

import java.util.Arrays;

public class DtBiometric implements JIntIs {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 229L;
    public byte[] value;
    /**
     * The maximum length a biometric vector can be.
     */
    private int _maxLength = 100;


    /**
     * Instantiates a new datatype biometric.
     *
     * @param bio type of byte array to create the datatype
     */
    public DtBiometric(byte[] bio) {
        this.value = bio;
    }

    public PtBoolean is() {
        return new PtBoolean(this.value.length <= _maxLength);
    }

    public PtBoolean eq(DtBiometric aDtBiometric) {
        return new PtBoolean(Arrays.equals(value, aDtBiometric.value));
    }
}
