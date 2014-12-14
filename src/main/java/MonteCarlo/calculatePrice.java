/*
 * author Chao Zhao
 * N11592683
 * Monte-Carlo Assignment
 */

package N11592683_HW3;

// This class is used to transform the expected payout of an option to its current price through discounting.
public class calculatePrice {
	public double Price(double payout, double r, int days) {
		double price;
		price = Math.exp(-1*days*r) * payout;
		return price;
	}
}
