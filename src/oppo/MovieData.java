package oppo;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

public class MovieData {
	
	public static void deleteMovieByMid(int mid) {
		//Write Java code to insert data into the database
		//Step-1 >>>Write SQL query
		 String query="delete from movie_tbl  where mid=?";
		 try {
			   //Step-1 -- loading the driver
			   Class.forName("com.mysql.jdbc.Driver");
			   Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/movies_db","root","mysql@1234");
			   //pstmt holds compiled query
			   PreparedStatement pstmt=conn.prepareStatement(query);
			   //setting data inside the query
			   pstmt.setInt(1, mid);
			   //Fire the query
			   pstmt.executeUpdate();
		 }catch(Exception supriya){
			 supriya.printStackTrace();
		 }
	}
	
	public static void addMovie(Movie movie){
		//Write Java code to insert data into the database
		//Step-1 >>>Write SQL query
		 String query="insert into tmovie_tbl(title,director,year,story,poster,language) values(?,?,?,?,?,?)";
		 try {
			   //Step-1 -- loading the driver
			   Class.forName("com.mysql.jdbc.Driver");
			   	//Making connection
			   //connection url = jdbc:mysql://localhost:3306/movies_db 
			   //dbusername = root
			   //dbpassword = mysql@1234
			   Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/movies_db","root","mysql@1234");
			   //pstmt holds compiled query
			   PreparedStatement pstmt=conn.prepareStatement(query);
			   //setting data inside the query
			   pstmt.setString(1, movie.getTitle());
			   pstmt.setString(2, movie.getDirector());
			   pstmt.setInt(3,Integer.parseInt(movie.getYear()));
			   pstmt.setString(4, movie.getStory());
			   pstmt.setString(5, movie.getPoster());
			   pstmt.setString(6, movie.getLanguage());
			   //Fire the query
			   pstmt.executeUpdate();
			 
		 }catch(Exception supriya){
			 supriya.printStackTrace();
		 }
	}

	
	
	static public ArrayList<Movie> loadMovieData(){
		//Here we are creating an ArrayList
		ArrayList<Movie>  movies=new ArrayList<Movie>();
		///Here we have to write query to fetch data from database
		try {
					String query="select title,director,year,story,poster,language,mid  from movie_tbl";
				   Class.forName("com.mysql.jdbc.Driver");
				   Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/movies_db","root","mysql@1234");
				   //No need to set any input
				   PreparedStatement pstmt=conn.prepareStatement(query);
				   //we should use executeQuery to fetch data
				  ResultSet rs= pstmt.executeQuery();
				  while(rs.next()){
					  String title=rs.getString(1);
					  String director=rs.getString(2);
					  int year=rs.getInt(3);
					  String story=rs.getString(4);
					  String poster=rs.getString(5);
					  String language=rs.getString(6);
					  int mid=rs.getInt(7);
					  Movie tmovie=new Movie(title,year+"",director,language,story,poster);
					  tmovie.setMid(mid);
					  movies.add(tmovie);
				  }
				   
		}catch(Exception ex){
			//below statement will print the error in details
			ex.printStackTrace();
		}
		
		return movies;
	}

}
