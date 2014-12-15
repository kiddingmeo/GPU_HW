/*
 * author Chao Zhao
 * N11592683
 * Monte-Carlo Assignment
 */

package MonteCarlo;

import java.util.ArrayList;

import org.joda.time.DateTime;

// Interface used to calculate stock path.
public interface StockPath {
	
	// This class is used to store the pair data of Date and Price
	class Data {
		DateTime date;
		double price;
	}
	
	public ArrayList<Double> getPrice(double startprice, double r, double sigma, ArrayList<Double> arr);
	public ArrayList<DateTime> getDate(DateTime startDate, int days);
	public ArrayList<Data> getData(ArrayList<Double> price, ArrayList<DateTime> date);
}
