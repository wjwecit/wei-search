package wei.web.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
	
	public static String getString(HttpServletRequest request,String name){
		String res=request.getParameter(name);
		if(res==null||res.length()<1){
			return "";
		}
		return res.trim();
	}
	
	public static int getInt(HttpServletRequest request,String name){
		int res=0;
		String strRes=request.getParameter(name);
		if(strRes==null){
			return 0;
		}
		try {
			res=Integer.parseInt(strRes);
		} catch (Exception e) {
			res=0;
		}
		return res;
	}
	
	public static long getLong(HttpServletRequest request,String name){
		long res=0;
		String strRes=request.getParameter(name);
		if(strRes==null){
			return 0;
		}
		try {
			res=Long.parseLong(strRes);
		} catch (Exception e) {
			res=0;
		}
		return res;
	}
	
	

}
