/*
 * author Chao Zhao
 * N11592683
 * Monte-Carlo Assignment
 */

package N11592683_HW3;

import java.util.ArrayList;

import N11592683_HW3.StockPath.Data;

// This class implements the method to calculate the payout of Asian call option.
public class Asian_Call implements PayOut {

	// Calculate the payout of the option according to a specific stock path.
	public double getPayout(ArrayList<Data> stockpath, double strikeprice) {
		double payout;
		double sum = 0.0;
		double average;
		for (int i = 0; i < stockpath.size(); i++){
			sum += stockpath.get(i).price;
		}
		average = sum / stockpath.size();
		if (average <= strikeprice) payout = 0.0;
		else payout = average - strikeprice;
		return payout;
	}
	
}
