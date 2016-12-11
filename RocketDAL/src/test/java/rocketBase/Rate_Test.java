package rocketBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import rocketDomain.RateDomainModel;

public class Rate_Test {

	
	
	//TODO - RocketDAL rate_test
	//		Check to see if a RateException is thrown if there are no rates for a given
	//		credit score
	// done in bll layer
	@Test
	public void test() {
		
		ArrayList<RateDomainModel> rates = RateDAL.getAllRates();
		System.out.println ("Rates size: " + rates.size());
		assert(rates.size() > 0);
		for (RateDomainModel rdm:rates){
			assert(rdm.getdInterestRate()!=0);
			assert(rdm.getiMinCreditScore()!=0);
		}
	}

}
