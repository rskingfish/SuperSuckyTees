package Models;


/**
 * Limited use object used to display rates
 * @author Scott King
 * @version 12-2-18
 */

public class Rate {

    private final String gate;
    private final String standard;
    private final String rush;

    public Rate(String _gate, String _standard, String _rush) {
        this.gate = _gate;
        this.standard = _standard;
        this.rush = _rush;
    }


    public String getGate() {
        return gate;
    }


    public String getStandard() {
        return standard;
    }


    public String getRush() {
        return rush;
    }


    @Override
    public String toString() {
        return "{Ship Gates:=" + gate
                + ", Standard:=" + standard
                + ", Rush:=" + rush + "}";
    }
}
