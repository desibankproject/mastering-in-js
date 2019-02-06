package oppo;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class MovieData {

	public static void deleteMovieByMid(int mid) {
		// Write Java code to insert data into the database
		// Step-1 >>>Write SQL query
		String query = "delete from movie_tbl  where mid=?";
		try {
			// Step-1 -- loading the driver
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movies_db", "root",
					"mysql@1234");
			// pstmt holds compiled query
			PreparedStatement pstmt = conn.prepareStatement(query);
			// setting data inside the query
			pstmt.setInt(1, mid);
			// Fire the query
			pstmt.executeUpdate();
		} catch (Exception supriya) {
			supriya.printStackTrace();
		}
	}

	public static void updateMovie(Movie movie) {
		// Write Java code to insert data into the database
		// Step-1 >>>Write SQL query
		String query = "update tmovie_tbl set title=?,director=?,year=?,story=?,language=? where mid=?";
		try {
			// Step-1 -- loading the driver
			Class.forName(DBSettings.DRIVER);
			Connection conn = DriverManager.getConnection(DBSettings.URL,DBSettings.USER_NAME,DBSettings.PASSWORD);
			// pstmt holds compiled query
			PreparedStatement pstmt = conn.prepareStatement(query);
			// setting data inside the query
			pstmt.setString(1, movie.getTitle());
			pstmt.setString(2, movie.getDirector());
			pstmt.setInt(3, Integer.parseInt(movie.getYear()));
			pstmt.setString(4, movie.getStory());
			pstmt.setString(5, movie.getLanguage());
			pstmt.setInt(6, movie.getMid());
			// Fire the query
			pstmt.executeUpdate();

		} catch (Exception supriya) {
			supriya.printStackTrace();
		}
	}

	public static void addMovie(Movie movie) {
		// Write Java code to insert data into the database
		// Step-1 >>>Write SQL query
		String query = "insert into tmovie_tbl(title,director,year,story,poster,language) values(?,?,?,?,?,?)";
		try {
			// Step-1 -- loading the driver
			Class.forName(DBSettings.DRIVER);
			Connection conn = DriverManager.getConnection(DBSettings.URL,DBSettings.USER_NAME,DBSettings.PASSWORD);
			// pstmt holds compiled query
			PreparedStatement pstmt = conn.prepareStatement(query);
			// setting data inside the query
			pstmt.setString(1, movie.getTitle());
			pstmt.setString(2, movie.getDirector());
			pstmt.setInt(3, Integer.parseInt(movie.getYear()));
			pstmt.setString(4, movie.getStory());
			pstmt.setBytes(5, movie.getPoster());
			pstmt.setString(6, movie.getLanguage());
			// Fire the query
			pstmt.executeUpdate();

		} catch (Exception supriya) {
			supriya.printStackTrace();
		}
	}

	static public ArrayList<Movie> loadMovieData() {
		// Here we are creating an ArrayList
		ArrayList<Movie> movies = new ArrayList<Movie>();
		/// Here we have to write query to fetch data from database
		try {
			String query = "select title,director,year,story,poster,language,mid  from tmovie_tbl";
			Class.forName(DBSettings.DRIVER);
			Connection conn = DriverManager.getConnection(DBSettings.URL,DBSettings.USER_NAME,DBSettings.PASSWORD);
			// No need to set any input
			PreparedStatement pstmt = conn.prepareStatement(query);
			// we should use executeQuery to fetch data
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String title = rs.getString(1);
				String director = rs.getString(2);
				int year = rs.getInt(3);
				String story = rs.getString(4);
				byte[] poster=null;
				 IOUtils.readFully(rs.getBlob(5).getBinaryStream(), poster);
				String language = rs.getString(6);
				int mid = rs.getInt(7);
				Movie tmovie = new Movie(title, year + "", director, language, story, poster);
				tmovie.setMid(mid);
				movies.add(tmovie);
			}

		} catch (Exception ex) {
			// below statement will print the error in details
			ex.printStackTrace();
		}

		return movies;
	}

	 public static void updatePoster(InputStream input, int mid) {
		try {
			// connects to the database
			Class.forName(DBSettings.DRIVER);
			Connection conn = DriverManager.getConnection(DBSettings.URL,DBSettings.USER_NAME,DBSettings.PASSWORD);

			// constructs SQL statement
			String sql = "update tmovie_tbl set poster=? where mid=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(2, mid);
			// fetches input stream of the upload file for the blob column
			if (input != null)
				// statement.setBinaryStream(2, inputStream, (int)filePart.getSize());
				statement.setBlob(1, input);

			int row = statement.executeUpdate();
			if (row > 0) {
				System.out.println("File uploaded and saved into database");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Blob  findPosterImg(Integer mid) {
		Blob  posterImg=null;
		try {
			// connects to the database
			Class.forName(DBSettings.DRIVER);
			Connection conn = DriverManager.getConnection(DBSettings.URL,DBSettings.USER_NAME,DBSettings.PASSWORD);

			// constructs SQL statement
			String sql = "select poster from tmovie_tbl where mid=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, mid);
			// fetches input stream of the upload file for the blob column

			ResultSet rs = statement.executeQuery();
			while(rs.next())
				posterImg=rs.getBlob(1);
			return posterImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posterImg;
	}
}
