package my.com.fotia.osdec.config;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter {
	
	@Value("${srv.ip}")
	private String ipAdd;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        
        if(ipAdd == null || "".equals(ipAdd)) {
        	ipAdd = "http://localhost:4200";
        }

        response.setHeader("Access-Control-Allow-Origin", ipAdd);
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, content-type, xsrf-token");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");

        if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
            try {
                chain.doFilter(req, res);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Pre-flight");
            /*response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type," +
                    "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with");
            */
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}