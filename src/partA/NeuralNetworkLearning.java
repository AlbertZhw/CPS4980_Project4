package partA;

import java.util.ArrayList;

public class NeuralNetworkLearning {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static double[] NeuralLearning(Neural[] neurals, double target, int numOfFinalOutput) {

		double[] finalOutputs = new double[numOfFinalOutput];
		for (int i = 0; i < finalOutputs.length; i++) {
			finalOutputs[i] = neurals[i + neurals.length - numOfFinalOutput].getOutput();
		}

		double[] errors = new double[numOfFinalOutput];
		for (int i = 0; i < errors.length; i++) {
			errors[i] = error(target, finalOutputs[i]);
		}

		double[] deltas = new double[numOfFinalOutput];
		for (int i = 0; i < deltas.length; i++) {
			deltas[i] = delta(finalOutputs[i], errors[i]);
		}
		
		ArrayList<Neural> parentNeurals = new ArrayList<Neural>();
		for (int i = 0; i < numOfFinalOutput; i++) {
			Neural[] tempNeural = neurals[i + neurals.length - numOfFinalOutput].getParents();
			for (int j = 0; j < tempNeural.length; j++) {				
				parentNeurals.add(tempNeural[j]);
			}
		}
	}

	public static double error(double target, double output) {
		return (target - output);
	}

	public static double error(double[] errors, double[] weights) {
		double error = 0;
		for (int i = 0; i < errors.length; i++) {
			error += errors[i] * weights[i];
		}
		return error;
	}

	public static double delta(double output, double error) {
		return output * (1 - output) * error;
	}

	double adjustWeight(double oldWeight, double learningRate, double delta, double input) {
		return oldWeight + learningRate * delta * input;
	}

}
