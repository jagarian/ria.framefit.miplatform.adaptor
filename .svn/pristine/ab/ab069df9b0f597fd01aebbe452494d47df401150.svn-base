package lotte.fw.web.ria;

import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * <PRE>
 * MiPlatform에서 전달되는 data를 처리하여 controller의 argument로 넘기기 위한 class이다.
 * WebArgumentResolver를 구현한다.
 * </PRE>
 */
public class RiaArgumentResolver implements WebArgumentResolver {
    
    private static final Logger log = Logger.getLogger(RiaArgumentResolver.class); // NOPMD by gunhlee on 09. 11. 2 오전 10:21

    /**
     * request의 내용을 변경해 주는 uiAdaptor
     */
    private UiAdaptor uiAs;

    public void setUiAdaptors(UiAdaptor uiAs) {
        this.uiAs = uiAs;
    }

    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {

        Class<?> type = methodParameter.getParameterType();
        Object uiObject = null;
        
        if (uiAs == null) {
            return UNRESOLVED; 
        }

        if (type.equals(uiAs.getModelName())) {
        	log.debug("[method]" + methodParameter.getMethod().getName() + " [type]" + type);
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            uiObject = (Object) uiAs.convert(request);
            return uiObject; 
        }

        return UNRESOLVED;
    }

}
