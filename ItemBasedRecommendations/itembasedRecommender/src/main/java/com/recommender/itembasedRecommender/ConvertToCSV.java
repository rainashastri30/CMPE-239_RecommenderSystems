package com.recommender.itembasedRecommender;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConvertToCSV {
	// public static final String QUERY = "select * from RatingsNewUsersORG";
	public static final String QUERY = "select * from RatingsPreProcessed";
	// public static final String QUERY = "select * from RatingsORG";
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/testOne";
	static final String FILE_NAME = "data/test-preprocessed.csv";
	// static final String FILE_NAME = "data/mahout-org-csv.csv";
	public static final String COMMA_DELIMITER = ",";
	public static final String NEW_LINE_SEPARATOR = "\n";

	static final String USER = "root";
	static final String PASS = "";

	public static void main(String[] args) {
		List<Model> ratinglist;
		Connection conn = null;
		Statement stmt = null;
		FileWriter fileWriter = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY);
			fileWriter = new FileWriter(FILE_NAME);
			while (rs.next()) {
				Model m = null;
				ratinglist = new ArrayList<Model>();
				m = new Model(rs.getInt("USERS"), 1, rs.getString("Star Wars"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 2, rs.getString("Spotlight"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 3, rs.getString("Rocky"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 4, rs.getString("Mad Max: Fury Road"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 5, rs.getString("Kung Fu Panda 3"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 6, rs.getString("Despicable Me"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 7, rs.getString("Big Hero 6"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 8, rs.getString("The hungover"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 9, rs.getString("Inside out"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 10, rs.getString("The big short"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 11, rs.getString("Almost famous"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 12, rs.getString("The hunger games"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 13, rs.getString("Pulp Fiction"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 14, rs.getString("Inglourious Basterds"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 15, rs.getString("The usual suspects"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 16, rs.getString("Mamma mia"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 17, rs.getString("Frozen"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 18, rs.getString("Legally blonde"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 19, rs.getString("Silver Linings"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 20, rs.getString("Twilight"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 21, rs.getString("Bridget Jones Diary"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 22, rs.getString("Gone girl"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 23, rs.getString("The matrix"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 24, rs.getString("Alien"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 25, rs.getString("2001: A space Odyssey"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 26, rs.getString("The godfather"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 27, rs.getString("Pretty woman"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 28, rs.getString("Room"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 29, rs.getString("Spy"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 30, rs.getString("The Intern"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 31, rs.getString("Zootopia"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 32, rs.getString("MI"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 33, rs.getString("The martian"));
				ratinglist.add(m);

				m = new Model(rs.getInt("USERS"), 34, rs.getString("The Danish Girl"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 35, rs.getString("X-men"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 36, rs.getString("Captain America"));
				ratinglist.add(m);
				m = new Model(rs.getInt("USERS"), 37, rs.getString("Focus"));
				ratinglist.add(m);

				writeToCSV(ratinglist, FILE_NAME, fileWriter);

			}
		} catch (Exception e) {
			System.out.println("EXCEPTION");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	}

	private static void writeToCSV(List<Model> modelsList, String fileName, FileWriter fileWriter) {

		try {

			for (Model model : modelsList) {

				if (!model.getPreferences().isEmpty()) {
					fileWriter.append(String.valueOf(model.getUserId()));

					fileWriter.append(COMMA_DELIMITER);

					fileWriter.append(String.valueOf((model.getItemid())));

					fileWriter.append(COMMA_DELIMITER);

					fileWriter.append(String.valueOf(model.getPreferences()));

					fileWriter.append(NEW_LINE_SEPARATOR);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();

		}

	}
}
