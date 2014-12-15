/*
 * author Chao Zhao
 * N11592683
 * Monte-Carlo Assignment
 */

package MonteCarlo;

import java.util.ArrayList;

import org.joda.time.DateTime;

// Implements the stock interface. Used to produce stock path
public class stock implements StockPath {
	
	// Input an array of random numbers and some parameters of the stock. Output one stock path.
	public ArrayList<Double> getPrice(double stockprice, double r, double sigma, ArrayList<Double> arr) {
		ArrayList<Double> rd = new ArrayList<Double>();
		double a = Math.pow(sigma, 2)/2;
		rd.add(stockprice*Math.exp((r-a)+sigma * arr.get(0)));
		for (int i = 1; i < arr.size(); i++){
			double index = (r - a) + sigma * arr.get(i);
			rd.add((rd.get(i-1)* Math.exp(index)));
		}
		return rd;
	}

	// Given the start date, output the date array.
	public ArrayList<DateTime> getDate(DateTime startDate, int days) {
		ArrayList<DateTime> range= new ArrayList<DateTime>();
		for (int i = 0; i < days; i++) {
			startDate.plusDays(1);
			range.add(startDate);
		}
		return range;
	}

	// Given data array and the stock price array, output the array of the pair of date and price.
	public ArrayList<Data> getData(ArrayList<Double> price, ArrayList<DateTime> date) {
		ArrayList<Data> K = new ArrayList<Data>();
		for(int i = 0; i < price.size(); i++){
			Data temp = new Data();
			temp.date = date.get(i);
			temp.price = price.get(i);
			K.add(temp);
			}
		return K;
		}
	}