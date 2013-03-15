package utils;

public class TVMUtils {

	// PV = Present Value, FV = Future Value, CF = Cash Flow
	//t=time (yrs), N = number of periods, r = rate
	
	// calc nominal, real, and inflation rates: nominal = (1 + inflation) * (1 + real) - 1
	// calc FV from PV: FV = PV(1 + r)^N
	// calc PV from FV: PV = FV / (1 + r)^N
	
	// continuous compounding
	// calc PV: PV = FV / e^(r * N)
	
	// annutities (finite)
	// calc FV from CF: FV = CF * ((1/r) - (1/(r(1+r)^t)))
	// calc CF from FV: CF = FV / ((1/r) - (1/(r(1+r)^t))) * (1 + r)^t 
	
	// perpetuities
	// calc PV: PV = CF/r
		
	public static Double getPVofPerpetuity(Double cf, Double rate) {
		return cf / rate;
	}
	
}
