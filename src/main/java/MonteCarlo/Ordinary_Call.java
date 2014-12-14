/*
 * author Chao Zhao
 * N11592683
 * Monte-Carlo Assignment
 */

package MonteCarlo;

import java.util.ArrayList;
import N11592683_HW3.StockPath.Data;

// This class implements of the algorithm to calculate the payout of ordinary European call option.
public class Ordinary_Call implements PayOut{
	
	// Calculating the payout of European call option.
	public double getPayout(ArrayList<Data> stockpath, double strikeprice) {
		double payout = 0.0;
		if (stockpath.get(stockpath.size()-1).price <= strikeprice) payout = 0.0;
		else payout = stockpath.get(stockpath.size()-1).price - strikeprice;
		return payout;
	}
}
