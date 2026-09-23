
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
	    
	    System.out.println("\n --RESUTS--");
	    
	    double[] c = {0.5,0.4};
	    for (double z: a.activate(c)){
	        System.out.println(z);
	    }
	}
}

class neuron {
    public double[] Weights;
    public double Bias;
    
    public neuron(int Connections){
        Weights = new double[Connections];
        for (int i = 0;i<Connections;i++){
            Weights[i] = Math.random();
        }
        Bias = Math.random();
    }
    
    public double fire(double[] Values){
        int Count = 0;
        double Sum = 0.0;
        
        for (double num:Values){
            Sum += num*Weights[Count++];
        }
        Sum += Bias;
        return neuron.ActivationFunction(Sum);
    }
    
    private static double ActivationFunction(double value){
        double x = Math.exp(value);
        return x/(x+1);
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
    
    public double[] activate(double[] input){
        double[] array = new double[Neurons.length];
        int count = 0;
        for(neuron node: Neurons){
            array[count++] = node.fire(input);
        }
        return array;
    }
}

class network {
    
    public layer[] Layers;
    
    public network(int inputLayer, int[] layers){ //hLayers ad karışmasın diye

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
            prev_result = Layer.activate(prev_result);
        }
        return prev_result;
    }
}   
