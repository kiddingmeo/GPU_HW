/*
 * author Chao Zhao
 * N11592683
 * Monte-Carlo Assignment
 */

package MonteCarlo;

import java.util.ArrayList;

// Decorator that implements the interface RandomVectorGenerator.
public class Decorator implements RandomVectorGenerator {
	
	protected RandomVectorGenerator rvg;
	
	public Decorator (RandomVectorGenerator rvg) {
		this.rvg = rvg;
	}

	public double normaldistribution(double mean, double sd) {
		double test;
		test = rvg.normaldistribution(mean, sd);
		return test;
	}

	public ArrayList<Double> randomvectorgenerator(double mean, double sd, int days) {
		ArrayList<Double> test = new ArrayList<Double>();
		test = rvg.randomvectorgenerator(mean, sd, days);
		return test;
		}
	
	// Function that takes in an array of random numbers and output an array of the negative of the corresponding random numbers.
	public ArrayList<Double> anti(ArrayList<Double> rv) {
		ArrayList<Double> anti = new ArrayList<Double>();
		for (int i = 0; i < rv.size(); i++) {
			anti.add(rv.get(i)*(-1));
		}
		return anti;
		}
	
	}
