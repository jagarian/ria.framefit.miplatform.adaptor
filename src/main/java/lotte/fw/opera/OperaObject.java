package lotte.fw.opera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lotte.fw.exception.LotteRuntimeException;
import lotte.fw.service.RowStatus;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import xdataset.XDataSet;
import xdataset.exception.XDataException;

public class OperaObject {
	private static final long serialVersionUID = 6723434363565853261L;
	public static final String DEFAULT_INPUT_XDATASET_NAME = "DS_INPUT";
	private static final Logger log = Logger.getLogger(OperaObject.class);
	
	private XDataSet		xDataSet = null;
	private OperaParamData	operaParamData = null;

	public void setOperaData(XDataSet xDataSet, OperaParamData operaParamData) {
		this.xDataSet = xDataSet;
		this.operaParamData = operaParamData;
	}

	// mjhong
	// XDataSet 이외에 Parameter로 넘어오는 데이터를 반환함
	// 해당 값이 없을 경우는 공백 문자열이 리턴됨
	public String getRequestParam(String name) {
		return operaParamData.getData(name);
	}
	
	// mjhong
	// UI에서 올라온 데이터를 저장하기 위한 클래스의 오브젝트 생성
	private Object getNewInstance(Class valueObjectClass) {
		if ("java.util.Map".equals(valueObjectClass.getName()))
			return new HashMap();
		
		Object valueObj = null;
		try {
			valueObj = valueObjectClass.newInstance();
		} 
		catch (InstantiationException ie) {
			throw new RuntimeException(ie);
		} 
		catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}
		
