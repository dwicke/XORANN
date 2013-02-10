/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package NeuralNetwork;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Drew
 */
public class Neuron 
{
    double input;
    ArrayList<Double> weight;
    ArrayList<Neuron> backwardConnected;
    ArrayList<Neuron> forwardConnected;
    int id; 
    
	private double delta, activationSum, derivSum, sum;

	Random rand = new Random();
    public Neuron()
    {
        input = 1.0;
        weight = new ArrayList<Double>();
        backwardConnected = new ArrayList<Neuron>();
        forwardConnected = new ArrayList<Neuron>();
        
    }
    public void setDelta()
    {
        //this.delta = delta;
    	
    	double Derror = 0.0;
        for (int i = 0; i < forwardConnected.size(); i++)
        {
            Derror += weight.get(i) * forwardConnected.get(i).delta;
        }
        delta = derivativeFunc() * Derror;

    	
    }
    public double getDelta()
    {
        return delta;
    }
   
    public void setInput(double input)
    {
       this.input = input;
    }
    public void makeWeight()
     {
    	System.out.print("Making weight for Neuron " + id + " forward connected to " + forwardConnected.size() + " neurons");
    	for (int i = 0; i < forwardConnected.size(); i++)
    	{
    		weight.add(rand.nextDouble());
    		System.out.println(" with a weight " + weight.get(i));
    	}
     }
    private double sum()
    {
        sum = 0.0;
        
        for (int N = 0; N < backwardConnected.size(); N++)
        {
        	double backInput = backwardConnected.get(N).input;
        	//System.out.println("Back input " + backInput);
        	//System.out.println(backwardConnected.get(N).weight.size());
        	double backWeight = backwardConnected.get(N).weight.get(id);
        	sum += backInput * backWeight;
        }
        //System.out.println("Sum " + sum + " BackConnected Size " + backwardConnected.size());
        return sum;
       
    }
    public double activateFunc()
    {
    	if (backwardConnected.size() != 0)
    	{
        // Can change the coefficient of the sum() from 1 to any number
        activationSum = (1 / (1 + Math.exp(sum() * -1)));
        //System.out.println("Activation sum " + activationSum);
        input = activationSum;
        return activationSum;
    	}
    	System.out.println("Active sum = " + 1.0);
    	return 1.0;
    }
    public double derivativeFunc()
    {
        // Can multiply activationSum by a constant to add momentum
        derivSum =  activationSum * (1 - activationSum);
        return derivSum;
    }
    public void updateWeights(double learningRate)
    {
    	// set the weights for all back connected nodes.
    	for (int i = 0; i < backwardConnected.size(); i++)
    	{
    		//System.out.println("Updating weight " + i +  " " + (backwardConnected.get(i).weight.get(id) + (learningRate * delta * backwardConnected.get(i).input)));
    		backwardConnected.get(i).weight.set(id, backwardConnected.get(i).weight.get(id) + (learningRate * delta * backwardConnected.get(i).input));
    	}
    }
 
     public double getActivationSum() {
 		return activationSum;
 	}
 	public void setActivationSum(double activationSum) {
 		this.activationSum = activationSum;
 	}
 	public ArrayList<Neuron> getForwardConnected() {
		return forwardConnected;
	}
	public void setForwardConnected(ArrayList<Neuron> forwardConnected) {
		this.forwardConnected = forwardConnected;
	}
	public void setOutDelta(Double error) {
		this.delta = derivativeFunc() * error;
	}

}
