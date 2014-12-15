package MonteCarlo;

import com.nativelibs4java.opencl.*;
import org.bridj.Pointer;

import java.util.ArrayList;
import java.util.Random;

import static org.bridj.Pointer.allocateFloats;


public class GPUGenerator implements RandomVectorGenerator {
    // length of random vector
    private int N;
    // batch number
    private int batchNum;
    private double[] normalSequence;
    private int idx;
    double x =0;
    double y =0;

    /*
    * The constructor of GPU Generator
    * */
    public GPUGenerator(int N, int batchNum) {
        this.N = N;
        this.batchNum = batchNum;
        normalSequence = getGPUGaussian(this.batchNum);
        idx = 0;
    }



    @Override
   /*
    *  Generating the normal random variable with length N
    * */
    public ArrayList<Double> randomvectorgenerator(double x, double y, int N) {



        ArrayList<Double> v = new ArrayList<Double>();
        for (int i = 0; i < N; i++){


            if (idx == 2 * batchNum - 1) {
                normalSequence = getGPUGaussian(this.batchNum);
            }

            v.add(normalSequence[idx]);
            idx++;

        }
        return v;
    }

    /*
    * using the best device chosen by kernal to generate normal random variable
    * */
    public double[] getGPUGaussian(int batchNum) {

        idx = 0;
        // Creating the platform which is out computer.
        CLPlatform clPlatform = JavaCL.listPlatforms()[0];

        // Getting all devices
        CLDevice device = clPlatform.listAllDevices(true)[0];

        // making context
        CLContext context = JavaCL.createContext(null, device);

        // making a default FIFO queue.
        CLQueue queue = context.createDefaultQueue();

        // Reading the program sources and compiling:
        String src = "__kernel void fill_in_values(__global const float* a, __global const float* b, __global float* out1, __global float* out2, float pi, int n) \n" +
                "{\n" +
                "    int i = get_global_id(0);\n" +
                "    if (i >= n)\n" +
                "        return;\n" +
                "\n" +
                "    out1[i] = sqrt(-2*log(a[i]))*cos(2*pi*b[i]);\n" +
                "    out2[i] = sqrt(-2*log(a[i]))*sin(2*pi*b[i]);\n" +
                "}";
        CLProgram program = context.createProgram(src);
        program.build();

        CLKernel kernel = program.createKernel("fill_in_values");

        final int n = batchNum;
        final Pointer<Float>
                aPtr = allocateFloats(n),
                bPtr = allocateFloats(n);
        // store the uniform vector in order for GPU to generate Gaussian
        RandomNumberGenerator r1 = new RandomNumberGenerator(2*batchNum);
        ArrayList<Double> a1 = r1.randomvectorgenerator(x,y,N);
        double[] uniformSequence =r1.randomvectorgenerator2(a1) ;

        for (int i = 0; i < n; i++) {
            aPtr.set(i, (float) uniformSequence[2 * i]);
            bPtr.set(i, (float) uniformSequence[2 * i + 1]);

        }

        // use native memory pointers aPtr and bPtr and create OpenCL input buffers) :
        CLBuffer<Float>
                a = context.createFloatBuffer(CLMem.Usage.Input, aPtr),
                b = context.createFloatBuffer(CLMem.Usage.Input, bPtr);

        // Create an OpenCL output buffer :
        CLBuffer<Float>
                out1 = context.createFloatBuffer(CLMem.Usage.Output, n),
                out2 = context.createFloatBuffer(CLMem.Usage.Output, n);

        kernel.setArgs(a, b, out1, out2, (float) Math.PI, batchNum);
        CLEvent event = kernel.enqueueNDRange(queue, new int[]{n}, new int[]{128});
        event.invokeUponCompletion(new Runnable() {
            @Override
            public void run() {

            }
        });
        final Pointer<Float> c1Ptr = out1.read(queue, event);
        final Pointer<Float> c2Ptr = out2.read(queue, event);

        double[] normalSequence = new double[2 * batchNum];
        for (int i = 0; i < batchNum; i++) {
            normalSequence[2 * i] = (double) c1Ptr.get(i);
            normalSequence[2 * i + 1] = (double) c2Ptr.get(i);
        }
        return normalSequence;
    }
}