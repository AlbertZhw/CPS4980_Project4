package partA;

public class NeuralNetwork {
	double[] inputs = {1,0};
	double[] hiddens;
	double[] outputs;
	double[] targets;
	double[] errorDeltas;
	double[] hiddenDeltas;
	double[][] inputHiddenWeights;
	double[][] hiddenOutputWeights;
	double[][] updateInputHiddenWeight;
	double[][] updateHiddenOutputWeight;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	void initNetwork(int inputSize,int hiddenSize, int outputSize){
		//inputs = new double[inputSize];
		hiddens = new double[hiddenSize];
		outputs = new double[outputSize];
		targets = new double[outputSize];
		errorDeltas = new double[outputSize];
		hiddenDeltas = new double[hiddenSize];
		inputHiddenWeights = new double[inputSize][hiddenSize];
		hiddenOutputWeights = new double[hiddenSize][outputSize];
		updateInputHiddenWeight = new double[hiddenSize][inputSize];
		updateHiddenOutputWeight = new double[outputSize][hiddenSize];
		randomWeight();
	}
	void randomWeight(){
		for(int i=0;i < inputHiddenWeights.length;i++){
			for(int j=0;j < inputHiddenWeights[i].length;j++){
				inputHiddenWeights[i][j] = Math.random();
			}
		}
		for(int i=0;i < hiddenOutputWeights.length;i++){
			for(int j=0;j < hiddenOutputWeights[i].length;j++){
				hiddenOutputWeights[i][j] = Math.random();
			}
		}
	}
	void forwordOutput(){
		for(int i = 0; i < hiddens.length;i++){
			for(int j = 0; j < inputs.length;j++){
				hiddens[i] += inputs[j] * inputHiddenWeights[j][i];
			}
		}
		for(int i = 0; i<hiddens.length;i++){
			hiddens[i] = sigmoid(hiddens[i]);
		}
		for(int i = 0; i < outputs.length;i++){
			for(int j = 0; j < hiddens.length;j++){
				outputs[i] += hiddens[j] * hiddenOutputWeights[j][i];
			}
		}
		for(int i = 0; i<outputs.length;i++){
			outputs[i] = sigmoid(outputs[i]);
		}
	}
	void errorDeltas(){
		for(int i = 0; i<errorDeltas.length;i++){
			errorDeltas[i] = outputs[i]*(1-outputs[i])*(targets[i] - outputs[i]);
		}
	}
	void adjustHiddenOutputWeight(){
		int learningRate = 1;
		for(int i = 0; i < outputs.length;i++){
			for(int j = 0; j < hiddens.length;j++){
				hiddenOutputWeights[j][i] +=  learningRate*errorDeltas[i]*hiddens[j];
			}
		}
	}
	void hiddenDeltas(){
		double[] hiddenError = new double[hiddens.length];
		for(int i = 0; i<hiddens.length;i++){
			for(int j = 0; j < outputs.length;j++){
				hiddenError[i] += errorDeltas[j] * hiddenOutputWeights[i][j]; 
			}
		}
		for(int i = 0; i<hiddenDeltas.length;i++){
			hiddenDeltas[i] = hiddenDeltas[i]*(1-hiddenDeltas[i])*(hiddenError[i]);
		}
	}
	void adjustInputHiddenWeight(){
		int learningRate = 1;
		for(int i = 0; i < hiddens.length;i++){
			for(int j = 0; j < inputs.length;j++){
				inputHiddenWeights[j][i] +=  learningRate*hiddenDeltas[i]*inputs[j];
			}
		}
	}
	double sigmoid(double z) {
		double sigmoid = 1.00 / (1.00 + Math.pow(Math.E, -z));
		return sigmoid;
	}
	void runOnce(){
		initNetwork(2, 2, 2);
		forwordOutput();
		errorDeltas();
		adjustHiddenOutputWeight();
		hiddenDeltas();
		adjustInputHiddenWeight();
	}

}
