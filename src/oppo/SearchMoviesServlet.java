package oppo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class SearchMovieServlet
 */
@WebServlet("/searchMovies")
public class SearchMoviesServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String stitle = request.getParameter("title");
		// Here we are creating an ArrayList
		ArrayList<Movie> movies=new ArrayList<Movie>();
		/// Here we have to write query to fetch data from database
		Movie tmovie = null;
		try {
			String query = "select title,director,year,story,poster,language,mid  from tmovie_tbl where title=?";
			Class.forName(DBSettings.DRIVER);
			Connection conn = DriverManager.getConnection(DBSettings.URL, DBSettings.USER_NAME, DBSettings.PASSWORD);
			// No need to set any input
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, stitle);
			// we should use executeQuery to fetch data
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String title = rs.getString(1);
				String director = rs.getString(2);
				int year = rs.getInt(3);
				String story = rs.getString(4);
				byte[] poster = null;
				// InputStream is=rs.getBinaryStream(5);
				// IOUtils.readFully(is, poster);
				String language = rs.getString(6);
				int mid = rs.getInt(7);
				tmovie = new Movie(title, year + "", director, language, story);
				tmovie.setMid(mid);
				movies.add(tmovie);
			}

		} catch (Exception ex) {
			// below statement will print the error in details
			ex.printStackTrace();
		}
		response.setContentType("application/json");
		// GSON ,JACKSON MAPPER
		Gson gson = new Gson();
		String jsonString = gson.toJson(movies, ArrayList.class);
		response.getWriter().print(jsonString);
		// 2. Java object to JSON, and assign to a String

	}

}
