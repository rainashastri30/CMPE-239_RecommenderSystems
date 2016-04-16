package com.recommender.contentbasedRecommender;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConvertToCSV {

	public static final String QUERY = "select * from content;";
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/testOne";
	static final String FILE_NAME = "data/content-based-dataset.csv";
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
				m = new Model(rs.getInt("ID"), 10, rs.getString("G1"));
				ratinglist.add(m);
				m = new Model(rs.getInt("ID"), 20, rs.getString("G2"));
				ratinglist.add(m);
				m = new Model(rs.getInt("ID"), 30, rs.getString("G3"));
				ratinglist.add(m);
				m = new Model(rs.getInt("ID"), 40, rs.getString("G4"));
				ratinglist.add(m);
				m = new Model(rs.getInt("ID"), 50, rs.getString("G5"));
				ratinglist.add(m);
				m = new Model(rs.getInt("ID"), 60, rs.getString("G6"));
				ratinglist.add(m);
				m = new Model(rs.getInt("ID"), 70, rs.getString("G7"));
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

			// } finally {
			// try {
			//// fileWriter.flush();
			//// fileWriter.close();
			// } catch (IOException e) {
			//
			// e.printStackTrace();
			// }
			//
			// }
		}

	}

}
