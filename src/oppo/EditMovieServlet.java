package oppo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import sun.misc.IOUtils;


//CRT + SHIFT+O
@WebServlet("/editMovieServlet")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class EditMovieServlet extends HttpServlet {

	//ArrayList is data structure which holds  other objects....it is present inside a package that is called java.util
	//Generics
	 //this is dynamic

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String title=request.getParameter("title");
			String mid=request.getParameter("mid");
			String year=request.getParameter("year");
			String director=request.getParameter("director");
			String language=request.getParameter("language");
			String story=request.getParameter("review");
			//String poster=request.getParameter("poster");
			Movie smovie=new Movie(title,year+"",director,language,story);
			smovie.setMid(Integer.parseInt(mid));
			MovieData.updateMovie(smovie);
			//here we are adding movie object inside request object using name =pdata
			response.setContentType("text/plain");
			response.getWriter().print("hey! data is updated successfully");
	}

}
