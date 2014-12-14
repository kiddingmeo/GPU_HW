/*
 * author Chao Zhao
 * N11592683
 * Monte-Carlo Assignment
 */

package N11592683_HW3;

import java.util.ArrayList;

// Interface that used for generating random vectors for stock price simulation.
public interface RandomVectorGenerator {
	
	public double normaldistribution(double mean, double sd );
	
	public ArrayList<Double> randomvectorgenerator(double mean, double sd, int days);
	}

