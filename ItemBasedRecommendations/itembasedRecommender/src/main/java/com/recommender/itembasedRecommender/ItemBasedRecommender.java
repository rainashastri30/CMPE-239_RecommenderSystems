package com.recommender.itembasedRecommender;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemBasedRecommender {
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
			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);

			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

			List<RecommendedItem> recommendations = recommender.recommend(userId, 5);
			for (RecommendedItem recommendation : recommendations) {
				movieList.add(recommendation.getItemID());
			}
			// Get movie names for movie Ids
			mapMovieIdToName(movieList);

		} catch (Exception e) {

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
