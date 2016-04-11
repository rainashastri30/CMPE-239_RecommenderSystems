package com.recommender.collaberativeRecommender;
import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;


public class ItemBasedRecommender {
	public static void main(String arg[]) {
		DataModel model;
		try {
			model = new FileDataModel(new File("data/movie.csv"));
			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);;
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity); 
					
//			for (LongPrimitiveIterator iterator = model.getItemIDs(); iterator.hasNext();) {
//				Long itemId = iterator.nextLong();
//				List<RecommendedItem> recommendations = recommender.mostSimilarItems(32, 5);
//				for (RecommendedItem recommendation : recommendations) {
//					System.out.println(recommendation);
//				}
//			}
//			
			List<RecommendedItem> recommendations = recommender.recommend(913, 5);
			for (RecommendedItem recommendation : recommendations) {
				System.out.println(recommendation);
			}

		} catch (Exception e) {

		}
	}

}
