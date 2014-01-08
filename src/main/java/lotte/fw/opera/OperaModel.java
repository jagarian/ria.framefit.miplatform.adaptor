package lotte.fw.opera;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lotte.fw.exception.BusinessException;
import lotte.fw.exception.MaxFetchingException;
import lotte.fw.exception.NoAuthorityException;
import lotte.fw.exception.NoSessionException;
import lotte.fw.exception.ValidationException;
import lotte.fw.service.RowStatus;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;

import xdataset.XDataSet;
import xdataset.exception.XDataException;

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
public class OperaModel implements Serializable {
    public static final String DEFAULT_OUTPUT_XDATASET_NAME = "DS_OUTPUT";
    
    private static final long serialVersionUID = 6723434363563378261L;    
    private static final Logger log = Logger.getLogger(OperaModel.class); 
    private static final String OPERA_VIEW_NAME = "operaView";
    private static final String XDATASET_OBJ_NAME = "XDATASET";
    
    private XDataSet xDataSet = null;
    
    // mjhong
    public OperaModel() {
    }

    // mjhong    
    public void setHttpRequest(HttpServletRequest request) {
    	try {
			xDataSet = new XDataSet(request, null);
		} catch (XDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return;
    }
    
    // mjhong    
    public void addAttribute(List<Map<String,Object>> list) {
    	addAttribute(DEFAULT_OUTPUT_XDATASET_NAME, list);
    }
    
    // mjhong    
    public void addAttribute(Map<String,Object> list) {
    	addAttribute(DEFAULT_OUTPUT_XDATASET_NAME, list);
    }
    
    // mjhong
    /**
     * string으로 변환하기위한 private method
     * @param obj
     * @return
     */
    private String changeString(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }    
    
    // mjhong
    // 테이블 데이터를 추가하는 로직
    // 각 레코드는 HashMap으로 구성되고 HashMap은 List에 추가되어 있음.
    /**
     * datasetList에 dataset을 추가한다.
     * @param name : XDataSet명
     * @param list : list 형태의 결과 데이터
     */
    public void addAttribute(String xDataSetName, List<Map<String,Object>> list) {
        int		recordIndex = 0;
        
        if(list != null && !list.isEmpty()) {
            Iterator<Map<String,Object>> iter = list.iterator();
            
            // 레코드를 돌면서 처리
            while(iter.hasNext()) {
                Map<String,Object> map = iter.next();

                // 레코드의 칼럼을 돌면서 처리
                Iterator<String> keyIter = map.keySet().iterator();
                while(keyIter.hasNext()) {
                    String key = keyIter.next();                	
                	try {
                		xDataSet.setRecordKey(xDataSetName, recordIndex, (String)map.get(RowStatus.COL_ROWKEYVALUE));
						xDataSet.setData(xDataSetName, key, recordIndex, changeString(map.get(key)));
						
						log.debug("[xDataSetName] = " + xDataSetName + " [recordIndex] = " + recordIndex + " [key] = " + key + " [changeString(map.get(key))] = " + changeString(map.get(key)));
					} catch (XDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                
                recordIndex++;
            }        	
        }
    }    
    
    // mjhong
    /**
     * datasetList에 dataset을 추가한다.
     * @param name : dataset 명
     * @param map : A1Map형태의 결과 데이터
     */
    public void addAttribute(String xDataSetName, Map<String,Object> map) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        list.add(map);
        addAttribute(xDataSetName, list);
    }

    //jypark
    //테스트 확인용 임시...
    public XDataSet getXDataSet(){
    	return this.xDataSet;
    }
    
    // mjhong
    /**
     * ModelAndView에 MiPlatformModel을 할당하여 return한다.\
     * @return
     */
    public ModelAndView success() {
    	/*
        addResponseParam("ErrorCode", "0");
        addResponseParam("ErrorMsg", "SUCC");
        addResponseParam("ErrorType","NoException");
        */
        
        ModelAndView mav = new ModelAndView(OperaModel.OPERA_VIEW_NAME);
        
        mav.addObject(XDATASET_OBJ_NAME, this.xDataSet);
        
        return mav;
    }
    
    /**
     * exception 처리후 MiPlatform에 전송할 error 항목을 할당하여 ModelAndView를 return한다.
     * @param e
     * @return
     */
    public ModelAndView error(Exception e) {
        
//    	log.debug("ModelAndView error 1:e: " + e);
//    	log.error("ModelAndView error 2:e.getMessage: " + e.getMessage(), e);
//    	log.debug("ModelAndView error 3:xDataset: " + xDataSet);
    	
    	//e.printStackTrace();
        
    	String errorType="";
    	String errorTypeNm="";
        
        if(e instanceof NoSessionException) { //세션이 없는 경우
        	errorType = "SYS.001";
        	errorTypeNm = "NoSessionException";
            
        } else if(e instanceof DataIntegrityViolationException) { //pk dup을 식별하기 위한 작업
            //oracle만 해당됨
            if(e.getMessage().indexOf("ORA-00001") > -1) {
            	errorType = "SYS.002";
            	errorTypeNm = "DuplicationException";
            } else {
            	errorType = "SYS.999";
            	errorTypeNm = "Exception";
            }
            
        } else if(e instanceof MaxFetchingException) { //max fetching 건수를 초과 
        	errorType = "SYS.003";
        	errorTypeNm = "MaxFetchingException";
            
        } else if(e instanceof NoAuthorityException) { //권한 없음
        	errorType = "SYS.004";
        	errorTypeNm = "NoAuthorityException";
            
        } else if(e instanceof ValidationException) { //validation 실패
        	errorType = "SYS.005";
        	errorTypeNm = "ValidationException";
            
        } else if(e instanceof BusinessException) { //기타 biz 체크 실패
        	errorType = "SYS.006";
        	errorTypeNm = "BusinessException";
            
        } else if(e instanceof Exception) { 
        	errorType = "SYS.999";
        	errorTypeNm = "Exception";
            
        } else {
        	errorType = "SYS.999";
        	errorTypeNm = "Throwable";
        }
        
        /*
        addResponseParam("ErrorCode", "-1");
        addResponseParam("ErrorMsg", e.getMessage());
        addResponseParam("ErrorType", errorType);
        */
        
        ModelAndView mav = new ModelAndView(OperaModel.OPERA_VIEW_NAME);
        try {
			xDataSet.setErrorMessage(errorType,"[" + errorTypeNm + "]" + e.getMessage());
		} catch (XDataException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
		}
        mav.addObject(XDATASET_OBJ_NAME, this.xDataSet);
               
        return mav;
    }    
}