		return valueObj;
	}	

    //jypark
    //테스트 확인용 임시...
    public XDataSet getXDataSet(){
    	return this.xDataSet;
    }

	//jypark
	// 데이터셋의 이름 리스트를 String[] 형식으로 반환
	public String[] getDataSetNamesArray() {
		return xDataSet.getDataSetNamesArray();
	}
	
	//jypark
	// 데이터셋의 이름을  String 형식으로 반환
	public String getDataSetNameByIndex(int index) {
		return xDataSet.getDataSetNamesArray()[index];
	}
	
	//jypark
	// 데이터셋의 개수를 int 형식으로 반환
	public int getDataSetCount() {
		return xDataSet.getDataSetNamesArray().length;
	}
	
	//jypark
	// 데이터셋의 개수를 int 형식으로 반환
	public String[] getColumnNamesArray(String xDataSetName) {
		try {
			return (String[]) xDataSet.getColumnNamesArray(xDataSetName);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new LotteRuntimeException(e);
		}  
		
	}
	
	
	// mjhong
	// 데이터 셋에서 첫번째 레코드 값만을 구함
	public Object getModelAttribute(String className) {
		return getModelAttribute(className, DEFAULT_INPUT_XDATASET_NAME);
	}
	
	// mjhong
	// 데이터 셋에서 전체 레코드 값을 구함
	public List getModelAttributes(String className) {
		return getModelAttributes(className, DEFAULT_INPUT_XDATASET_NAME);
	}

	// mjhong 
	private Integer convertRowType(String xDataSetName, int rowIndex) {
		try {
			if(xDataSet.isDeleteRecord(xDataSetName, rowIndex))
				return new Integer(8);
			
			if(xDataSet.isInsertRecord(xDataSetName, rowIndex))
				return new Integer(2);
			
			if(xDataSet.isUpdateRecord(xDataSetName, rowIndex))
				return new Integer(4);
			
			// Normal 레코드
			return new Integer(1);
		}
		catch(XDataException e) {
			//e.printStackTrace();
			throw new LotteRuntimeException(e);
			//return new Integer(1);
		}
	}	
	
    // mjhong
	private void setRowStatus(Object valueObject, String name, Object value) {
		try {
			if(Map.class.isAssignableFrom(valueObject.getClass())) {
				((Map)valueObject).put(name, value);
			}
			else {
				PropertyUtils.setProperty(valueObject, name, value);
			}			
		} 
		catch (Exception localException) {
			log.warn("setRowStauts> Exception Msg = " + localException.getMessage());
			//localException.printStackTrace();			
			log.warn("Given class's row status fields count't be set. "
					+ "check field name(rowStatus) and setter method. "
					+ localException);
			throw new LotteRuntimeException(localException);
		}
	}
	
	private void setRowKeyValue(Object valueObject, String name, Object value) {
		try {
			if(Map.class.isAssignableFrom(valueObject.getClass())) {
				((Map)valueObject).put(name, value);
			}
			else {
				PropertyUtils.setProperty(valueObject, name, value);
			}			
		} 
		catch (Exception localException) {
			log.warn("setRowKeyValue> Exception Msg = " + localException.getMessage());
			//localException.printStackTrace();			
			log.warn("Given class's row key value fields count't be set. "
					+ "check field name(rowKeyValue) and setter method. "
					+ localException);
			throw new LotteRuntimeException(localException);
		}
	}
	
	// mjhong 
	private void setRowValue(Object valueObject, String name, Object value) {	
		try {
			if(Map.class.isAssignableFrom(valueObject.getClass())) {
				((Map)valueObject).put(name, value);
			}	 
			else {
				PropertyUtils.setProperty(valueObject, name, value);
			}
		}
		catch (Exception localException) {
			log.warn("setRowValue> Exception Msg = " + localException.getMessage());
			//localException.printStackTrace();
			log.warn("Given class's row value fields count't be set. "
					+ "check field name(rowStatus) and setter method. "
					+ localException);
			throw new LotteRuntimeException(localException);
		}		
	}
	
	// mjhong 
	// 데이터셋 에서 첫번째 레코드를 구함
	public Object getModelAttribute(String className, String xDataSetName) {
		Class valueObjectClass = null;
		Object valueObject = null;
		int i = 0;
		
		try {
			valueObjectClass = Class.forName(className);
			// java.util.Map 형태인 경우 HashMap을 리턴하고, 아닌 경우는 valueObjectClass의 newInstance() 함수를 호출
			// 현재는 HashMap인 경우만 고려
			valueObject = getNewInstance(valueObjectClass);
			if(valueObject == null) {
				return null;
			}
		} 
		catch (Exception e) {
			throw new LotteRuntimeException(e);
		}

		String[] xDataSetNames = xDataSet.getDataSetNamesArray();
		
		// 데이터 셋 이름 리스트의 갯수가 1 미만인 경우, 그냥 빈 ValueObject를 리턴함.
		if(xDataSetNames.length < 1) {
			return valueObject;
		}
		
		for(i = 0; i < xDataSetNames.length; i++) {
			if(xDataSetName.compareTo(xDataSetNames[i]) == 0) {
				break;
			}
		}
		
		// 데이터 셋 이름 리스트에 해당 데이터셋의 이름이 없는 경우, 그냥 빈 ValueObject를 리턴함.	
		if(i == xDataSetNames.length) {
			return valueObject;
		}
		
		// 레코드 카운트가 1 미만은 경우에는 그냥 빈 ValueObject를 리턴함.
		try {
			if(xDataSet.getRecordCount(xDataSetName) < 1) {
				return valueObject;		
			}
		}
		catch(XDataException e) {
			return valueObject;
		}
		
		try {
			int 	recordIndex = 0;
			int		columnIndex;
			int		columnCount;
			
			columnCount = xDataSet.getColumnCount(xDataSetName);
			String[] columnNames = xDataSet.getColumnNamesArray(xDataSetName);
			
			// 레코드 형식 설정
			setRowStatus(valueObject, RowStatus.COL_ROWSTATUS, convertRowType(xDataSetName, recordIndex));
			setRowKeyValue(valueObject, RowStatus.COL_ROWKEYVALUE, xDataSet.getRecordKey(xDataSetName, recordIndex));
			// 레코드 데이터 설정
			for(columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
				//  데이터 셋의 첫번째 레코드를 얻어옴.
				setRowValue(valueObject, columnNames[columnIndex], 
						xDataSet.getData(xDataSetName, columnNames[columnIndex], recordIndex));
			}
			
			return valueObject;
		}
		catch (Exception e) {
			throw new LotteRuntimeException(e);
		}		
	}

	// mjhong 
	// 데이터셋의 전체 데이터를 리스트 형태로 구함, 각 레코드는 HashMap 또는 POLO 형식이어야 함.
	public List getModelAttributes(String className, String xDataSetName) {
		List valueList = new ArrayList();
		int	recordIndex, columnIndex;
		int	recordCount, columnCount;
		int	i;
		
		Class valueObjectClass = null;
		
		String[] xDataSetNames = xDataSet.getDataSetNamesArray();
		
		// 데이터 셋 이름 리스트의 갯수가 1 미만인 경우, 그냥 빈 ValueObject를 리턴함.
		if(xDataSetNames.length < 1) {
			return valueList;
		}
		
		for(i = 0; i < xDataSetNames.length; i++) {
			if(xDataSetName.compareTo(xDataSetNames[i]) == 0) {
				break;
			}
		}
		
		// 데이터 셋 이름 리스트에 해당 데이터셋의 이름이 없는 경우, 그냥 빈 ValueObject를 리턴함.	
		if(i == xDataSetNames.length) {
			return valueList;
		}
		
		// 레코드 카운트가 1 미만은 경우에는 그냥 빈 ValueObject를 리턴함.
		try {
			if(xDataSet.getRecordCount(xDataSetName) < 1) {
				return valueList;		
			}
		}
		catch(XDataException e) {
			return valueList;
		}
		
		try {
			recordCount = xDataSet.getRecordCount(xDataSetName);
			for(recordIndex = 0; recordIndex < recordCount; recordIndex++) {
				Object valueObject = null;
				
				try {
					valueObjectClass = Class.forName(className);
					// java.util.Map 형태인 경우 HashMap을 리턴하고, 아닌 경우는 valueObjectClass의 newInstance() 함수를 호출
					// 현재는 HashMap인 경우만 고려
					valueObject = getNewInstance(valueObjectClass);
					if(valueObject == null) {
						return null;
					}
				} 
				catch (Exception e) {
					throw new LotteRuntimeException(e);
				}
							
				columnCount = xDataSet.getColumnCount(xDataSetName);
				String[] columnNames = xDataSet.getColumnNamesArray(xDataSetName);
				
				// 레코드 형식 설정
				setRowStatus(valueObject, RowStatus.COL_ROWSTATUS, convertRowType(xDataSetName, recordIndex));
				setRowKeyValue(valueObject, RowStatus.COL_ROWKEYVALUE, xDataSet.getRecordKey(xDataSetName, recordIndex));
				
				for(columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					//  데이터 셋의 첫번째 레코드를 얻어옴.
					setRowValue(valueObject, columnNames[columnIndex], 
							xDataSet.getData(xDataSetName, columnNames[columnIndex], recordIndex));
				}
							
				valueList.add(valueObject);
			}
			return valueList;			
		} 
		catch (Exception e) {
			throw new LotteRuntimeException(e);
		}
	}
}
