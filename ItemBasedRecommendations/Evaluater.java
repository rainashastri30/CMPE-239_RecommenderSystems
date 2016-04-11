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
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.common.RandomUtils;

public class Evaluater {

	public static void main(String[] args) {
		String recsFile = "data/movie.csv";
		// for Recommendation evaluations
		RecommenderBuilder itemSimRecBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {
				// The Similarity algorithms used in your recommender
				
				ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(model);

				/*
				 * The Neighborhood algorithms used in your recommender not
				 * required if you are evaluating your item based
				 * recommendations
				 */

				

				// Recommender used in your real time implementation
				Recommender recommender = new GenericItemBasedRecommender(model, itemSimilarity);
				return recommender;
			}
		};

		try {

			// Use this only if the code is for unit tests and other examples to
			// guarantee repeated results
			RandomUtils.useTestSeed();

			// Creating a data model to be passed on to RecommenderEvaluator -
			// evaluate method
			FileDataModel dataModel = new FileDataModel(new File(recsFile));

			/*
			 * Creating an RecommenderEvaluator to get the evaluation done you
			 * can use AverageAbsoluteDifferenceRecommenderEvaluator() as well
			 */
			RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
			// RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
			RecommenderIRStatsEvaluator ir = new GenericRecommenderIRStatsEvaluator();

			// for obtaining Item Similarity Evaluation Score
			double itemSimEvaluationScore = evaluator.evaluate(itemSimRecBuilder, null, dataModel, 0.9, 1.0);
//			IRStatistics stats = ir.evaluate(userSimRecBuilder, null, dataModel, null,5,
//					GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
//			//System.out.println("User Similarity Evaluation score : " + stats.getPrecision()+" "+stats.getRecall()+" "+stats.getF1Measure());
			System.out.println("Item Similarity Score: "+itemSimEvaluationScore);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	

}

