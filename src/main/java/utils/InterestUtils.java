package utils;

import selfstudy.ds.LinkedList;
import selfstudy.financial.DailyBasis;
import selfstudy.financial.PaymentFrequency;
import static selfstudy.fp.functions.NumericFunctions.*;

public class InterestUtils {

	public static double discreteCompoundInterest(Double rate, int yrs, PaymentFrequency paymentBasis) {
		return Math.pow((1 + (rate / paymentBasis.getNumPaymentsPerYear()) ), (yrs * paymentBasis.getNumPaymentsPerYear())) - 1;
	}
	
	public static double calculateContinuousCompoundInterest(Double principal, Double rate, int years, PaymentFrequency paymentBasis) {

		if(years == 1) {
			return principal * getContinuousCompoundInterestRate(rate, paymentBasis);
		} else {
			return  calculateContinuousCompoundInterest(principal, rate, (years - 1), paymentBasis) * getContinuousCompoundInterestRate(rate, paymentBasis);
		}
	}	

	/**
	 * Done in terms of REDUCE
	 * Create a list of size <code>years</code> and fill it with the same value, the rate. 
	 * 
	 * @param principal
	 * @param rate
	 * @param years
	 * @param paymentBasis
	 * @return
	 */
	public static double calculateContinuousCompoundInterestViaReduce(Double principal, Double rate, int years, PaymentFrequency paymentBasis) {
		
		double continuousCompoundInterestRate = getContinuousCompoundInterestRate(rate, paymentBasis);
		LinkedList<Double> linkedList = new LinkedList<Double>();
		for(int i = 0; i < years; i++){
			linkedList = linkedList.add(continuousCompoundInterestRate);
		}
		
		return FunctionalUtils.<Double, Double>reduce(multiplyDoubles(), principal, linkedList);
	}
	
	public static double calculateContinuousCompoundInterestV3(Double principal, Double rate, int years, PaymentFrequency paymentBasis) {
		return principal * ( Math.pow( Math.E, (rate * years) ) );
	}		
	
	public static double getContinuousCompoundInterestRate(Double rate, PaymentFrequency paymentBasis) {
		return Math.pow( Math.E, (rate * paymentBasis.getNumPaymentsPerYear()) );
	}	
	
	/**
	 * @param principal
	 * @param rate
	 * @param years
	 * @return
	 */
	public static double simpleInterest(Double principal, Double rate, int years) {
		return principal * rate * years;
	}	

	/**
	 * @param principal
	 * @param rate
	 * @return the Interest on the principal for one year.
	 */
	public static double simpleInterest(Double principal, Double rate) {
		return simpleInterest(principal, rate, 1);
	}		

	public static double simpleInterest(Double principal, Double rate, int days, DailyBasis basis) {
		return principal * (rate * basis.getFactor(days));
	}

	
	public static double getStatedRateFromCompound(Double compoundRate, PaymentFrequency paymentFrequency, int yrs) {
		return paymentFrequency.getNumPaymentsPerYear() * (Math.pow ((1 + compoundRate), (1 / (paymentFrequency.getNumPaymentsPerYear() * yrs))) -1);
	}
	
}
