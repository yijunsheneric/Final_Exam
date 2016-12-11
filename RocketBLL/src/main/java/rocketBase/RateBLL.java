package rocketBase;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.*;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int GivenCreditScore) throws RateException 
	{
		ArrayList<RateDomainModel> allRates = RateDAL.getAllRates();
		double rate = 0;
		//System.out.println("getRates");
		for (RateDomainModel RateModel:allRates){
			//System.out.println("get:"+RateModel.getiMinCreditScore());
			if (RateModel.getiMinCreditScore()<=GivenCreditScore){
				rate = RateModel.getdInterestRate();
				break;
			}
		}
		if (rate == 0){
			RateDomainModel rdm = new RateDomainModel();
			rdm.setiMinCreditScore(GivenCreditScore);
			rdm.setiRateID(0);
			rdm.setiRateID(0);
			throw new RateException(rdm);
		}
		else{return rate;}
	}
	
	
	//TODO - RocketBLL RateBLL.getPayment 
	//		how to use:
	//		https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html
	
	public static double getPayment(double rate, double nterms, double start)
	{		
		return FinanceLib.pmt(rate, nterms, 0, start, false);
	}
}
