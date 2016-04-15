package com.recommender.contentbasedRecommender;

public class EvaluatorMain {

	public static void main(String[] args) {

		Evaluator eval = new Evaluator();
		eval.createPearsonCoefficientWithNNN(3);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithNNN(4);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithNNN(5);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithNNN(7);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithNNN(10);
		System.out.println("====================================================================================");

		eval.createPearsonCoefficientWithThreshold(0.1);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithThreshold(0.3);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithThreshold(0.5);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithThreshold(0.7);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithThreshold(0.9);
		System.out.println("====================================================================================");

		eval.createUncenteredCosineWithNNN(3);
		System.out.println("====================================================================================");
		eval.createUncenteredCosineWithNNN(4);
		System.out.println("====================================================================================");
		eval.createUncenteredCosineWithNNN(5);
		System.out.println("====================================================================================");
		eval.createUncenteredCosineWithNNN(7);
		System.out.println("====================================================================================");
		eval.createUncenteredCosineWithNNN(10);
		System.out.println("====================================================================================");

		eval.createUncenteredCosineWithThreshold(0.1);
		System.out.println("====================================================================================");
		eval.createUncenteredCosineWithThreshold(0.3);
		System.out.println("====================================================================================");
		eval.createUncenteredCosineWithThreshold(0.5);
		System.out.println("====================================================================================");
		eval.createUncenteredCosineWithThreshold(0.7);
		System.out.println("====================================================================================");
		eval.createUncenteredCosineWithThreshold(0.9);
		System.out.println("====================================================================================");

		
		eval.createLogLikeliHoodWithNNN(3);
		System.out.println("====================================================================================");
		eval.createLogLikeliHoodWithNNN(4);
		System.out.println("====================================================================================");
		eval.createLogLikeliHoodWithNNN(5);
		System.out.println("====================================================================================");
		eval.createLogLikeliHoodWithNNN(7);
		System.out.println("====================================================================================");
		eval.createLogLikeliHoodWithNNN(10);
		System.out.println("====================================================================================");

		eval.createLogLikeliHoodWithThreshold(0.1);
		System.out.println("====================================================================================");
		eval.createLogLikeliHoodWithThreshold(0.3);
		System.out.println("====================================================================================");
		eval.createLogLikeliHoodWithThreshold(0.5);
		System.out.println("====================================================================================");
		eval.createLogLikeliHoodWithThreshold(0.7);
		System.out.println("====================================================================================");
		eval.createLogLikeliHoodWithThreshold(0.9);
		System.out.println("====================================================================================");
	}

}