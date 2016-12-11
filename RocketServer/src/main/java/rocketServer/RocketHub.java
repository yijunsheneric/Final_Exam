package rocketServer;

import java.io.IOException;

import exceptions.RateException;
import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;


public class RocketHub extends Hub {

	private RateBLL _RateBLL = new RateBLL();
	
	public RocketHub(int port) throws IOException {
		super(port);
	}

	@Override
	protected void messageReceived(int ClientID, Object message) {
		System.out.println("Message Received by Hub");
		
		if (message instanceof LoanRequest) {
			resetOutput();
			
			LoanRequest lq = (LoanRequest) message;
			//	TODO - RocketHub.messageReceived
			//	You will have to:
			//	Determine the rate with the given credit score (call RateBLL.getRate)
			try {
				double rate = RateBLL.getRate(lq.getiCreditScore());
				lq.setdRate(rate);
				double payment = RateBLL.getPayment(lq.getdRate(), 12*lq.getiTerm(), lq.getdAmount());
				lq.setdPayment(payment);
			} catch (RateException e) {
				String problem = "You have entered an invalid credit score, \n";
				problem += "enter an integer between 600 and 800.";
				lq.setsError(problem);
				//e.printStackTrace();
			}
			//		If exception, show error message, stop processing
			//		If no exception, continue
			//	Determine if payment, call RateBLL.getPayment
			//	
			//	you should update lq, and then send lq back to the caller(s)
			
			sendToAll(lq);
		}
	}
}
