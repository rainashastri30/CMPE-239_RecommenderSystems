package com.recommender.collaberativeRecommender;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * Hello world!
 *
 */
public class UserBasedRecommender {
	public static void main(String[] args) {
		DataModel model;
		try {

			model = new FileDataModel(new File("/Users/rainashastri/Desktop/239/mahout-org-csv.csv"));

			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

			UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
			// UserNeighborhood neighborhood = new
			// ThresholdUserNeighborhood(0.7, similarity, model);

			GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			//long recommendedUsers[] = recommender.mostSimilarUserIDs(32, 5);
			List<RecommendedItem> recommendedItems = recommender.recommend(913, 5);

			for (RecommendedItem recommendation : recommendedItems) {
				System.out.println(recommendation);
			}

//			for (long recommendation : recommendedUsers) {
//				System.out.println(recommendation);
//			}

		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
			e.printStackTrace();
		} catch (TasteException e) {
			System.out.println("Taste Exception");
			e.printStackTrace();
		}

	}
}
