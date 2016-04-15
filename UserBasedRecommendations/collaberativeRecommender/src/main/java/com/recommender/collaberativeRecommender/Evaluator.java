package com.recommender.collaberativeRecommender;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class Evaluator {

	public static String recsFile = "data/mahout-new-users-org-csv.csv";

	public void createPearsonCoefficientWithNNN(final int nearestNeighbour) {
		RecommenderBuilder userSimRecBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {

				UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(nearestNeighbour, userSimilarity, model);

				Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
				return recommender;
			}
		};

		System.out.println("PearsonCorrelationSimilarity ----- NearestNUserNeighborhood (" + nearestNeighbour
				+ ") -----RMSE ---> SCORE: " + evaluate(userSimRecBuilder, new RMSRecommenderEvaluator()));
		System.out.println("PearsonCorrelationSimilarity ----- NearestNUserNeighborhood (" + nearestNeighbour
				+ ") -----MAE ---> SCORE: "
				+ evaluate(userSimRecBuilder, new AverageAbsoluteDifferenceRecommenderEvaluator()));
	}

	private double evaluate(RecommenderBuilder userSimRecBuilder, RecommenderEvaluator evaluator) {
		FileDataModel dataModel;

		double userSimEvaluationScore = 0;
		try {

			dataModel = new FileDataModel(new File(recsFile));
			RandomUtils.useTestSeed();
			userSimEvaluationScore = evaluator.evaluate(userSimRecBuilder, null, dataModel, 0.9, 1.0);
		} catch (TasteException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return userSimEvaluationScore;
	}

	public void createPearsonCoefficientWithThreshold(final double threshold) {
		RecommenderBuilder userSimRecBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {

				UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);
				UserNeighborhood neighborhood = new ThresholdUserNeighborhood(threshold, userSimilarity, model);

				Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
				return recommender;
			}
		};

		System.out.println("PearsonCorrelationSimilarity ----- ThresholdUserNeighborhood (" + threshold
				+ ") -----RMSE ---> SCORE: " + evaluate(userSimRecBuilder, new RMSRecommenderEvaluator()));
		System.out.println(
				"PearsonCorrelationSimilarity ----- ThresholdUserNeighborhood (" + threshold + ") -----MAE ---> SCORE: "
						+ evaluate(userSimRecBuilder, new AverageAbsoluteDifferenceRecommenderEvaluator()));

	}

	public void createLogLikeliHoodWithNNN(final int neighbour) {
		RecommenderBuilder userSimRecBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {

				UserSimilarity userSimilarity = new LogLikelihoodSimilarity(model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(neighbour, userSimilarity, model);

				Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
				return recommender;
			}
		};

		System.out.println("LogLikelihoodSimilarity ----- NearestNUserNeighborhood (" + neighbour
				+ ") -----RMSE ---> SCORE: " + evaluate(userSimRecBuilder, new RMSRecommenderEvaluator()));
		System.out.println(
				"LogLikelihoodSimilarity ----- NearestNUserNeighborhood (" + neighbour + ") -----MAE ---> SCORE: "
						+ evaluate(userSimRecBuilder, new AverageAbsoluteDifferenceRecommenderEvaluator()));

	}

	public void createLogLikeliHoodWithThreshold(final double threshold) {
		RecommenderBuilder userSimRecBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {

				UserSimilarity userSimilarity = new LogLikelihoodSimilarity(model);
				UserNeighborhood neighborhood = new ThresholdUserNeighborhood(threshold, userSimilarity, model);

				Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
				return recommender;
			}
		};

		System.out.println("LogLikelihoodSimilarity ----- ThresholdUserNeighborhood (" + threshold
				+ ") -----RMSE ---> SCORE: " + evaluate(userSimRecBuilder, new RMSRecommenderEvaluator()));
		System.out.println(
				"LogLikelihoodSimilarity ----- ThresholdUserNeighborhood (" + threshold + ") -----MAE ---> SCORE: "
						+ evaluate(userSimRecBuilder, new AverageAbsoluteDifferenceRecommenderEvaluator()));

	}

	public void createUncenteredCosineWithNNN(final int nearestNeighbour) {
		RecommenderBuilder userSimRecBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {

				UserSimilarity userSimilarity = new UncenteredCosineSimilarity(model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(nearestNeighbour, userSimilarity, model);

				Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
				return recommender;
			}
		};

		System.out.println("UncenteredCosineSimilarity ----- NearestNUserNeighborhood (" + nearestNeighbour
				+ ") -----RMSE ---> SCORE: " + evaluate(userSimRecBuilder, new RMSRecommenderEvaluator()));
		System.out.println("UncenteredCosineSimilarity ----- NearestNUserNeighborhood (" + nearestNeighbour
				+ ") -----MAE ---> SCORE: "
				+ evaluate(userSimRecBuilder, new AverageAbsoluteDifferenceRecommenderEvaluator()));
		
	}

	public void createUncenteredCosineWithThreshold(final double threshold) {
		RecommenderBuilder userSimRecBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {

				UserSimilarity userSimilarity = new UncenteredCosineSimilarity(model);
				UserNeighborhood neighborhood = new ThresholdUserNeighborhood(threshold, userSimilarity, model);

				Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
				return recommender;
			}
		};

		System.out.println("UncenteredCosineSimilarity ----- ThresholdUserNeighborhood (" + threshold
				+ ") -----RMSE ---> SCORE: " + evaluate(userSimRecBuilder, new RMSRecommenderEvaluator()));
		System.out.println(
				"UncenteredCosineSimilarity ----- ThresholdUserNeighborhood (" + threshold + ") -----MAE ---> SCORE: "
						+ evaluate(userSimRecBuilder, new AverageAbsoluteDifferenceRecommenderEvaluator()));
		
	}
}
