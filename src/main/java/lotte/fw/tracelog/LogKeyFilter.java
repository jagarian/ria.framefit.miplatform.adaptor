package lotte.fw.tracelog;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import lotte.fw.web.ria.RiaArgumentResolver;

/**
 * <PRE>
 * Log4j로 발생시키는 로그 앞에 매 request단위에 unique하게 생성되는 key를 부여하기위해
 * request 시작시 filter를 이용하여 생성시킨다.
 * </PRE>
 * @date     : 2009. 11. 20.
 * @author   : 이건희
 * @hostory : 
 */
public class LogKeyFilter implements Filter{
    
    private static final Logger log = Logger.getLogger(LogKeyFilter.class); // NOPMD by gunhlee on 09. 11. 2 오전 10:21
    
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        
        LogKeyUtil.startLogKey();
        
        try {
            chain.doFilter(req, res);
        } catch(Exception e) {
            log.error("Exception on doFilter", e);
            throw new ServletException("Exception on doFilter", e);
        } finally {
            LogKeyUtil.clearLogKey();
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        
    }
    
}
