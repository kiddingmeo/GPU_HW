/*
 * author Chao Zhao
 * N11592683
 * Monte-Carlo Assignment
 */

package MonteCarlo;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.NormalDistribution;

// Generating an array of random vector that satisfies normal distribution with Mean mean and standard deviation sd.
public class RandomVector implements RandomVectorGenerator {

	// Generating one random number.
	public double normaldistribution(double mean, double sd ) {
		double next;
		NormalDistribution ND = new NormalDistribution(mean, sd);
		next = ND.sample();
		return next;
	}
	
	// Generating a array of random numbers.
	public ArrayList<Double> randomvectorgenerator(double mean, double sd, int days) {
		ArrayList<Double> v = new ArrayList<Double>();
		for (int i = 0; i < days; i++){
			v.add(normaldistribution(mean, sd));
		}
		return v;
		}
}
