/*
 * author Chao Zhao
 * N11592683
 * Monte-Carlo Assignment
 */

package N11592683_HW3;

import java.util.ArrayList;

import N11592683_HW3.StockPath.Data;

// Interface to implements various payout functions.
public interface PayOut {
	public double getPayout(ArrayList<Data> stockpath, double strikeprice);
}
