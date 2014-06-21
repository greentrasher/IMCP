package net.wolfpac.cpportal.v2.db;

public enum FreqType {
    DAILY(0), WEEKLY(1), MONTHLY(2), UNKNOWN(-1);

    private int value;
    
    private FreqType(int value) {
	this.value = value;
    }

    public int getIntValue() {
	return this.value;
    }

    public static FreqType getType(int i) {
        for (FreqType t : FreqType.values()) {
            if (t.getIntValue() == i) {
		return t;
            }
        }
        return UNKNOWN;
    }

}
