package selfstudy.financial;

import utils.InterestUtils;

public class Rate {

	double apr;
	DailyBasis dailyBasis;
	
	public Rate(double apr, DailyBasis dailyBasis) {
		super();
		this.apr = apr;
		this.dailyBasis = dailyBasis;
	}

	public Rate(double apr) {
		this(apr, DailyBasis.BOND_MARKET_RATE);
	}
	
	/**
	 * For one year.
	 * @param principal
	 * @return
	 */
	public double simpleInterest(double principal) {
		return InterestUtils.simpleInterest(principal, apr);
	}	
	
	public double getApr() {
		return apr;
	}
	
	public DailyBasis getDailyBasis() {
		return dailyBasis;
	}

	/**
	 * Effective annual rate - compounded for one year
	 * @return
	 */
	public double getEAR(PaymentFrequency paymentBasis) {
		return getEAR(paymentBasis, 1);
	}
	
	public double getEAR(PaymentFrequency paymentBasis, int yrs) {
		return Math.pow((1 + (apr/(yrs * paymentBasis.numPaymentsPerYear))), (yrs * paymentBasis.numPaymentsPerYear)) - 1;
	}
	
	public double simpleInterest(double principal, int yrs) {
		return InterestUtils.simpleInterest(principal, apr, yrs);
	}

	public double simpleInterest(double principal, int days, DailyBasis dailyBasis) {
		return InterestUtils.simpleInterest(principal, apr, days, dailyBasis);
	}	
	
	public double compoundInterest(double principal, int yrs, PaymentFrequency paymentBasis) {
		return InterestUtils.discreteCompoundInterest(apr, yrs, paymentBasis);
	}

	public double convertBondToMarket() {
		
		if(this.dailyBasis.equals(DailyBasis.MONEY_MARKET_RATE) || !(this.dailyBasis.equals(DailyBasis.BOND_MARKET_RATE))) {
			return apr;
		} else {
			return apr * (360/365);
		}

	}
	
	public double convertMarketToBond() {

		if(this.dailyBasis.equals(DailyBasis.BOND_MARKET_RATE) || !(this.dailyBasis.equals(DailyBasis.MONEY_MARKET_RATE))) {
			return apr;
		} else {
			return apr * (365/360);
		}		
	}
	
}
