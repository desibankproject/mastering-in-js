package oppo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;

//CRT + SHIFT+O
@WebServlet("/posterServlet")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class UpdatePosterServlet extends HttpServlet {
	private static final long serialVersionUID = 7703926767168924473L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer mid=Integer.parseInt(request.getParameter("mid"));	
		//find the updated img from db with mid
		Blob posterImg=MovieData.findPosterImg(mid);
		//return the img
		response.setContentType("image/jpg");
		OutputStream os = response.getOutputStream();
		try {
			InputStream is=posterImg.getBinaryStream();
			IOUtils.copy(is,os);
			is.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		os.flush();
}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Integer mid=Integer.parseInt(request.getParameter("mid"));
			
			InputStream inputStream = null; // input stream of the upload file

			// obtains the upload file part in this multipart request
			Part filePart = request.getPart("posterFile");
			if (filePart != null) {
				// prints out some information for debugging
				System.out.println(filePart.getSize());
				System.out.println(filePart.getContentType());
			}
			
			// obtains input stream of the upload file
			inputStream = filePart.getInputStream();
			//update img with mid
			MovieData.updatePoster(inputStream,mid);
			//find the updated img from db with mid
			Blob posterImg=MovieData.findPosterImg(mid);
			//return the img
			response.setContentType("image/jpg");
			OutputStream os = response.getOutputStream();
			try {
				InputStream is=posterImg.getBinaryStream();
				IOUtils.copy(is,os);
				is.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			os.flush();
	}

}
