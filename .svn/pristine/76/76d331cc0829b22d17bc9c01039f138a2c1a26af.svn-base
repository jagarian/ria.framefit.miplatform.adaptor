package lotte.fw.opera;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import lotte.fw.web.ria.UiAdaptor;

import org.apache.log4j.Logger;

import xdataset.XDataSet;

/**
* @Class Name : BusinessException.java
* @Description : opera에서 전송된 request의 내용을 OperaObject에 담아 return하여 주는 class
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
public class OperaObjectUiAdaptor implements UiAdaptor {
    
    private static final Logger log = Logger.getLogger(OperaObjectUiAdaptor.class); // NOPMD by gunhlee on 09. 11. 2 ?삤?쟾 10:24
    
    // mjhong
    /* (non-Javadoc)
     * @see lotte.fw.opera.UiAdaptor#convert(javax.servlet.http.HttpServletRequest)
     */
    public Object convert(HttpServletRequest request) throws Exception {
    	// 데이터셋 이외의 데이터를 저장하는 오브젝트 생성 
       	OperaParamData operaParamData = new OperaParamData();
       	operaParamData.parseData(request);
       	
        XDataSet		xDataSet = null;
        
        String beforeCharacterEncoding = request.getCharacterEncoding();
        log.debug("before request encoding: "+beforeCharacterEncoding);
        
//        request.setCharacterEncoding("euc-kr");
        
//        String afterCharacterEncoding = request.getCharacterEncoding();
//        log.debug("after request encoding: "+afterCharacterEncoding);       
        
       	xDataSet = new XDataSet(request, null);

//       	log.debug("after xDataSet : " + xDataSet.getData("DS_OUTPUT", "regUser"));
       	
       	OperaObject		operaObject = new OperaObject();
       	operaObject.setOperaData(xDataSet, operaParamData);
       	
       	return operaObject;
    }

    /* (non-Javadoc)
     * @see lotte.fw.opera.UiAdaptor#getModelName()
     */
    public Class getModelName() {
        return OperaObject.class;
    }

}
