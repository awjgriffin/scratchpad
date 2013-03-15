package selfstudy.financial;

public enum DailyBasis {

	ACT_360 (365, 360),
	ACT_365 (365, 365),
	_30_360 (30, 360),
	ACT_ACT (365, 365),
	BOND_MARKET_RATE (365, 365),
	MONEY_MARKET_RATE (365, 360);
	
	int days, denominator;

	private DailyBasis(int days, int denominator) {
		this.days = days;
		this.denominator = denominator;
	}	
	
	/**
	 * @param horizon number of days
	 * @return the factor for the given number of days
	 */
	public double getFactor(int horizon) {
		return (this.days == 0 ? horizon : this.days) / denominator;
	}

	/**
	 * @return the factor for a whole year.
	 */
	public double getFactor() {
		return days / denominator;
	}	
	
}
