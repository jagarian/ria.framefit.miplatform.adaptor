package lotte.fw.opera;

import javax.servlet.http.HttpServletRequest;

import lotte.fw.opera.OperaModel;
import lotte.fw.web.ria.UiAdaptor;

import org.apache.log4j.Logger;


/**
* @Class Name : BusinessException.java
* @Description : 단순히 OperaModel을 생성하고 전달함
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
public class OperaModelUiAdaptor implements UiAdaptor {
    
    private static final Logger log = Logger.getLogger(OperaModelUiAdaptor.class); // NOPMD by gunhlee on 09. 11. 2 오전 10:24
        
    // mjhong
    /* (non-Javadoc)
     * @see lotte.fw.opera.UiAdaptor#convert(javax.servlet.http.HttpServletRequest)
     */
    public Object convert(HttpServletRequest request) throws Exception {
    	OperaModel operaModel = new OperaModel();
    	operaModel.setHttpRequest(request);
    	return operaModel;
    }

    /* (non-Javadoc)
     * @see lotte.fw.opera.UiAdaptor#getModelName()
     */
    public Class getModelName() {
        return OperaModel.class;
    }

}
