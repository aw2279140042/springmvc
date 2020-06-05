package com.springmvc.common;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONObject;


public class BaseController {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected Log log = LogFactory.getLog(BaseController.class);
	
	// 1.放置在方法的形参上：表示引用Model中的数据;2.放置在方法上面：表示请求该类的每个Action前都会首先执行它，也可以将一些准备数据的操作放置在该方法里面
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	protected void writeDate_xml(String data){
		PrintWriter out = null;
		try {
			//设置编码和返回格式
			response.setContentType("text/xml;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			out.print(data);
			out.flush();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if(out!=null) out.close();
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected void writeJSON() throws Exception{
		writeJSON(new HashMap());
	}
	
	protected void writeJSON(Object object) throws Exception{
		JSONObject json = new JSONObject();
		json.put("code", "success");
		json.put("msg", "");
		json.put("data", object);
		json.put("time", new Date().getTime());
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/plain;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
	}
	
	protected void writeError(String object) throws Exception{
		JSONObject json = new JSONObject();
		json.put("code", "error");
		json.put("msg", object);
		json.put("data", "{}");
		json.put("time", new Date().getTime());
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/plain;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 
     * 获取操作系统,浏览器及浏览器版本信息
     * @param request
     * @return
     */
    public static String getOsAndBrowserInfo(HttpServletRequest request){
        String  browserDetails  =   request.getHeader("User-Agent");
        String  userAgent       =   browserDetails;
        if(userAgent == null)return " --- ";
        String  user            =   userAgent.toLowerCase();

        String os = "";
        String browser = "";

        //=================OS Info=======================
        if (userAgent.toLowerCase().indexOf("windows") >= 0 )
        {
            os = "Windows";
        } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
        {
            os = "Mac";
        } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
        {
            os = "Unix";
        } else if(userAgent.toLowerCase().indexOf("android") >= 0)
        {
            os = "Android";
        } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
        {
            os = "IPhone";
        }else{
            os = "UnKnown, More-Info: "+userAgent;
        }
        //===============Browser===========================
	    try{
	        if (user.contains("edge"))
	        {
	            browser=(userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
	        } else if (user.contains("msie"))
	        {
	            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
	            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
	        } else if (user.contains("safari") && user.contains("version"))
	        {
	            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
	                    + "-" +(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
	        } else if ( user.contains("opr") || user.contains("opera"))
	        {
	            if(user.contains("opera")){
	                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
	                        +"-"+(((userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/").length >=1)?(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1]:"");
	            }else if(user.contains("opr")){
	                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
	                        .replace("OPR", "Opera");
	            }
	
	        } else if (user.contains("chrome"))
	        {
	            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
	        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  ||
	                (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) ||
	                (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
	        {
	            browser = "Netscape-?";
	
	        } else if (user.contains("firefox"))
	        {
	            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
	        } else if(user.contains("rv"))
	        {
	            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
	            browser="IE" + IEVersion.substring(0,IEVersion.length() - 1);
	        } else
	        {
	            browser = "UnKnown, More-Info: "+userAgent;
	        }
	    }catch(Exception e){
	    	browser = "[Execption]UnKnown, More-Info: "+userAgent;
	    }
        return os +" --- "+ browser ;
    }
    
    public String getBasePath(){
    	String path = request.getContextPath();
    	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    	return basePath;
    }
    
    public String getCookie(String key){
    	String val = null;
    	Cookie[] cookies = request.getCookies();
    	if(cookies == null)	return null;
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals(key)){
    			val =  cookie.getValue();
    			break;
    		}	
    	}
    	return val;
    }

	/**
	 * 获取 request
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * 设置 request
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取 response
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * 设置 response
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 获取 session
	 * @return the session
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * 设置 session
	 * @param session the session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}
    
    
}
