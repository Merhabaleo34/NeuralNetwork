public class Main
{
	public static void main(String[] args) {
		network test = new network(2, new int[] {3,2});
		
		for (int i = 0; i < 100; i++){
		    double result = test.Train(new double[] {1.0,1.0}, new double[] {0.0,1.0});
		    System.out.println(result);
		
		    //System.out.println(result);
		}
		System.out.println("-------");
		System.out.println(test.run(new double[] {1.0,1.0})[0]);
		System.out.println(test.run(new double[] {1.0,1.0})[1]);
	}
}

class neuron {
	public double[] Weights;
	public double Bias;

	public double NetValue = 0;
	public double ActivationValue = 0;

	public double Adjustment = 0;

	public neuron(int Connections) {
		Weights = new double[Connections];
		for (int i = 0; i<Connections; i++) {
			Weights[i] = Math.random() * 2 - 1;
		}
		Bias = Math.random() * 2 - 1;
	}

	public void fire(double[] Values) {
		int Count = 0;

		for (double num:Values) {
			NetValue += num*Weights[Count++];
		}
		NetValue += Bias;
		ActivationValue = neuron.ActivationFunction(NetValue);
	}

	private static double ActivationFunction(double value) {
		double x = Math.exp(value);
		return x/(x + 1);
	}

	public static double ActivationFunctionDerivative(double value) {
		double x = Math.exp(value);
		return x/Math.pow(x + 1, 2);
	}
}

class layer {
	public neuron[] Neurons;

	public layer(int n, int c) {
		Neurons = new neuron[n];
		for(int i = 0; i < n; i++) {
			Neurons[i] = new neuron(c);
		}
	}

	public void activate(double[] input) {
		for(neuron node: Neurons) {
			node.fire(input);
		}
	}
}

class network {

	public static final double LearnRate = 0.2;

	public layer[] Layers;

	public network(int inputLayer, int[] layers) {

		Layers = new layer[layers.length];

		int LayerSize = inputLayer;
		
		int count = 0;

		for (int i:layers) {
			Layers[count++] = new layer(i, LayerSize);
			LayerSize = i;
		}
	}

	public double[] run(double[] input) {
		double[] prev_result = input;
		for (layer Layer: Layers) {
			Layer.activate(prev_result);
			for (int d = 0; d<prev_result.length; d++) {
				prev_result[d] = Layer.Neurons[d].ActivationValue;
			}
		}
		return prev_result;
	}

	public layer getLastLayer() {
		return Layers[Layers.length - 1];
	}

	public void CalculateAdjustment(double[] costDerivatives) {
		int layerLength = Layers.length;

		//Calculate first layer
		for (int j = 0; j < Layers[layerLength - 1].Neurons.length; j++) {
			neuron Ne = Layers[layerLength - 1].Neurons[j];
			Ne.Adjustment = neuron.ActivationFunctionDerivative(Ne.NetValue)*costDerivatives[j];
		}

		//Calculate the rest
		for (int l = layerLength-2; l>0; l--) {
			for (int k = 0; k < Layers[l].Neurons.length; k++) {
				neuron Ne = Layers[l].Neurons[k];
				for (double weight: Ne.Weights) {
					Ne.Adjustment += weight*Layers[l+1].Neurons[k].Adjustment;
				}
				Ne.Adjustment *= neuron.ActivationFunctionDerivative(Ne.NetValue);
			}
		}
	}

	public void CalculateGradient(double[] input) {

		for (int i = 0; i < input.length; i++) {
			neuron Ne = Layers[0].Neurons[i];

			double NeAdjustment = Ne.Adjustment;

			for (int w = 0; w < Ne.Weights.length; w++) {

				Ne.Weights[w] -= input[w] * LearnRate * NeAdjustment;
			}

			Ne.Bias -= NeAdjustment * LearnRate;
		}

		for (int l = 1; l < Layers.length; l++) {

			layer currentLayer = Layers[l];

			neuron[] LastNeurons = Layers[l - 1].Neurons;

			double[] NetValues = new double[LastNeurons.length];

			for (int n = 0; n < LastNeurons.length; n ++) {
				NetValues[n] = LastNeurons[n].NetValue;
			}

			for (int n = 0; n < currentLayer.Neurons.length; n++) {
			    
			    neuron Ne = currentLayer.Neurons[n];
			    
			    double NeAdjustment = Ne.Adjustment;
			    
				for (int w = 0; w < Ne.Weights.length; w++) {
					Ne.Weights[w] -= NetValues[w] * LearnRate * NeAdjustment;
				}
				
				Ne.Bias -= NeAdjustment * LearnRate;
			}
		}
	}
	
	public double Train(double[] input, double[] SearchedOutput){
	    double[] output = run(input);
	    
	    double[] costDerivatives = new double[SearchedOutput.length];
	    
	    for (int o = 0; o < output.length; o++){
	        costDerivatives[o] = 2 * (SearchedOutput[o] - output[o]);
	    }
	    
	    CalculateAdjustment(costDerivatives);
	    CalculateGradient(input);
	    
	    double cost = 0;
	    
	    for (int o = 0; o < output.length; o++){
	        cost += Math.pow(SearchedOutput[o] - output[o], 2);
	    }
	    
	    return cost;
	}
}
