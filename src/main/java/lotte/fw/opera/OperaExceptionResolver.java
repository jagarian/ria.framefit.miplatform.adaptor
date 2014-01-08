/**
 * 
 */
package lotte.fw.opera;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
* @Class Name : BusinessException.java
* @Description : servlet에서 exception 처리를 위한 resolver class이다.
 * spring의 SimpleMappingExceptionResolver를 확장하였으며
 * Opera에서 요청한 request에서 exception 발생시 exception 처리를 한다.
 ** @Modification Information  
* @
* @  수정일      수정자              수정내용
* @ ---------	---------	-------------------------------
* @ 2012.09.10	최초생성
* 
* @author 
* @since 2012.09.10
* @version 1.0
* @see
* 
*  Copyright (C) by LDCC All right reserved.
*/
public class OperaExceptionResolver extends SimpleMappingExceptionResolver {
    
    private static final Logger log = Logger.getLogger(OperaExceptionResolver.class); // NOPMD by gunhlee on 09. 10. 30 오후 6:43
    
    //miplatform에서 요청한 url인지를 확인하는 url의 확장자(ex:/xxx.mip)
    //miplatform요청인지 url 확장자로 구분한다.
    private String[] operaUrlExtentions = new String[0];
    
    
    /**
     * opera url extension setter
     * @author     : 이건희
     * @param exts
     */
    public void setOperaUrlExtentions(String[] exts) {
        if (exts == null) {
            this.operaUrlExtentions = new String[0];
        }
        else {
            this.operaUrlExtentions = new String[exts.length];
            for (int i = 0; i < exts.length; i++) {
                String extention = exts[i];
                Assert.hasText(extention, "extention must not be empty");
                this.operaUrlExtentions[i] = extention.trim();
            }
        }
    }
    
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.handler.SimpleMappingExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        
        ModelAndView mav = null;
        
        String requestUri = request.getRequestURI();
        //log.debug("[RequestURI]" + requestUri);
        String ext = StringUtil.getLastString(requestUri, ".");
        
        if(isOperaExtension(ext)) {
            log.debug("[Exception:Opera]");
            OperaModel mModel = new OperaModel();
            mModel.setHttpRequest(request);
            mav = mModel.error(ex);
        } else {
            log.debug("[Exception:web]");
            mav = super.resolveException(request, response, handler, ex);
        }
        
        return mav;
    }
    
    
    /**
     * @User     : 이건희
     * @설명     : 현재 request의 url 확장자가 miplatformExtension인지 판단한다.
     * @param ext : 검사할 확장자
     * @return   : 적합여부
     */
    private boolean isOperaExtension(String ext) {
        boolean result = false;
        
        for (int i = 0; i < this.operaUrlExtentions.length; i++) {
            if(operaUrlExtentions[i].equals(ext)) {
                result = true;
                break;
            }
        }
        
        return result;
    }
}
