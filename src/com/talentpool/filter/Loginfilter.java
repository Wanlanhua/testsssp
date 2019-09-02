package com.talentpool.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName="Loginfilter",value= {"/*"},initParams= {
		@WebInitParam(name="nologin",value="login.html;register_step1.html;register_step2.html;register_step3.html;register_step4.html;registered;Login;.jpg;"
				+ ".png;.jpeg;.css;.js;jiaoyan;testA")
})
public class Loginfilter implements Filter{
	private FilterConfig config; 
	public void destroy() {
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		HttpSession session	=request.getSession();
		//设置编码器
		request.setCharacterEncoding("UTF-8");
		String uri=request.getRequestURI();
		String nologin=config.getInitParameter("nologin");
		if(nologin!=null)
		{
			String[] strarray=nologin.split(";");
			for (String string : strarray) {
				 if (string == null || "".equals(string)) {
	                    continue;
	                }
				if(uri.endsWith(string))
				{
					arg2.doFilter(arg0, arg1);
					return;
				}
			}
		}
		if(session.getAttribute("username")!=null )
		{
			arg2.doFilter(arg0, arg1);
		}else
		{
//			PrintWriter pw = response.getWriter();
//        	pw.println("<script type=\"text/javascript\">" + 
//        			"		window.top.location='"+request.getContextPath()+"/login.html'" + 
//        			"	</script>");
        	arg2.doFilter(arg0, arg1);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		config=arg0;
	}



}
