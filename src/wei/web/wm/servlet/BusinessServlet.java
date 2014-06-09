package wei.web.wm.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wei.db.common.DbTemplate;
import wei.db.common.Session;
import wei.db.common.TransactionExecutor;
import wei.web.mvc.model.AreaChina;
import wei.web.util.RequestUtils;

import com.google.gson.Gson;

/**
 * Servlet implementation class BusinessServlet
 */
public class BusinessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbTemplate template;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BusinessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		template = new DbTemplate();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		String action=RequestUtils.getString(request, "action");
		final String did=RequestUtils.getString(request, "did");
		
		if(did.length()<1){
			return;
		}
		if(action.equalsIgnoreCase("q")){
			Object obj=null;
			//Pos obj=template.getBean(Pos.class, "select * from pos where did=? order by ptime desc limit 1",new Object[]{did});
			//AreaChina obj=template.getBean(AreaChina.class, "select * from areachina where areaCode=? limit 1",new Object[]{did});
			
			Session session=new Session();
			session.beginTransaction();
			obj=session.getBean(AreaChina.class, "select * from areachina where areaCode=? limit 1",new Object[]{did});
			System.out.println(obj);
			session.endTransaction();
			
			if(obj==null)return;
			String res=new Gson().toJson(obj);
			response.getWriter().write(res);
			response.flushBuffer();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
