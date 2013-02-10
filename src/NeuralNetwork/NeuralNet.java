/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package NeuralNetwork;
import java.util.ArrayList;
/**
 *
 * @author Drew
 */
public class NeuralNet 
{
	public ArrayList< ArrayList<Neuron> > network;
	private int numLayers;

	public NeuralNet(ArrayList<Integer> structure)
	{
		numLayers = structure.size();
		network = new ArrayList< ArrayList<Neuron> >();
		//System.out.println(numLayers);
		// Initialize the network
		for (int i = 0; i < structure.size(); i++)
		{
			network.add(new ArrayList<Neuron>());
			// Add all neurons plus a bias neuron except do not put a bias node in the output layer
			for (int s = 0; s < (structure.get(i) + 1); s++)
			{
				if (i == (structure.size() - 1) && s == structure.get(i))
				{
					// do not add bias node to output layer
				}
				else
				{
					network.get(i).add(new Neuron());
					//System.out.println("Layer " + i + " Neuron " + s + " ID " + s);
					network.get(i).get(s).id = s;
				}
				//System.out.println(i + " " + s);
			}
		}

		// set associations for back connected
		for (int i = 1; i < numLayers; i++)
		{
			for (int s = 0; s < (structure.get(i)); s++)
			{
				// Set all associations plus the bias's association
				for (int n = 0; n < (structure.get(i - 1) + 1); n++)
				{
					if (i == (numLayers - 1) && s == structure.get(i))
					{

					}
					else// (i != (numLayers - 1) && s != structure.get(i))
					{
						network.get(i).get(s).backwardConnected.add(network.get(i-1).get(n));
					}
					System.out.println("Layer "+ i + " neuron " + s + " is backconnected to Layer " + (i -1) + " neuron " + n );
				}
			}
		}

		// set associations for forward connected
		for (int i = 0; i < numLayers - 1; i++)
		{
			for (int n = 0; n < network.get(i).size(); n++)
			{
				
				
				if ((i + 1) != (numLayers - 1) )
				{
					ArrayList<Neuron> noBias = new ArrayList<Neuron>();
					for (int f = 0; f < network.get(i+1).size() - 1; f++)
					{
						noBias.add(network.get(i+1).get(f));
					}
					network.get(i).get(n).setForwardConnected(noBias);
					System.out.println("Removing Forward Connection of Layer " + i + " neuron " + n + " With a size of " + network.get(i+1).size() + " Neurons");
				}
				else
				{
					network.get(i).get(n).setForwardConnected(network.get(i+1));
					System.out.println("Forward Connection " + network.get(i+1).size() + " i is " + i + " numLayer-1 " + (numLayers - 1));
					
				}
				
			}
		}


		//System.out.println(network.size());
	}

	public void Train(ArrayList<Double> theError)
	{
		//double delta = theError.deltaError();
		//System.out.println("Error" + delta);
		//training(choice,delta);
		training(theError);
	}

	private void training(ArrayList<Double> error)
	{
		// Set delta for output layer
		for (int i = 0; i < network.get(numLayers - 1).size(); i++)
		{
			network.get(numLayers - 1).get(i).setOutDelta(error.get(i));
		}
		// set delta for hidden layers
		for (int L = numLayers - 2; L > 0; L--)
		{
			for (int N = 0; N < network.get(L).size(); N++)
			{
				network.get(L).get(N).setDelta();
			}
		}

		// Adjust weights I need to do it backconnected so start
		//with last layer and go back until the hidden layer just
		// before the input layer
		for (int i = (numLayers - 1); i > 0; i--)
		{
			for (int s =0 ; s < network.get(i).size(); s++)
			{
				//System.out.println("Updating weight on Layer " + i + " neuron " + s);
				network.get(i).get(s).updateWeights(.9);
			}
		}


	}
	

	public ArrayList<Double> getOutput(ArrayList<Double> inputs)
	{
		ArrayList<Double> output = new ArrayList<Double>();

		// Set inputs to the input layer not including the bias node
		for (int i = 0; i < (network.get(0).size() - 1); i++)
		{
			//System.out.println("INput " + i);
			ArrayList<Double> in = new ArrayList<Double>();
			in.add(inputs.get(i));
			network.get(0).get(i).setInput(inputs.get(i));
			if (network.get(0).get(i).weight.size() == 0)
			{
				network.get(0).get(i).makeWeight();
			}
		}
		if (network.get(0).get(network.get(0).size() - 1).weight.size() == 0)
		{
			// Set weight for bias input layer node
			network.get(0).get(network.get(0).size() - 1).makeWeight();
		}

		// For each of the nodes in the hidden and the output layer call activateFunc()
		for (int L = 1; L < network.size(); L++)
		{
			for (int N = 0; N < network.get(L).size(); N++)
			{
				if (network.get(L).get(N).weight.size() == 0 && L != (network.size() - 1))
				{
					network.get(L).get(N).makeWeight();
				}
				//System.out.println("Calling ActiveFunc on Layer " + L + " Neuron " + N);
				if (L != network.size() - 1 && N == network.get(L).size() - 1 )
				{

				}
				else
				{
					network.get(L).get(N).activateFunc();
				}
			}
		}

		// Get the output
		for (int out = 0; out < network.get(numLayers - 1).size(); out++)
		{
			output.add(network.get(numLayers - 1).get(out).getActivationSum());
		}

		return output;
	}
	
}
