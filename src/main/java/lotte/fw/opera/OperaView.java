/**
 * 
 */
package lotte.fw.opera;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.PropertyService;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import xdataset.XDataSet;

/**
* @Class Name : BusinessException.java
* @Description : Opera의로 response를 전송하기 위한 view class이다.
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
public class OperaView extends AbstractView {
    
    protected PropertyService propertyMain;
    
    private static final Logger log = Logger.getLogger(OperaView.class);
    private static final String XDATASET_OBJ_NAME = "XDATASET";    
    
    public void setPropertyMain(PropertyService propertyMain) {
        this.propertyMain = propertyMain;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	XDataSet	xDataSet = (XDataSet)model.get(XDATASET_OBJ_NAME);
    	xDataSet.setHttpResponse(response);
    	xDataSet.returnData();
    }
}
