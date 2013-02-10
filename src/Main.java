import java.util.ArrayList;
import NeuralNetwork.Error;
import NeuralNetwork.NeuralNet;

public class Main implements Error {

	ArrayList<Double> correct;
	ArrayList< ArrayList<Double> > input;
	double nnOutput, actual;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main xor = new Main();
		xor.correct = new ArrayList<Double>();
		xor.correct.add(0.0);
		xor.correct.add(1.0);
		xor.correct.add(1.0);
		xor.correct.add(0.0);
		
		xor.input = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> first = new ArrayList<Double>();
		first.add(1.0);
		first.add(1.0);
		xor.input.add(first);
		
		first = new ArrayList<Double>();
		first.add(1.0);
		first.add(0.0);
		xor.input.add(first);
		
		first = new ArrayList<Double>();
		first.add(0.0);
		first.add(1.0);
		xor.input.add(first);
		
		first = new ArrayList<Double>();
		first.add(0.0);
		first.add(0.0);
		xor.input.add(first);
		
		ArrayList<Integer> struc = new ArrayList<Integer>();
		struc.add(2);
		struc.add(2);
		struc.add(1);
		NeuralNet nn = new NeuralNet(struc);
		
		for (int i = 0; i < 10000; i++)
		{
			if ((i % 100) == 0)
			{
				System.out.println(i + "");
			}
			for (int j = 0; j < xor.input.size(); j++)
			//for (int j = 0; j < 1; j++)
			{
				xor.nnOutput = nn.getOutput(xor.input.get(j)).get(0);
				//System.out.println(nn.network.get(0).get(0).weights.size() + "");
				xor.actual = xor.correct.get(j);
				if ((i % 100) == 0)
				{
				System.out.println("Neural Output " + xor.nnOutput + "   " + "Actual: " + xor.actual );
				
				}
				ArrayList<Double> error  = new ArrayList<Double>();
				error.add(xor.deltaError());
				nn.Train(error);
			}
			
		}

	}
	public double deltaError()
	{
		return actual - nnOutput;
	}

}
