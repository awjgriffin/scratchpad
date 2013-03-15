package selfstudy.financial;

public enum PaymentFrequency {

	ANNUALLY(1),
	SEMI_ANNUALLY(2),
	QUARTERLY(4),
	MONTHLY(12),
	WEEKLY(52),
	DAILY(365);
	
	int numPaymentsPerYear;
	
	private PaymentFrequency(int numPaymentsPerYear) {
		this.numPaymentsPerYear = numPaymentsPerYear;
	}

	public int getNumPaymentsPerYear() {
		return numPaymentsPerYear;
	}
	
}
