package com.recommender.itembasedRecommender;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

	public class ItemBasedRecommenderClient {

		public static void main(String[] args) {
			ItemBasedRecommender recommender = new ItemBasedRecommender();
			System.out.println("************** WELCOME to Movies4u*********************");
			System.out.println("Enter the user Id to login in: ");

			// Take user input for user Id
			int userId = new Scanner(System.in).nextInt();

			// check if user is valid
			if (checkIfUserIsValid(userId)) {
				System.out.println("User " + userId + " logged in.");
				// fetch list of movies that the user has already watched.
				Map<String, Integer> userMovieRatings = getCurrentUserMoviesAndRatings(userId);
				System.out.println("******************** Movies you watched so far ***********************");
				for (String movie : userMovieRatings.keySet()) {
					if (userMovieRatings.get(movie) != 0) {
						System.out.println(movie + " ====>  Rating: " + userMovieRatings.get(movie));
					}
				}
				System.out.println("******************** Like other users you may also like   ***********************\n");
				// Recommend unseen movies to logged in user using item based
				// collaberative filtering.
				recommender.getNewMovieRecommendationsForUser(userId);
			} else {
				System.out.println("Invalid user");
			}

		}

		/**
		 * This method fetches the movies watched by the users along with their
		 * ratings.
		 * 
		 * @param userId
		 * @return
		 */

		private static Map<String, Integer> getCurrentUserMoviesAndRatings(int userId) {
			Map<String, Integer> userMovieRatings = new HashMap<String, Integer>();
			try {
				Connection connection = DBConnection.loadDriver();
				PreparedStatement pst = connection.prepareStatement("select * from UserMovieRatings where USERS = ?");
				pst.setInt(1, userId);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					userMovieRatings.put("Star Wars", rs.getInt("Star Wars"));
					userMovieRatings.put("Alien", rs.getInt("Alien"));
					userMovieRatings.put("Spotlight", rs.getInt("Spotlight"));
					userMovieRatings.put("Rocky", rs.getInt("Rocky"));
					userMovieRatings.put("Mad Max: Fury Road", rs.getInt("Mad Max: Fury Road"));
					userMovieRatings.put("Kung Fu Panda 3", rs.getInt("Kung Fu Panda 3"));
					userMovieRatings.put("Despicable Me", rs.getInt("Despicable Me"));
					userMovieRatings.put("Big Hero 6", rs.getInt("Big Hero 6"));
					userMovieRatings.put("The hungover", rs.getInt("The hungover"));
					userMovieRatings.put("Inside out", rs.getInt("Inside out"));
					userMovieRatings.put("The big short", rs.getInt("The big short"));
					userMovieRatings.put("Almost famous", rs.getInt("Almost famous"));
					userMovieRatings.put("The hunger games", rs.getInt("The hunger games"));
					userMovieRatings.put("Pulp Fiction", rs.getInt("Pulp Fiction"));
					userMovieRatings.put("Inglourious Basterds", rs.getInt("Inglourious Basterds"));
					userMovieRatings.put("The usual suspects", rs.getInt("The usual suspects"));
					userMovieRatings.put("Mamma mia", rs.getInt("Mamma mia"));
					userMovieRatings.put("Frozen", rs.getInt("Frozen"));
					userMovieRatings.put("Legally blonde", rs.getInt("Legally blonde"));
					userMovieRatings.put("Silver Linings", rs.getInt("Silver Linings"));
					userMovieRatings.put("Twilight", rs.getInt("Twilight"));
					userMovieRatings.put("Bridget Jones Diary", rs.getInt("Bridget Jones Diary"));
					userMovieRatings.put("Gone girl", rs.getInt("Gone girl"));
					userMovieRatings.put("The matrix", rs.getInt("The matrix"));
					userMovieRatings.put("2001: A space Odyssey", rs.getInt("2001: A space Odyssey"));
					userMovieRatings.put("The godfather", rs.getInt("The godfather"));
					userMovieRatings.put("Pretty woman", rs.getInt("Pretty woman"));
					userMovieRatings.put("Room", rs.getInt("Room"));
					userMovieRatings.put("Spy", rs.getInt("Spy"));
					userMovieRatings.put("The Intern", rs.getInt("The Intern"));
					userMovieRatings.put("Zootopia", rs.getInt("Zootopia"));
					userMovieRatings.put("Mission Impossible", rs.getInt("Mission Impossible"));
					userMovieRatings.put("The martian", rs.getInt("The martian"));
					userMovieRatings.put("The Danish Girl", rs.getInt("The Danish Girl"));
					userMovieRatings.put("X-men", rs.getInt("X-men"));
					userMovieRatings.put("Captain America", rs.getInt("Captain America"));
					userMovieRatings.put("Focus", rs.getInt("Focus"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return userMovieRatings;
		}

		/**
		 * This method check if the userId entered is that of a valid user
		 * 
		 * @param userId
		 * @return
		 */
		private static boolean checkIfUserIsValid(int userId) {

			try {
				
				Connection connection = DBConnection.loadDriver();
				PreparedStatement pst = connection.prepareStatement("select * from Viewers where userId = ?");
				pst.setInt(1, userId);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					int user = rs.getInt("userId");
					if (user == userId) {
						return true;
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}

	}

