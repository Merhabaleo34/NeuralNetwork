
public class Network
{
	public static void main(String[] args) {
	    layer a = new layer(3, 2);  
	    for (neuron i:a.Neurons){
	        System.out.println();
		    System.out.println(i);
		    for (double m:i.Weights){
		        System.out.print("weight: ");
		        System.out.println(m);
		    }
		    System.out.print("bias: ");
		    System.out.println(i.Bias);
	    }
	}
}

class neuron {
    public double[] Weights;
    public double Bias;

    public double Value;

    public neuron(int Connections){
        Weights = new double[Connections];
        for (int i = 0;i<Connections;i++){
            Weights[i] = Math.random();
        }
        Bias = Math.random();
        Value = 0.0;
    }
    
    public void fire(double[] Values){
        int Count = 0;
        
        for (double num:Values){
            Value += num*Weights[Count++];
        }
        Value += Bias;
        Value = neuron.ActivationFunction(Value);
    }
    
    private static double ActivationFunction(double value){
        double x = Math.exp(value);
        return x/(x+1);
    }

    public static double ActivationFunctionDerivative(double value){
        double x = Math.exp(value);
        return x/Math.pow(x+1, 2);
    }
}

class layer {
    public neuron[] Neurons;
    
    public layer(int n, int c){
        Neurons = new neuron[n];
        for(int i = 0; i < n; i++){
            Neurons[i] = new neuron(c);
        }
    }
    
    public void activate(double[] input){
        for(neuron node: Neurons){
            node.fire(input);
        }
    }
}

class network {
    
    public layer[] Layers;
    
    public network(int inputLayer, int[] layers){

        Layers = new layer[layers.length];

        int LayerSize = inputLayer;

        for (int i:layers){
            Layers[i] = new layer(i, LayerSize);
            LayerSize = i;
        }
    }
    
    public double[] run(double[] input){
        double[] prev_result = input;
        for (layer Layer: Layers){
            Layer.activate(prev_result);
            for (int d = 0; d<prev_result.length; d++){
                prev_result[d] = Layer.Neurons[d].Value;
            }
        }
        return prev_result;
    }

    public layer getLastLayer(){
        return Layers[Layers.length-1];
    }

    public double cost(double[] input, double[] expected){
        double[] x = run(input);
        double sum = 0;
        for (int b = 0; b<x.length; b++){
            sum += (Math.pow(x[b]-expected[b], 2));
        }
        return sum;
    }

    public double[][] CalculateAdjustment(double[] costDerivatives){
        int layerLength = Layers.length;
        // Initialize the AdjustmentValues
        double[][] AdjustmentValues = new double[layerLength][];
        for (int i = 0; i<layerLength; i++){
            AdjustmentValues[i] = new double[Layers[i].Neurons.length];
        }

        //Calculate first layer
        for (int j = 0; j < Layers[layerLength-1].Neurons.length; j++){
        AdjustmentValues[layerLength-1][j] = neuron.ActivationFunctionDerivative(costDerivatives[j]);
        }

        //Calculate the rest
        for (int l = layerLength-1; l>0; l--){
            for (int k = 0; k < Layers[l].Neurons.length; k++){
                for (double weight: Layers[l].Neurons[k].Weights){
                    AdjustmentValues[l][k] += weight*Layers[l].Neurons[k].Value;
                }
            }
        }
        return AdjustmentValues;
    }
}   
