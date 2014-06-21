package net.wolfpac.cpportal.v2.db;

public enum ResetType {
    RRN_RESET(0), FREQ_TYPE_RESET(1), SUBS_COUNT_RESET(2), DISPATCHED_RESET(3), BROADCAST_RESET(4), MSG_RESET(5), UNKNOWN(-1);

    private int value;
    
    private ResetType(int value) {
	this.value = value;
    }

    public int getIntValue() {
	return this.value;
    }

    public static ResetType getType(int i) {
        for (ResetType t : ResetType.values()) {
            if (t.getIntValue() == i) {
		return t;
            }
        }
        return UNKNOWN;
    }

}
