package rocket.app.view;

import eNums.eAction;
import exceptions.RateException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController {

	private MainApp mainApp;
	
	@FXML
	private TextField income;
	@FXML
	private TextField expenses;
	@FXML
	private TextField creditScore;
	@FXML
	private TextField houseCost;
	@FXML
	private ComboBox<String> term;
	@FXML
	private Label mortgageDetails;
	@FXML
	private Button calculatePayment;
	@FXML
	private Label interestRateLabel;
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	//	TODO - RocketClient.RocketMainController
	//			Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		//	TODO - RocketClient.RocketMainController
		
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		//	TODO - RocketClient.RocketMainController
		//			set the loan request details...  rate, term, amount, credit score, downpayment
		//			I've created you an instance of lq...  execute the setters in lq
		lq.setiCreditScore(Integer.parseInt(creditScore.getText()));
		lq.setiTerm(Integer.parseInt(term.getValue()));
		lq.setdAmount(Double.parseDouble(houseCost.getText()));
		try {
			lq.setdRate(RateBLL.getRate(lq.getiCreditScore()));
		} catch (RateException e) {
			mortgageDetails.setText("Invalid credit score");
			e.printStackTrace();
		}
		//lq.setdPayment(RateBLL.getPayment(lq.getdRate(), lq.getiTerm()*12, lq.getdAmount(), 0, false));
		a.setLoanRequest(lq);
		//	send lq as a message to RocketHub
		Object message = lq;
		mainApp.messageSend(message);
		String rate = "You interest rate is: ";
		rate += lq.getdRate();
		rate += "%";
		interestRateLabel.setText(rate);
		//HandleLoanRequestDetails(lq);
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		String message = lRequest.getsError();
		mortgageDetails.setText("Value will be displayed shortly");
		//getting values
		double payment = Math.round(lRequest.getdPayment()*100)/100;
		
		double Income = Double.parseDouble(income.getText());
		double Expenses = Double.parseDouble(expenses.getText());
		//get max payment
		double max = Income*28/100;
		if ((Income*36/100)-Expenses < max){
			max = (Income*36/100)-Expenses;
		}
		// creating the message
		if (max < payment){
			message += "You do not have enough moeny to afford this house, \n";
			message += "try finding a cheaper one.";
		}else{
			message += "Your monthly payment is: $";
			message += payment;
			message += ".";
		}
		
		mortgageDetails.setText(message);
		
	}
}