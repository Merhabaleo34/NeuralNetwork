import java.util.ArrayList;

public class Main
{
	public static void main(String[] args) {
	    layer a = new layer(3, 2);  
	    for (neuron i:a.Neurons){
	        System.out.println();
		    System.out.println(i);
		    for (Double m:i.Weights){
		        System.out.print("weight: ");
		        System.out.println(m);
		    }
		    System.out.print("bias: ");
		    System.out.println(i.Bias);
	    }
	    
	    System.out.println("\n --RESUTS--");
	    
	    Double[] c = {0.5,0.4};
	    for (Double z: a.activate(c)){
	        System.out.println(z);
	    }
	}
}

class neuron {
    public Double[] Weights;
    public Double Bias;
    
    public neuron(int Connections){
        Weights = new Double[Connections];
        for (int i = 0;i<Connections;i++){
            Weights[i] = Math.random(); // TODO
        }
        Bias = Math.random();
    }
    
    public Double fire(Double[] Values){
        int Count = 0;
        Double Sum = 0.0;
        
        for (Double num:Values){
            Sum += num*Weights[Count++];
        }
        Sum += Bias;
        return neuron.ActivationFunction(Sum);
    }
    
    private static Double ActivationFunction(Double value){
        Double x = Math.exp(value);
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
    
    public Double[] activate(Double[] input){
        Double[] array = new Double[Neurons.length];
        int count = 0;
        for(neuron node: Neurons){
            array[count++] = node.fire(input);
        }
        return array;
    }
}

class network {
    
    public layer[] Layers;
    
    public network(int inputLayer, layer[] hLayers){ //hLayers ad karışmasın diye
        //TODO
    }
    
    public Double[] run(Double[] input){ //layerları birleştir
        //TODO
    }
}
