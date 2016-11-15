package lu.uni.lassy.excalibur.examples.icrash.dev.controller;


import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtBiometric;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import org.apache.log4j.Logger;
import org.bioapi.BioAPIException;
import org.bioapi.data.BIR;
import org.bioapi.template.BIR_Implemented;
import org.bioapi.template.Date;

import java.util.UUID;

public class BiometricAuthController {
    private static final Logger log = Log4JUtils.getInstance().getLogger();
    private final BIR bioDataProvider;

    public BiometricAuthController() {
        // Bio Data Provider initialized for test cases
        byte[] testBioData = new byte[]{0x11, 0xf, 0x1f, 0xa, 0x33};
        this.bioDataProvider = new BIR_Implemented(testBioData, null, true, "Unknown",
                BIR.ProcessedLevel.RAW, UUID.fromString("test"), BIR.Purpose.IDENTIFY, new Date(0, 0, 0));
    }

    public DtBiometric getData() {
        try {
            return new DtBiometric(this.bioDataProvider.getBiometricData());
        } catch (BioAPIException ex) {
            log.error("Error during biometric data get : " + ex.toString());
            return new DtBiometric(new byte[]{});
        }
    }

    public void closeProvider() {
        try {
            this.bioDataProvider.destroy();
        } catch (BioAPIException ex) {
            log.error("Error during biometric provider close : " + ex.toString());
        }
    }
}
