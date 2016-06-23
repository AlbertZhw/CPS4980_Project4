package partA;

public class NeuralNetwork {
	static double[] inputs;
	static double[] hiddens;
	static double[] outputs;
	static double[] targets;
	static double[] errorDeltas;
	static double[] hiddenDeltas;
	static double[][] inputHiddenWeights;
	static double[][] hiddenOutputWeights;
	static double[][] updateInputHiddenWeight;
	static double[][] updateHiddenOutputWeight;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] input1 = {1,0};
		double[] target1 = {1,0};
		double[] input2 = {0,1};
		double[] target2 = {0,1};
		initNetwork(2, 2, 2);
		for (int i = 0; i < 300; i++) {
			runOnce(input1,target1);
			runOnce(input2,target2);
			System.out.println();			
		}
		double [] testInput = {1,0};
		double [] finalOutput = forwordOutput(testInput);
		for (int i = 0; i < finalOutput.length; i++) {
			System.out.println("Final out put: " + finalOutput[i]);		
		}
		
	}
	static void initNetwork(int inputSize,int hiddenSize, int outputSize){
		//inputs = new double[inputSize];
		hiddens = new double[hiddenSize];
		outputs = new double[outputSize];
		//targets = new double[outputSize];
		errorDeltas = new double[outputSize];
		hiddenDeltas = new double[hiddenSize];
		inputHiddenWeights = new double[inputSize][hiddenSize];
		hiddenOutputWeights = new double[hiddenSize][outputSize];
		updateInputHiddenWeight = new double[hiddenSize][inputSize];
		updateHiddenOutputWeight = new double[outputSize][hiddenSize];
		randomWeight();
	}
	static void setInputAndTarget(double[] input, double[] target){
		inputs = input;
		targets = target;
	}
	static void randomWeight(){
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
	static double[] forwordOutput(double[] input){
		for(int i = 0; i < hiddens.length;i++){
			for(int j = 0; j < input.length;j++){
				hiddens[i] += input[j] * inputHiddenWeights[j][i];
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
		return outputs;
	}
	static void errorDeltas(){
		for(int i = 0; i<errorDeltas.length;i++){
			errorDeltas[i] = outputs[i]*(1-outputs[i])*(targets[i] - outputs[i]);
			System.out.println("error:" + errorDeltas[i]);
		}
	}
	static void adjustHiddenOutputWeight(){
		int learningRate = 1;
		for(int i = 0; i < outputs.length;i++){
			for(int j = 0; j < hiddens.length;j++){
				hiddenOutputWeights[j][i] +=  learningRate*errorDeltas[i]*hiddens[j];
			}
		}
	}
	static void hiddenDeltas(){
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
	static void adjustInputHiddenWeight(){
		int learningRate = 1;
		for(int i = 0; i < hiddens.length;i++){
			for(int j = 0; j < inputs.length;j++){
				inputHiddenWeights[j][i] +=  learningRate*hiddenDeltas[i]*inputs[j];
				System.out.println(inputHiddenWeights[j][i]);
			}
		}
	}
	static double sigmoid(double z) {
		double sigmoid = 1.00 / (1.00 + Math.pow(Math.E, -z));
		return sigmoid;
	}
	static void runOnce(double[] input, double[] target){
		setInputAndTarget(input, target);
		forwordOutput(inputs);
		errorDeltas();
		adjustHiddenOutputWeight();
		hiddenDeltas();
		adjustInputHiddenWeight();
	}

}
