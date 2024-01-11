import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/index")
public class index extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public index() {
        super();
        // TODO Auto-generated constructor stub
        Server server = new Server();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        String keyword = request.getParameter("keyword");
        Algorithm algorithm = new Algorithm();
        System.out.println(keyword);
        System.out.println(keyword);
        try {
            //Thread.sleep(1000);
            String result = algorithm.getResult(keyword);   
            PrintWriter out = response.getWriter();
            System.out.println(result);
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error occurred");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	doGet(request, response);
    }
}
