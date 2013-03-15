package selfstudy.financial;

public class Deal implements Comparable<Deal>{

	Rate rate;
	PaymentFrequency paymentBasis;
	int yrs;
	
	
	private double get1yrRate(){
		return rate.getEAR(paymentBasis);
	}
	
	public int compareTo(Deal o) {
		
		return (get1yrRate() > o.get1yrRate()) ? 1 : (o.get1yrRate() > get1yrRate() ? -1 : 0);
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((paymentBasis == null) ? 0 : paymentBasis.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + yrs;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deal other = (Deal) obj;
		if (paymentBasis != other.paymentBasis)
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (yrs != other.yrs)
			return false;
		return true;
	}
	
	
}
