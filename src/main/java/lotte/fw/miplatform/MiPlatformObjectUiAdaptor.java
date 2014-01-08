package lotte.fw.miplatform;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import lotte.fw.web.ria.UiAdaptor;

import org.apache.log4j.Logger;

import com.tobesoft.platform.PlatformConstants;
import com.tobesoft.platform.PlatformRequest;

/**
* @Class Name : BusinessException.java
* @Description : miplatform에서 전송된 request의 내용을 MiPlatformObject에 담아 return하여 주는 class
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
public class MiPlatformObjectUiAdaptor implements UiAdaptor {
    
    private static final Logger log = Logger.getLogger(MiPlatformObjectUiAdaptor.class); // NOPMD by gunhlee on 09. 11. 2 오전 10:24
    
    /* (non-Javadoc)
     * @see lotte.fw.miplatform.UiAdaptor#convert(javax.servlet.http.HttpServletRequest)
     */
    public Object convert(HttpServletRequest request) throws Exception {
        PlatformRequest mpReq = null;
        
        try {
            mpReq = new PlatformRequest(request, PlatformConstants.CHARSET_UTF8);
            mpReq.receiveData();
        } catch(IOException e) {
            log.error("[IOException]on receive MiPlatform request", e);
            throw e;
        }
            
        MiPlatformObject miPlatformRequest = new MiPlatformObject();
        miPlatformRequest.setPlatformData(mpReq.getPlatformData());
        
        return miPlatformRequest;
    }

    /* (non-Javadoc)
     * @see lotte.fw.miplatform.UiAdaptor#getModelName()
     */
    public Class getModelName() {
        return MiPlatformObject.class;
    }

}
