package wasdev.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonPrimitive;
//import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
//import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;
//import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;
//import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassifier;
/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/SimpleServlet")
@MultipartConfig
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().print("Where is my Food?");
        System.out.println("Request:"+request.toString());
        System.out.println("Response:"+response.toString());
    }
    
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
    	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    	File uploadedFile;
    	final PrintWriter writer = response.getWriter();
    	
    	if (isMultipart) {
    	    FileItemFactory factory = new DiskFileItemFactory();
    	    ServletFileUpload upload = new ServletFileUpload(factory);

    	    try {
    	        List items = upload.parseRequest(request);
    	        Iterator iterator = items.iterator();
    	        while (iterator.hasNext()) {
    	            FileItem item = (FileItem) iterator.next();
    	            if (!item.isFormField()) {
    	                String fileName = item.getName();

    	                String root = getServletContext().getRealPath("/");
    	                File path = new File(root + "/fileuploads");
    	                if (!path.exists()) {
    	                    boolean status = path.mkdirs();
    	                }

    	                uploadedFile = new File(path + "/" + fileName);
    	                item.write(uploadedFile);
    	                
    	                String versionDate="2016-05-20";
    	                
    	                String apiKey="4481d32c3d43ae96289fd5eee0722504e927935c";
    	                
    	                VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
    	                service.setApiKey(apiKey);
    	                
    	                ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
    	                    .images(uploadedFile)//new File("src/test/resources/visual_recognition/car.png"))
    	                    .build();
    	                VisualClassification result = service.classify(options).execute();
    	                writer.println(result);
    	                
    	            }
    	        }
    	    } catch (Exception e) {
    	    	writer.println("You either did not specify a file to upload or are "
                        + "trying to upload a file to a protected or nonexistent "
                        + "location.");
                writer.println("<br/> ERROR: " + e.getMessage());
    	    }
    	}
    	
    }

    //protected void doPost1(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
    	
    	//response.setContentType("text/html;charset=UTF-8");

        // Create path components to save the file
    /*    final String path = "/path/to";
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(path + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            writer.println("New file " + fileName + " created at " + path);
        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            writer.println("<br/> ERROR: " + fne.getMessage());

        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    	
    	*/
    	
//    	final Part filePart = request.getPart("file");
//        final String fileName = getFileName(filePart);
//        String filePath=getServletContext().getContextPath()+ File.separator+fileName;
//        File f=new File(filePath);
        
        
        //InputStream fileContent= filePart.getInputStream();
//        final PrintWriter writer = response.getWriter();
//        writer.println(filePath);
        
        
        //writer.println("Classify an image");
   /*     ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
            .images(f)//new File("src/test/resources/visual_recognition/car.png"))
            .build();
        VisualClassification result = service.classify(options).execute();
        writer.println(result);
     */   
//        curl -X POST -F "images_file=@prez.jpg" "https://gateway-a.watsonplatform.net/visual-recognition/api/v3/detect_faces?api_key="+apiKey+"&version="+versionDate;
//        String serviceURL="https://gateway-a.watsonplatform.net/visual-recognition/api/v3/classify";
//        URL myURL = new URL(serviceURL);
//        HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();
//        myURLConnection.setRequestMethod("POST");
//        myURLConnection.setRequestProperty("X-Parse-Application-Id", "");
//        myURLConnection.setRequestProperty("X-Parse-REST-API-Key", "");
//        myURLConnection.setRequestProperty("Content-Type", "application/json");
//        myURLConnection.setUseCaches(false);
//        myURLConnection.setDoInput(true);
//        myURLConnection.setDoOutput(true);
//        myURLConnection.connect();
        
//    	RequestDispatcher view = request.getRequestDispatcher("index2.html");
//        view.forward(request, response); 
    	
    	
    	// Check that we have a file upload request
//	    	File im1 = chooser.getSelectedFile();    
//	    	BufferedImage buff = ImageIO.read(im1);
//	
//	    	if (buff != null) {
//	    	    System.out.println(buff.getWidth() + " " + buff.getHeight());
//	    	    System.out.println(buff.getRGB(0, 0));
//	    	}
//       }
}
