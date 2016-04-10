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
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class Evaluator {
	public static void main(String args[]) {
		String recsFile = "/Users/rainashastri/Desktop/239/mahout-org-csv.csv";

		RecommenderBuilder userSimRecBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {
				
				UserSimilarity userSimilarity = new LogLikelihoodSimilarity(model);

			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, userSimilarity, model);
			//	UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, userSimilarity, model);

				Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
				return recommender;
			}
		};

		try {

			RandomUtils.useTestSeed();
			// evaluate method
			FileDataModel dataModel = new FileDataModel(new File(recsFile));

			 RecommenderEvaluator evaluator = new
			 AverageAbsoluteDifferenceRecommenderEvaluator();
			//RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();

			// for obtaining User Similarity Evaluation Score
			double userSimEvaluationScore = evaluator.evaluate(userSimRecBuilder, null, dataModel, 0.9, 1.0);
			System.out.println("Score: "+userSimEvaluationScore);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TasteException e) {
			e.printStackTrace();
		}
	}
}
