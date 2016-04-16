package com.recommender.collaberativeRecommender;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

public class UserBasedRecommender {

	/**
	 * This method fetches top 5 recommendations for the unseen movies
	 * 
	 * @param userId
	 */
	public void getNewMovieRecommendationsForUser(int userId) {

		DataModel model;
		try {
			List<Long> movieList = new ArrayList<Long>();
			model = new FileDataModel(new File("data/additional-users-preprocessed.csv"));

			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

			//select closest 5 neighbors 
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);

			GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			
			//Get top 5 recommendations
			List<RecommendedItem> recommendedItems = recommender.recommend(userId, 5);

			for (RecommendedItem recommendation : recommendedItems) {
				movieList.add(recommendation.getItemID());
			}
			
			//Get mmovie names for movie Ids
			mapMovieIdToName(movieList);

		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
			e.printStackTrace();
		} catch (TasteException e) {
			System.out.println("Taste Exception");
			e.printStackTrace();
		}

	}

	/**
	 * Returns list of movie Ids for the given movie names
	 * 
	 * @param movieList
	 */
	private void mapMovieIdToName(List<Long> movieList) {
		Connection connection;
		try {
			connection = DBConnection.loadDriver();

			for (Long movieId : movieList) {
				PreparedStatement pst = connection.prepareStatement("select * from Movies where movieID = ?");
				pst.setLong(1, movieId);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					System.out.println(rs.getString("name"));
				}
			}
		} catch (

		SQLException e)

		{
			System.out.println("A SQL Exception has occurred");
			e.printStackTrace();
		}
	}

}
