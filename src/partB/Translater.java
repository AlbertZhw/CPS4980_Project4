package partB;

import java.util.Scanner;

public class Translater {
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
	static boolean flag = true;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input1 = "Hello";
		String input2 = "Hi";
		String input3 = "howdy";
		String input4 = "How do you do";
		String input5 = "How are you";
		String input6 = "What is up?";
		String input7 = "What is cooking?";
		String target1 = "Nihao";
		String target2 = "Nihaoma?";

		initNetwork(16, 10, 8);
		//while (flag) {
			for(int i = 0; i < 50000; i++)	{
			runOnce(generateDouble(input1, 16), generateDouble(target1, 8));
			runOnce(generateDouble(input2, 16), generateDouble(target1, 8));
			runOnce(generateDouble(input3, 16), generateDouble(target1, 8));
			runOnce(generateDouble(input4, 16), generateDouble(target1, 8));
			runOnce(generateDouble(input5, 16), generateDouble(target2, 8));
			runOnce(generateDouble(input6, 16), generateDouble(target2, 8));
			runOnce(generateDouble(input7, 16), generateDouble(target2, 8));
			System.out.println();
			}
		//}
		System.out.println("==========Finished Adjusting==========");
		System.out.println();
		System.out.println("================Input=================");
		System.out.println("test inputs (split by space): ");
		Scanner in = new Scanner(System.in);
		String stringTestInput = in.next();
		double[] testInput = generateDouble(stringTestInput, 16);
		double[] finalOutput = forwordOutput(testInput);
		System.out.println("===============Output=================");
		for (int i = 0; i < finalOutput.length; i++) {
			System.out.println("Final out put: " + finalOutput[i]);
		}

	}

	static void initNetwork(int inputSize, int hiddenSize, int outputSize) {
		// inputs = new double[inputSize];
		hiddens = new double[hiddenSize];
		outputs = new double[outputSize];
		// targets = new double[outputSize];
		errorDeltas = new double[outputSize];
		hiddenDeltas = new double[hiddenSize];
		inputHiddenWeights = new double[inputSize][hiddenSize];
		hiddenOutputWeights = new double[hiddenSize][outputSize];
		updateInputHiddenWeight = new double[hiddenSize][inputSize];
		updateHiddenOutputWeight = new double[outputSize][hiddenSize];
		randomWeight();
	}

	static void setInputAndTarget(double[] input, double[] target) {
		inputs = input;
		targets = target;
	}

	static void randomWeight() {
		for (int i = 0; i < inputHiddenWeights.length; i++) {
			for (int j = 0; j < inputHiddenWeights[i].length; j++) {
				inputHiddenWeights[i][j] = Math.random()* 200;
			}
		}
		for (int i = 0; i < hiddenOutputWeights.length; i++) {
			for (int j = 0; j < hiddenOutputWeights[i].length; j++) {
				hiddenOutputWeights[i][j] = Math.random() * 200;
			}
		}
	}

	static double[] forwordOutput(double[] input) {
		for (int i = 0; i < hiddens.length; i++) {
			for (int j = 0; j < input.length; j++) {
				hiddens[i] += input[j] * inputHiddenWeights[j][i];
			}
		}
		for (int i = 0; i < hiddens.length; i++) {
			hiddens[i] = sigmoid(hiddens[i]);
		}
		for (int i = 0; i < outputs.length; i++) {
			for (int j = 0; j < hiddens.length; j++) {
				outputs[i] += hiddens[j] * hiddenOutputWeights[j][i];
			}
		}
		for (int i = 0; i < outputs.length; i++) {
			outputs[i] = sigmoid(outputs[i]);
		}
		return outputs;
	}

	static void errorDeltas() {
		double product = 1;
		for (int i = 0; i < errorDeltas.length; i++) {
			errorDeltas[i] = outputs[i] * (1 - outputs[i]) * (targets[i] - outputs[i]);
			System.out.println("error:" + errorDeltas[i]);
			product *= Math.abs(errorDeltas[i]);
		}
		if (product < Math.pow(0.5, errorDeltas.length))	{
			flag = false;
		}
	}

	static void adjustHiddenOutputWeight() {
		int learningRate = 1;
		for (int i = 0; i < outputs.length; i++) {
			for (int j = 0; j < hiddens.length; j++) {
				hiddenOutputWeights[j][i] += learningRate * errorDeltas[i] * hiddens[j];
			}
		}
	}

	static void hiddenDeltas() {
		double[] hiddenError = new double[hiddens.length];
		for (int i = 0; i < hiddens.length; i++) {
			for (int j = 0; j < outputs.length; j++) {
				hiddenError[i] += errorDeltas[j] * hiddenOutputWeights[i][j];
			}
		}
		for (int i = 0; i < hiddenDeltas.length; i++) {
			hiddenDeltas[i] = hiddenDeltas[i] * (1 - hiddenDeltas[i]) * (hiddenError[i]);
		}
	}

	static void adjustInputHiddenWeight() {
		int learningRate = 1;
		for (int i = 0; i < hiddens.length; i++) {
			for (int j = 0; j < inputs.length; j++) {
				inputHiddenWeights[j][i] += learningRate * hiddenDeltas[i] * inputs[j];
				System.out.println(inputHiddenWeights[j][i]);
			}
		}
	}

	static double sigmoid(double z) {
		double sigmoid = 200.00 / (1.00 + Math.pow(Math.E, -z));
		return sigmoid;
	}

	static void runOnce(double[] input, double[] target) {
		setInputAndTarget(input, target);
		forwordOutput(inputs);
		errorDeltas();
		adjustHiddenOutputWeight();
		hiddenDeltas();
		adjustInputHiddenWeight();
	}
	
	static double[] generateDouble(String str, int size)	{
		char[] temp = str.toCharArray();
		double[] array = new double[size];
		for (int i = 0; i < array.length; i++) {
			if (i < str.length())	{
				array[i] = (double)temp[i];
			} else {
				array[i] = 0;
			}
		}
		return array;
	}

}
