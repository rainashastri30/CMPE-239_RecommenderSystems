package com.recommender.itembasedRecommender;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.common.RandomUtils;

public class Evaluator {

	public static String recsFile = "data/additional-users-preprocessed.csv";

	public void createPearsonCoefficientWithNNN(final double split) {
		RecommenderBuilder itemSimRecBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {

				ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(model);

				Recommender recommender = new GenericItemBasedRecommender(model, itemSimilarity);
				return recommender;
			}
		};

		System.out.println("PearsonCorrelationSimilarity ----- "
				+ " ----- Split: " + split + " RMSE ---> SCORE: "
				+ evaluate(itemSimRecBuilder, new RMSRecommenderEvaluator(), split));
		System.out.println("PearsonCorrelationSimilarity ----- Split: " + split + " MAE ---> SCORE: "
				+ evaluate(itemSimRecBuilder, new AverageAbsoluteDifferenceRecommenderEvaluator(), split));
	}

	private double evaluate(RecommenderBuilder userSimRecBuilder, RecommenderEvaluator evaluator, double split) {
		FileDataModel dataModel;

		double userSimEvaluationScore = 0;
		try {

			dataModel = new FileDataModel(new File(recsFile));
			RandomUtils.useTestSeed();
			userSimEvaluationScore = evaluator.evaluate(userSimRecBuilder, null, dataModel, split, 1.0);
		} catch (TasteException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return userSimEvaluationScore;
	}
}
