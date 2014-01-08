package lotte.fw.opera; 

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class OperaParamData 
{ 
	// Data Delimiter, 동일한 파리미터 이름으로 여러개의 파라미터 값이 송신된 경우,
	// 파라미터 사이의 구분값으로 사용됨	
    public static String DATA_DELIMITER = "^";		
    public static String XDATASET_PARAM_NAME = "XDATASET";
    public static boolean ENCODING_FLAG = true;
    
    // 기본 Character Encoding 
    public static String CHARACTER_ENCODING = "euc-kr";
    
    private String  clientIpAddr = "";				// HTTP Request를 송신한 클라이언트 IP 주소
	private HashMap webFormDataMap = new HashMap();	// Web Form Data를 저장할 HashMap 오브젝트    

	public static String asc2ksc(String asc) throws UnsupportedEncodingException  {
		if(asc == null) 
			return null;
		return new String(asc.getBytes("ISO-8859-1"), "KSC5601");
	}	
	
	public HashMap getDataMap() {
		return webFormDataMap;
	}
	
    public OperaParamData() {
    }
    
    public OperaParamData(String dataDelimiter) {
    	DATA_DELIMITER = dataDelimiter;
    }    
    
    public static boolean getDataEncodingFlag() {
    	return ENCODING_FLAG;
    }
    
    public static boolean setDataEncodingFlag(boolean encodingFlag) {
    	boolean prevEncodingFlag = ENCODING_FLAG;
    	ENCODING_FLAG = encodingFlag;
    	return prevEncodingFlag;
    }
    
    public OperaParamData(HttpServletRequest request, String dataDelimiter) throws UnsupportedEncodingException {
    	DATA_DELIMITER = dataDelimiter;
    	parseData(request);
    }
    
    public OperaParamData(HttpServletRequest request) throws UnsupportedEncodingException 
    {
    	parseData(request);
    }        
    
    public void parseData(HttpServletRequest request) throws UnsupportedEncodingException 
    {
		clientIpAddr = request.getRemoteAddr();     
        
		// Request 객체로 부터 파라미터 이름 리스트를 얻어옴 
		Enumeration enumParamNames = request.getParameterNames();	
		
		String paramValues[] 	= null;			// 파라미터 값 배열
		String paramName 		= "";			// 파라미터 이름
		String paramValue 		= "";			// 파라미터 값        
        
		while(enumParamNames.hasMoreElements()) 
		{
			StringBuffer	sbParamValue = new StringBuffer();
			
			// 파라미터 이름을 추출
			paramName = (String)enumParamNames.nextElement();
			if(paramName.equalsIgnoreCase(XDATASET_PARAM_NAME))
				continue;
			
			System.out.println("Parameter Name = [" + paramName.trim() + "]");			
			
			// 파라미터에 해당하는 파라미터 값을 추출함.
			paramValues = request.getParameterValues(paramName);				
			
			// 파라미터 이름에 해당하는 파라미터 값을 추출함.
			// 값이 여러개일 경우, Delimeter를 붙여서 설정
			paramValue = (paramValues[0] == null) ? "" : paramValues[0];
			if(ENCODING_FLAG) {
				paramValue = asc2ksc(paramValue);
			}
			sbParamValue.append(paramValue.trim());

			if(paramValues.length > 1) {
				for(int i = 1; i < paramValues.length ; i++)
				{
					paramValue = (paramValues[i] == null) ? "" : paramValues[i];
					if(ENCODING_FLAG) {
						paramValue = asc2ksc(paramValue);
					}
					
					sbParamValue.append(DATA_DELIMITER);
					sbParamValue.append(paramValue.trim());
				}
			}
			
			// System.out.println("[" + sParamName.trim() + "] = [" + sbParamValue.toString() + "]");	
            
			webFormDataMap.put(paramName.trim(), sbParamValue.toString());
		}	        
    }
    
    public String getClientIpAddr() {
    	return this.clientIpAddr;
    }
    
    public void setData(String paramName, String paramValue) {
    	webFormDataMap.put(paramName, paramValue);
    }
    
	/**
	 * key에 해당되는 value return   
	 */
	public String getData(String sKey) {
		Object o = webFormDataMap.get(sKey);
		if(o == null) {
			return ""; 
		} else {
			return o.toString();
		}
	}	
    
	/**
	 *  key에 해당하는 INT value return 
	 *  파라미터가 존재하지 않을 경우 0 return 
	 */
	public int getIntData(String sKey) {
		String sData = getData(sKey);
		if(sData.equals("")) {
			sData = "0";
		}
		
	  	return Integer.parseInt(sData);
	}    
} 
