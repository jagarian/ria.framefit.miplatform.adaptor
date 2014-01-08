package lotte.fw.miplatform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lotte.fw.exception.BusinessException;
import lotte.fw.exception.MaxFetchingException;
import lotte.fw.exception.NoAuthorityException;
import lotte.fw.exception.NoSessionException;
import lotte.fw.exception.ValidationException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;

import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.VariableList;

/**
* @Class Name : BusinessException.java
* @Description : MiPlatform에 data를 전송하기 위한 Model Class
 * 응답할 datasetlist 및 variable list를 할당하는 method를 제공한다.
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
public class MiPlatformModel implements Serializable {
    public static final String DataSetOutputName = "dsOutput";
    
    private static final long serialVersionUID = 6723434363563378261L;    
    private static final Logger log = Logger.getLogger(MiPlatformModel.class); 
    public static final String VARIABLE_LIST_NAME = "variableList";
    public static final String DATASET_LIST_NAME = "datasetList";
    private static final String MIP_VIEW_NAME = "miPlatformView";
    
    private final VariableList variableList = new VariableList();
    private final DatasetList datasetList = new DatasetList();
    
    /**
     * ModelAndView에 MiPlatformModel을 할당하여 return한다.\
     * @return
     */
    public ModelAndView success() {
        addResponseParam("ErrorCode", "0");
        addResponseParam("ErrorMsg", "SUCC");
        addResponseParam("ErrorType","NoException");
        
        ModelAndView mav = new ModelAndView(MiPlatformModel.MIP_VIEW_NAME);
        mav.addObject(MiPlatformModel.VARIABLE_LIST_NAME, this.variableList);
        mav.addObject(MiPlatformModel.DATASET_LIST_NAME, this.datasetList);
        
        return mav;
    }
    
    /**
     * exception 처리후 MiPlatform에 전송할 error 항목을 할당하여 ModelAndView를 return한다.
     * @param e
     * @return
     */
    public ModelAndView error(Exception e) {
        log.error(e.getMessage(), e);
        e.printStackTrace();
        
        String errorType;
        
        if(e instanceof NoSessionException) { //세션이 없는 경우
            errorType = "NoSessionException";
            
        } else if(e instanceof DataIntegrityViolationException) { //pk dup을 식별하기 위한 작업
            //oracle만 해당됨
            if(e.getMessage().indexOf("ORA-00001") > -1) {
                errorType = "DuplicationException";
            } else {
                errorType = "Exception";
            }
            
        } else if(e instanceof MaxFetchingException) { //max fetching 건수를 초과 
            errorType = "MaxFetchingException";
            
        } else if(e instanceof NoAuthorityException) { //권한 없음
            errorType = "NoAuthorityException";
            
        } else if(e instanceof ValidationException) { //validation 실패
            errorType = "ValidationException";
            
        } else if(e instanceof BusinessException) { //기타 biz 체크 실패
            errorType = "BusinessException";
            
        } else if(e instanceof Exception) { 
            errorType = "Exception";
            
        } else {
            errorType = "Throwable";
        }
        
        addResponseParam("ErrorCode", "-1");
        addResponseParam("ErrorMsg", e.getMessage());
        addResponseParam("ErrorType", errorType);
        
        ModelAndView mav = new ModelAndView(MiPlatformModel.MIP_VIEW_NAME);
        mav.addObject(MiPlatformModel.VARIABLE_LIST_NAME, this.variableList);
        mav.addObject(MiPlatformModel.DATASET_LIST_NAME, new DatasetList());
        
        return mav;
    }
    
    
    /**
     * variableList에 문자열을 추가 한다.
     * @param name
     * @param value
     */
    public void addResponseParam(String name, String value) {
        variableList.addStr(name, value);
    }
    
    public void addAttribute(List<Map<String,Object>> list) {
    	addAttribute(DataSetOutputName, list);
    }
    
    public void addAttribute(Map<String,Object> list) {
    	addAttribute(DataSetOutputName, list);
    }
    
    /**
     * datasetList에 dataset을 추가한다.
     * @param name : dataset명
     * @param list : list 형태의 결과 데이터
     */
    public void addAttribute(String name, List<Map<String,Object>> list) {
        Dataset dataset = new Dataset();
        
        if(list != null && !list.isEmpty()) {
            //make dataset header
            Map<String,Object> firstRow = list.get(0);
            Iterator<String> headIter = firstRow.keySet().iterator();
            while(headIter.hasNext()) {
                dataset.addColumn(headIter.next(), ColumnInfo.COLUMN_TYPE_STRING, 255);
            }
            
            Iterator<Map<String,Object>> iter = list.iterator();
            while(iter.hasNext()) {
                Map<String,Object> map = iter.next();
                int row = dataset.appendRow();
                Iterator<String> keyIter = map.keySet().iterator();
                
                while(keyIter.hasNext()) {
                    String key = keyIter.next();
                    dataset.setColumn(row, key, changeString(map.get(key)));
                }
            }
        }
        
        datasetList.addDataset(name, dataset);
    }
    
    /**
     * datasetList에 dataset을 추가한다.
     * @param name : dataset 명
     * @param map : A1Map형태의 결과 데이터
     */
    public void addAttribute(String name, Map<String,Object> map) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        list.add(map);
        addAttribute(name, list);
    }
    
    /**
     * string으로 변환하기위한 private method
     * @param obj
     * @return
     */
    private String changeString(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}
