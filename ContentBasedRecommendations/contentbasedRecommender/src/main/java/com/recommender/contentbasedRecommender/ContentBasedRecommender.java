package com.recommender.contentbasedRecommender;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class ContentBasedRecommender {
	public static List<String> userMovieList = new ArrayList<String>();

	public long[] getSimilarMovies(int movieId) {
		DataModel model;
		long similarMovies[] = new long[3];
		try {
			model = new FileDataModel(new File("data/content-based-mahout.csv"));
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
			GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			similarMovies = recommender.mostSimilarUserIDs(movieId, 10);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TasteException e) {
			e.printStackTrace();
		}
		return similarMovies;
	}

	/**
	 * This method invokes the main content based recommender model to recommend
	 * similar movies based on this top three rated movies passed.
	 * 
	 * @param userId
	 */
	public void getNewMovieRecommendationsForUser(int userId) {
		List<Long> recommendedList = new ArrayList<Long>();
		Map<Integer, String> userMovieRatings = getTopSimilarMoviesToRecommend(userId);
		// retrieve three movie ids
		for (int movieId : userMovieRatings.keySet()) {
			// get similar movies for each of the movies
			long similarMovies[] = getSimilarMovies(movieId);
			if (null != similarMovies) {
				for (long movie : similarMovies) {
					// logic to avoid adding duplicate similar movies .( As the
					// similar movies could overlap in list of similar movies
					// for each of the three movie ids passed)
					if (!recommendedList.contains(movie)) {
						recommendedList.add(movie);
					}
				}
			}

		}
		// get Movie Ids for movies already seen by the user.
		List<Integer> userWatchedmovieIDs = getMovieIdsForMovieNames(userMovieList);
		for (long id : userWatchedmovieIDs) {
			// remove movie from the recommendation list if the user has already
			// seen it.
			if (recommendedList.contains(id)) {
				recommendedList.remove(id);
			}
		}
		mapMovieIdToName(recommendedList);
	}

	/**
	 * This method fetch list of movie IDs for the list of movie names passed as input parameters.
	 * @param userMovieList
	 * @return
	 */
	private List<Integer> getMovieIdsForMovieNames(List<String> userMovieList) {
		List<Integer> movieIDList = new ArrayList<Integer>();
		Connection connection;
		try {
			connection = DBConnection.loadDriver();

			StringBuilder sb = new StringBuilder("\"" + userMovieList.get(0) + "\"");
			for (int i = 1; i < userMovieList.size(); i++)
				sb.append(",\"").append(userMovieList.get(i)).append("\"");

			String sql = "select movieID from Movies where name in (" + sb.toString() + ")";
			PreparedStatement pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				movieIDList.add(rs.getInt("movieID"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return movieIDList;
	}

	/**
	 * Fetch movie name for movie ids.
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
			e.printStackTrace();
		}
	}
	/**
	 * This method performs the below steps - 
	 * a. Fetch list of user rated movies.
	 * b. Computes avg of the user's ratings for all movies.
	 * c. Select movies whose rating is above or equal to avg.
	 * d. Select any random 3 movies from the list in c.
	 * e.return the random 3 movies to find list of similar movies to each of the three movies
	 * @param userId
	 * @return
	 */

	private Map<Integer, String> getTopSimilarMoviesToRecommend(int userId) {
		Map<Integer, String> movieNamesMap = new HashMap<Integer, String>();
		try {
			Map<String, Integer> userMovieRatings = new HashMap<String, Integer>();
			Connection conn;
			conn = DBConnection.loadDriver();
			PreparedStatement pst = conn.prepareStatement("select * from UserMovieRatings where USERS = ?");
			pst.setInt(1, userId);
			ResultSet rs = pst.executeQuery();
			int sum = 0, count = 0;
			while (rs.next()) {
				if (rs.getInt("Star Wars") > 0) {
					userMovieRatings.put("Star Wars", rs.getInt("Star Wars"));
					++count;
					sum += rs.getInt("Star Wars");
					userMovieList.add("Star Wars");
				}
				if (rs.getInt("Alien") > 0) {
					userMovieRatings.put("Alien", rs.getInt("Alien"));
					++count;
					sum += rs.getInt("Alien");
					userMovieList.add("Alien");
				}
				if (rs.getInt("Spotlight") > 0) {
					userMovieRatings.put("Spotlight", rs.getInt("Spotlight"));
					++count;
					sum += rs.getInt("Spotlight");
					userMovieList.add("Spotlight");
				}
				if (rs.getInt("Rocky") > 0) {
					userMovieRatings.put("Rocky", rs.getInt("Rocky"));
					++count;
					sum += rs.getInt("Rocky");
					userMovieList.add("Rocky");
				}
				if (rs.getInt("Mad Max: Fury Road") > 0) {
					userMovieRatings.put("Mad Max: Fury Road", rs.getInt("Mad Max: Fury Road"));
					++count;
					sum += rs.getInt("Mad Max: Fury Road");
					userMovieList.add("Mad Max: Fury Road");
				}
				if (rs.getInt("Kung Fu Panda 3") > 0) {
					userMovieRatings.put("Kung Fu Panda 3", rs.getInt("Kung Fu Panda 3"));
					++count;
					sum += rs.getInt("Kung Fu Panda 3");
					userMovieList.add("Kung Fu Panda 3");

				}
				if (rs.getInt("Despicable Me") > 0) {
					userMovieRatings.put("Despicable Me", rs.getInt("Despicable Me"));
					++count;
					sum += rs.getInt("Despicable Me");
					userMovieList.add("Despicable Me");
				}
				if (rs.getInt("Big Hero 6") > 0) {
					userMovieRatings.put("Big Hero 6", rs.getInt("Big Hero 6"));
					++count;
					sum += rs.getInt("Big Hero 6");
					userMovieList.add("Big Hero 6");
				}
				if (rs.getInt("The hungover") > 0) {
					userMovieRatings.put("The hungover", rs.getInt("The hungover"));
					++count;
					sum += rs.getInt("The hungover");
					userMovieList.add("The hungover");
				}
				if (rs.getInt("Inside out") > 0) {
					userMovieRatings.put("Inside out", rs.getInt("Inside out"));
					++count;
					sum += rs.getInt("Inside out");
					userMovieList.add("Inside out");
				}
				if (rs.getInt("The big short") > 0) {
					userMovieRatings.put("The big short", rs.getInt("The big short"));
					++count;
					sum += rs.getInt("The big short");
					userMovieList.add("The big short");
				}
				if (rs.getInt("Almost famous") > 0) {
					userMovieRatings.put("Almost famous", rs.getInt("Almost famous"));
					++count;
					sum += rs.getInt("Almost famous");
					userMovieList.add("Almost famous");
				}
				if (rs.getInt("The hunger games") > 0) {
					userMovieRatings.put("The hunger games", rs.getInt("The hunger games"));
					++count;
					sum += rs.getInt("The hunger games");
					userMovieList.add("The hunger games");
				}
				if (rs.getInt("Pulp Fiction") > 0) {
					userMovieRatings.put("Pulp Fiction", rs.getInt("Pulp Fiction"));
					++count;
					sum += rs.getInt("Pulp Fiction");
					userMovieList.add("Pulp Fiction");
				}
				if (rs.getInt("Inglourious Basterds") > 0) {
					userMovieRatings.put("Inglourious Basterds", rs.getInt("Inglourious Basterds"));
					++count;
					sum += rs.getInt("Inglourious Basterds");
					userMovieList.add("Inglourious Basterds");
				}
				if (rs.getInt("The usual suspects") > 0) {
					userMovieRatings.put("The usual suspects", rs.getInt("The usual suspects"));
					++count;
					sum += rs.getInt("The usual suspects");
					userMovieList.add("The usual suspects");
				}
				if (rs.getInt("Mamma mia") > 0) {
					userMovieRatings.put("Mamma mia", rs.getInt("Mamma mia"));
					++count;
					sum += rs.getInt("Mamma mia");
					userMovieList.add("Mamma mia");
				}
				if (rs.getInt("Frozen") > 0) {
					userMovieRatings.put("Frozen", rs.getInt("Frozen"));
					++count;
					sum += rs.getInt("Frozen");
				}
				if (rs.getInt("Legally blonde") > 0) {
					userMovieRatings.put("Legally blonde", rs.getInt("Legally blonde"));
					++count;
					sum += rs.getInt("Legally blonde");
					userMovieList.add("Legally blonde");
				}
				if (rs.getInt("Silver Linings") > 0) {
					userMovieRatings.put("Silver Linings", rs.getInt("Silver Linings"));
					++count;
					sum += rs.getInt("Silver Linings");
					userMovieList.add("Silver Linings");
				}
				if (rs.getInt("Twilight") > 0) {
					userMovieRatings.put("Twilight", rs.getInt("Twilight"));
					++count;
					sum += rs.getInt("Twilight");
					userMovieList.add("Twilight");
				}
				if (rs.getInt("Bridget Jones Diary") > 0) {
					userMovieRatings.put("Bridget Jones Diary", rs.getInt("Bridget Jones Diary"));
					++count;
					sum += rs.getInt("Bridget Jones Diary");
				}
				if (rs.getInt("Gone girl") > 0) {
					userMovieRatings.put("Gone girl", rs.getInt("Gone girl"));
					++count;
					sum += rs.getInt("Gone girl");
					userMovieList.add("Gone girl");
				}
				if (rs.getInt("The matrix") > 0) {
					userMovieRatings.put("The matrix", rs.getInt("The matrix"));
					++count;
					sum += rs.getInt("The matrix");
					userMovieList.add("The matrix");
				}
				if (rs.getInt("2001: A space Odyssey") > 0) {
					userMovieRatings.put("2001: A space Odyssey", rs.getInt("2001: A space Odyssey"));
					++count;
					sum += rs.getInt("2001: A space Odyssey");
					userMovieList.add("2001: A space Odyssey");
				}
				if (rs.getInt("The godfather") > 0) {
					userMovieRatings.put("The godfather", rs.getInt("The godfather"));
					++count;
					sum += rs.getInt("The godfather");
				}
				if (rs.getInt("Pretty woman") > 0) {
					userMovieRatings.put("Pretty woman", rs.getInt("Pretty woman"));
					++count;
					sum += rs.getInt("Pretty woman");
					userMovieList.add("Pretty woman");
				}
				if (rs.getInt("Room") > 0) {
					userMovieRatings.put("Room", rs.getInt("Room"));
					++count;
					sum += rs.getInt("Room");
					userMovieList.add("Room");
				}
				if (rs.getInt("Spy") > 0) {
					userMovieRatings.put("Spy", rs.getInt("Spy"));
					++count;
					sum += rs.getInt("Spy");
					userMovieList.add("Spy");
				}
				if (rs.getInt("The Intern") > 0) {
					userMovieRatings.put("The Intern", rs.getInt("The Intern"));
					++count;
					sum += rs.getInt("The Intern");
					userMovieList.add("The Intern");
				}
				if (rs.getInt("Zootopia") > 0) {
					userMovieRatings.put("Zootopia", rs.getInt("Zootopia"));
					++count;
					sum += rs.getInt("Zootopia");
					userMovieList.add("Zootopia");
				}
				if (rs.getInt("MI") > 0) {
					userMovieRatings.put("MI", rs.getInt("MI"));
					++count;
					sum += rs.getInt("MI");
					userMovieList.add("MI");
				}
				if (rs.getInt("The martian") > 0) {
					userMovieRatings.put("The martian", rs.getInt("The martian"));
					++count;
					sum += rs.getInt("The martian");
					userMovieList.add("The martian");
				}
				if (rs.getInt("The Danish Girl") > 0) {
					userMovieRatings.put("The Danish Girl", rs.getInt("The Danish Girl"));
					++count;
					sum += rs.getInt("The Danish Girl");
					userMovieList.add("The Danish Girl");
				}
				if (rs.getInt("X-men") > 0) {
					userMovieRatings.put("X-men", rs.getInt("X-men"));
					++count;
					sum += rs.getInt("X-men");
					userMovieList.add("X-men");
				}
				if (rs.getInt("Captain America") > 0) {
					userMovieRatings.put("Captain America", rs.getInt("Captain America"));
					++count;
					sum += rs.getInt("Captain America");
					userMovieList.add("Captain America");
				}
				if (rs.getInt("Focus") > 0) {
					userMovieRatings.put("Focus", rs.getInt("Focus"));
					++count;
					sum += rs.getInt("Focus");
					userMovieList.add("Focus");
				}
			}

			// compute average
			double avg = ((double) sum / count);

			// remove movies which user has rated less than his avg ratings
			userMovieRatings.entrySet().removeIf(entry -> entry.getValue() < avg);

			// randomly select 3 movies from the filtered list above
			Random random = new Random();
			List<String> randomMovieList = new ArrayList<String>();
			Object moviesArr[] = userMovieRatings.keySet().toArray();
			Object key = moviesArr[new Random().nextInt(moviesArr.length)];
			if (null != key) {
				randomMovieList.add(key.toString());
			}

			key = moviesArr[new Random().nextInt(moviesArr.length)];
			if (null != key) {
				randomMovieList.add(key.toString());
			}

			key = moviesArr[new Random().nextInt(moviesArr.length)];
			if (null != key) {
				randomMovieList.add(key.toString());
			}

			// fetch movie Ids for each movie name
			for (String movie : randomMovieList) {
				pst = conn.prepareStatement("select * from Movies where name = ?");
				pst.setString(1, movie);
				rs = pst.executeQuery();
				while (rs.next()) {
					movieNamesMap.put(rs.getInt("movieID"), movie);
				}
			}

		} catch (

		SQLException e)

		{
			e.printStackTrace();
			System.out.println("A SQL exception has occurred");
		}
		return movieNamesMap;
	}
}
