
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

		initNetwork(16, 5, 1);
		while (flag) {
			// for (int i = 0; i < 200000; i++) {
			runOnce(generateDouble(input1, 16), new double[] { 1 });
			runOnce(generateDouble(input2, 16), new double[] { 1 });
			runOnce(generateDouble(input3, 16), new double[] { 1 });
			runOnce(generateDouble(input4, 16), new double[] { 1 });
			runOnce(generateDouble(input5, 16), new double[] { 0 });
			runOnce(generateDouble(input6, 16), new double[] { 0 });
			runOnce(generateDouble(input7, 16), new double[] { 0 });
			//System.out.println();
		}
		System.out.println("==========Finished Adjusting==========");
		for (int k = 0; k < 100; k++) {
			System.out.println();
			System.out.println("================Input=================");
			System.out.println("test inputs: ");
			Scanner in = new Scanner(System.in);
			String stringTestInput = in.next();
			double[] testInput = generateDouble(stringTestInput, 16);
			double[] finalOutput = forwordOutput(testInput);
			System.out.println("===============Output=================");
			for (int i = 0; i < finalOutput.length; i++) {
				System.out.println("Final out put: " + getOutput(finalOutput[i]));
			}
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
				inputHiddenWeights[i][j] = Math.random();
			}
		}
		for (int i = 0; i < hiddenOutputWeights.length; i++) {
			for (int j = 0; j < hiddenOutputWeights[i].length; j++) {
				hiddenOutputWeights[i][j] = Math.random();
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
		double sum = 0;
		for (int i = 0; i < errorDeltas.length; i++) {
			errorDeltas[i] = outputs[i] * (1 - outputs[i]) * (targets[i] - outputs[i]);
			//System.out.println("error:" + errorDeltas[i]);
			sum += Math.abs(errorDeltas[i]);
		}
		if (sum < 0.00001) {
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
		for (int i = 0; i < hiddens.length; i++) {
			hiddenDeltas[i] = hiddens[i] * (1 - hiddens[i]) * (hiddenError[i]);
		}
	}

	static void adjustInputHiddenWeight() {
		int learningRate = 1;
		for (int i = 0; i < hiddens.length; i++) {
			for (int j = 0; j < inputs.length; j++) {
				inputHiddenWeights[j][i] += learningRate * hiddenDeltas[i] * inputs[j];
				// System.out.println(inputHiddenWeights[j][i]);
			}
		}
	}

	static double sigmoid(double z) {
		double sigmoid = 1.00 / (1.00 + Math.pow(Math.E, -z));
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

	static double[] generateDouble(String str, int size) {
		char[] ch = str.toCharArray();
		char[] temp = new char[ch.length];
		double[] array = new double[size];
		for (int i = 0; i < temp.length; i++) {
			if (!Character.isLowerCase(ch[i])) {
				temp[i] = Character.toLowerCase(ch[i]);
			} else {
				temp[i] = ch[i];
			}
		}
		for (int i = 0; i < array.length; i++) {
			if (i < str.length()) {
				array[i] = (double)temp[i] * 0.04 - 3.84;
			} else {
				array[i] = 0;
			}
		}
		return array;

	}

	static String getOutput(double num) {
		if (num >= 0.5) {
			return "Nihao";
		} else {
			return "Nihaoma?";
		}
	}

}