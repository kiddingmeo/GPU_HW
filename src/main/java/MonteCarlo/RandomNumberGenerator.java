package MonteCarlo;
import java.util.*;
/**
 * Created by chaozhao on 14-12-14.
 */

public class RandomNumberGenerator implements RandomVectorGenerator{

    private int N;
    double mean = 0;
    double sd = 0;
    int d;

    public RandomNumberGenerator(int N){
        this.N = N;
    }



    public ArrayList<Double> randomvectorgenerator(double mean, double sd, int days) {
        this.mean = mean;
        this.sd = sd;
        Random r = new Random();
        d = days;
        ArrayList<Double> v = new ArrayList<Double>();
        for (int i = 0; i < days; i++){
            v.add(r.nextDouble());
        }
        randomvectorgenerator2(v);
        return v;
    }

    public double[] randomvectorgenerator2(ArrayList a){
        double[] kk = new double[d];
        for (int i = 0; i<d; i++)
            kk[i]=a.indexOf(i);
        return kk;
    }
}
