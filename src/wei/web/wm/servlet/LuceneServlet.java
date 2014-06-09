package wei.web.wm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wei.web.util.LuceneUtil;

/**
 * Servlet implementation class LuceneServlet
 */
public class LuceneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LuceneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long start = System.currentTimeMillis();
		String cmd=request.getParameter("cmd");
		LuceneUtil lu=new LuceneUtil();
		if(cmd==null){
			response.getWriter().write("total time elipse:"+(System.currentTimeMillis() - start));
			return;			
		}
		if(cmd.equalsIgnoreCase("q")){
			lu.search("id:100");
		}else if(cmd.equalsIgnoreCase("u")){
			LuceneUtil.addDoc();
		}else if(cmd.equalsIgnoreCase("a")){
			LuceneUtil.add(100);
		}else if(cmd.equalsIgnoreCase("d")){
			LuceneUtil.del(100);
		}
		
		response.getWriter().write("total time elipse:"+(System.currentTimeMillis() - start));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
