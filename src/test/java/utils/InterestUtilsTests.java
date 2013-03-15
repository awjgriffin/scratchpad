package utils;

import org.junit.Test;

import selfstudy.financial.PaymentFrequency;
import selfstudy.financial.Rate;
import static utils.InterestUtils.*;


public class InterestUtilsTests {

	@Test
	public void testSimpleInterest() {
		System.out.println(simpleInterest(10000d, .1));	
		System.out.println(simpleInterest(10000d, .1, 2));
		System.out.println(simpleInterest(10000d, .1, 3));
	}

	
	@Test
	public void testDiscreteCompoundInterest() {
		System.out.println(discreteCompoundInterest(.05d, 3, PaymentFrequency.SEMI_ANNUALLY));
		System.out.println(discreteCompoundInterest(.05d, 10, PaymentFrequency.SEMI_ANNUALLY));	
	}	
	
	@Test
	public void testContinuousCompoundInterest() {
		System.out.println("Compound Interest:");
		System.out.println(calculateContinuousCompoundInterest(100d, .05d, 1, PaymentFrequency.SEMI_ANNUALLY));
		System.out.println(calculateContinuousCompoundInterestV3(100d, .05d, 1, PaymentFrequency.SEMI_ANNUALLY));
		System.out.println(calculateContinuousCompoundInterest(100d, .05d, 5, PaymentFrequency.SEMI_ANNUALLY));
	}	
		

	@Test
	public void testGetEAR() {
		System.out.println(new Rate(.05).getEAR(PaymentFrequency.SEMI_ANNUALLY));	
		System.out.println(new Rate(.05).getEAR(PaymentFrequency.QUARTERLY));
		System.out.println(new Rate(.05).getEAR(PaymentFrequency.WEEKLY));
	}	
}
