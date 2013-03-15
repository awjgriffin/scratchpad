package utils;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

public enum EvictionPeriod {
	
	ONE_SEC(1, SECONDS), FIVE_SEC(5, SECONDS), TEN_SEC(10, SECONDS), THIRTY_SEC(30, SECONDS), ONE_MIN(1, MINUTES), FIVE_MIN(5, MINUTES), TEN_MIN(10, MINUTES), THIRTY_MIN(30, MINUTES), ONE_HOUR(1, HOURS);
	
	final long amt;
	final TimeUnit unit;
	
	private EvictionPeriod(long amt, TimeUnit unit) {
		this.amt = amt;
		this.unit = unit;
	}

	long getAmt() {
		return amt;
	}

	TimeUnit getUnit() {
		return unit;
	}
	
}