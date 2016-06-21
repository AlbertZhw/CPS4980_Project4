package partA;

public class Neural {
	private double[] inputs;
	private double[] weights;
	private double output = -1.00;
	private Neural[] parents;

	public Neural() {
		inputs = new double[0];
		weights = new double[0];
	}

	public Neural(double[] inputs, double[] weight) {
		this.inputs = inputs;
		this.weights = inputs;
	}

	public Neural(double[] inputs, double[] weight, Neural[] parents) {
		this.inputs = inputs;
		this.weights = inputs;
		this.parents = parents;
	}
	
	public Neural[] getParents() {
		return parents;
	}
	
	public double[] getInputs() {
		return inputs;
	}

	public double[] getWeights() {
		return weights;
	}
	
	public double getOutput() {
		double sum = 0.00;
		for (int i = 0; i < inputs.length; i++) {
			sum += inputs[i] * weights[i];
		}
		output = sigmoid(sum);
		return output;
	}

	public void setInputs(double[] inputs) {
		this.inputs = inputs;
	}
	
	public void setParents(Neural[] parents) {
		this.parents = parents;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	double sigmoid(double z) {
		double sigmoid = 1.00 / (1.00 + Math.pow(Math.E, -z));
		return sigmoid;
	}
}
