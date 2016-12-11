package rocketBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class rate_test {


	//TODO - RocketBLL rate_test
	//		Check to see if a RateException is thrown if there are no rates for a given
	//		credit score
	@Test
	public void test() {
		ArrayList<RateDomainModel> rates = RateDAL.getAllRates();
		//System.out.println ("Rates size: " + rates.size());
		assert(rates.size() > 0);
		for (RateDomainModel rdm:rates){
			assert(rdm.getdInterestRate()!=0);
			assert(rdm.getiMinCreditScore()!=0);
		}
		int CreditScoreThatWorks = 760;
		int CreditScoreThatFails = -1;
		try {
			assert(RateBLL.getRate(CreditScoreThatWorks) !=0);
		} catch (RateException e) {
			//Should not get here
			System.out.println("Should not get here");
			e.printStackTrace();
		}
		try {
			RateBLL.getRate(CreditScoreThatFails);
			System.out.print("Should not get here");
		} catch (RateException e) {
			assert(e.getRateDomainModel().getiMinCreditScore() == 0);
			e.printStackTrace();
		}
		
	}
}
