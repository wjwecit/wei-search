package wei.web.wm.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wei.web.util.LuceneUtil;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexServlet() {
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
		}
		
		response.getWriter().write("total time elipse:"+(System.currentTimeMillis() - start));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
	}

	public static String getRootPath() {
		String classPath = IndexServlet.class.getClassLoader().getResource("/").getPath();
		String rootPath = "";
		// windowsÏÂ
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linuxÏÂ
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

}
