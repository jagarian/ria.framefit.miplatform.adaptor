/**
 * 
 */
package lotte.fw.miplatform;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.PropertyService;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import com.tobesoft.platform.PlatformConstants;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.VariableList;

/**
* @Class Name : BusinessException.java
* @Description : Miplatform의로 response를 전송하기 위한 view class이다.
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
public class MiPlatformView extends AbstractView {
    
    protected PropertyService propertyMain;
    
    private static final Logger log = Logger.getLogger(MiPlatformView.class); 
    
    public void setPropertyMain(PropertyService propertyMain) {
        this.propertyMain = propertyMain;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        VariableList variableList = (VariableList)model.get(MiPlatformModel.VARIABLE_LIST_NAME);
        DatasetList datasetList = (DatasetList)model.get(MiPlatformModel.DATASET_LIST_NAME);
        
        /**
         * PlatformResponse.XML       : XML TEXT   포맷으로 데이타 전송
         * PlatformResponse.ZLIB_COMP : XML BINARY 압축으로 데이터 전송
         */
        int method = PlatformResponse.XML;
        if(propertyMain.getBoolean("miplatform.response.compress", false)) {
            method = PlatformResponse.ZLIB_COMP;
        }
        PlatformResponse platformResponse = new PlatformResponse(response, method, PlatformConstants.CHARSET_UTF8);
        platformResponse.sendData(variableList, datasetList);
        
        //new PlatformResponse(response, PlatformConstants.CHARSET_UTF8).sendData(variableList, datasetList);
    }

}
