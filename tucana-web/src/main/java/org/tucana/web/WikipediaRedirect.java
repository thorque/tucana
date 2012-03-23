package org.tucana.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class WikipediaRedirect
 */
public class WikipediaRedirect implements Filter {

    /**
     * Default constructor. 
     */
    public WikipediaRedirect() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		if (request instanceof HttpServletRequest){
			HttpServletRequest httpreq = (HttpServletRequest) request;
			String redirectUrl = httpreq.getRequestURL().toString();
			
			redirectUrl = redirectUrl.substring(redirectUrl.indexOf("/tucana-web/")+12);
			
			if (redirectUrl.startsWith("wiki/")){
				((HttpServletResponse) response).sendRedirect("http://en.wikipedia.org/"+redirectUrl);
			}
		}else{
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
