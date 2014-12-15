/*
 * author Chao Zhao
 * N11592683
 * Monte-Carlo Assignment
 */

package MonteCarlo;

import java.util.ArrayList;
import org.joda.time.DateTime;
import N11592683_HW3.StockPath.Data;

//This class is used to put all the methods together to do Monte-Carlo simulation.
public class Simulation_Manager {
	
	public static void main(String[] args) {
		
		// Various parameters that is given for this specific problem.
		int days = 252;
		int maxPath = 1000000;
		double r = 0.0001;
		double sigma = 0.01;
		double startprice = 152.35;
		Double StrikePrice_oc = 165.0;
		Double StrikePrice_ac = 164.0;
		DateTime startdate = new DateTime(2013, 9, 28, 17, 30);
		
		// Implementing other classes.
		Ordinary_Call oc = new Ordinary_Call();
		Asian_Call ac = new Asian_Call();
		stock IBM = new stock();
		RandomVector rvg = new RandomVector();
		Decorator dec = new Decorator(rvg);
		calculatePrice cp = new calculatePrice();
		
		// Used to calculate option prices.
		double price_oc, price_ac;
		double ordinary_payout = 0.0;
		double Asian_payout = 0.0;
		
		// Used to judge whether the required accuracy is achieved or not.
		double ocv1 = 0.0;
		double acv1 = 0.0;
		double ocsum1 = 0.0;
		double acsum1 = 0.0;
		double ocaverage1, acaverage1;
		double criticalvalue = 1.75;
		int N = 0;
		
		// Arrays to store payout of different paths.
		ArrayList<Double> ocpayout = new ArrayList<Double>();
		ArrayList<Double> acpayout = new ArrayList<Double>();
		
		for (int i = 0; i < maxPath/2; i++){
			ArrayList<Double> a =dec.randomvectorgenerator(0, 1, days);
			ArrayList<Double> b =dec.anti(a);
			
			// Generating a stock path.
			ArrayList<Data> stockpath1 = IBM.getData(IBM.getPrice(startprice, r, sigma, a), IBM.getDate(startdate, days));
			
			ordinary_payout += oc.getPayout(stockpath1, StrikePrice_oc);
			ocpayout.add(oc.getPayout(stockpath1, StrikePrice_oc));
			
			Asian_payout += ac.getPayout(stockpath1, StrikePrice_ac); 
			acpayout.add(ac.getPayout(stockpath1, StrikePrice_ac));
			
			// Generating the anti-thetic stock path.
			ArrayList<Data> antistockpath = IBM.getData(IBM.getPrice(startprice, r, sigma, b), IBM.getDate(startdate, days));
			
			ordinary_payout += oc.getPayout(antistockpath, StrikePrice_oc);
			ocpayout.add(oc.getPayout(antistockpath, StrikePrice_oc));
			
			Asian_payout += ac.getPayout(antistockpath, StrikePrice_ac);
			acpayout.add(ac.getPayout(antistockpath, StrikePrice_ac));
			
			// Calculating the current average payout. 
			for (int j = 0; j < ocpayout.size(); j++){
				ocsum1 += ocpayout.get(j);
				acsum1 += acpayout.get(j);
			}
			ocaverage1 = ocsum1/ocpayout.size();
			acaverage1 = acsum1/acpayout.size();
			
			// Calculate the current payout variance.
			for (int k = 0; k < ocpayout.size(); k++){
				ocv1 += Math.pow((ocpayout.get(k)-ocaverage1),2);
				acv1 += Math.pow((acpayout.get(k)-acaverage1),2);
			}
			ocv1 = ocv1 / ocpayout.size();
			acv1 = acv1 / acpayout.size();
			
			// Using Central Limit Theorem (CLT) to judge whether we have achieved the desired accuracy.
			double ocValue = Math.sqrt(i)*0.01*ocaverage1/Math.sqrt(ocv1);
			double acValue = Math.sqrt(i)*0.01*acaverage1/Math.sqrt(acv1);
			
			
			// If the desired accuracy is achieved, then break the for loop.
			if ((ocValue != 0.0) && (acValue != 0.0) && (ocValue >= criticalvalue) && (acValue >= criticalvalue)) break;
			N++;
		}
		
		// Calculating option prices.
		ordinary_payout = ordinary_payout / (2*N);
		Asian_payout = Asian_payout / (2*N);
		price_oc = cp.Price(ordinary_payout, r, days);
		price_ac = cp.Price(Asian_payout, r, days);
		
		// Print the output.
		System.out.println("The price for the European call option is: " + String.format("%.4f", price_oc) + " (rounded to fourth decimal).");
		System.out.println("The Price for the Asian call option is: " + String.format("%.4f", price_ac) + " (rounded to fourth decimal).");
		System.out.println("Total path simulated: " + 2*N + ".");
	}	
}
