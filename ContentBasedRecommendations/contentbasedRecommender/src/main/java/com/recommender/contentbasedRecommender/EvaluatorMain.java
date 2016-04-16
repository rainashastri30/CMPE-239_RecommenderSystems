package com.recommender.contentbasedRecommender;

public class EvaluatorMain {

	public static void main(String[] args) {

		Evaluator eval = new Evaluator();

		eval.createPearsonCoefficientWithNNN(5, 0.1);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithNNN(5, 0.3);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithNNN(5, 0.5);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithNNN(5, 0.7);
		System.out.println("====================================================================================");
		eval.createPearsonCoefficientWithNNN(5, 0.9);
		System.out.println("====================================================================================");

	}

}
