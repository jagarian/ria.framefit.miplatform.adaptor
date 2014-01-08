package lotte.fw.miplatform;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lotte.fw.exception.LotteRuntimeException;
import lotte.fw.miplatform.util.CastMap;
import lotte.fw.miplatform.util.FieldUtils;
import lotte.fw.service.RowStatus;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.PlatformData;
import com.tobesoft.platform.data.Variable;
import com.tobesoft.platform.data.VariableList;
import com.tobesoft.platform.data.Variant;

public class MiPlatformObject {
	private static final long serialVersionUID = 6723434363565853261L;
	public static final String defaultInputName = "dsInput";
	private static final Logger log = Logger.getLogger(MiPlatformObject.class);
	private PlatformData platformData;
//	private LinkedHashMap<String, String> variableList = null;

	public void setPlatformData(PlatformData platformData) {
		this.platformData = platformData;
//		setVariableList();

	}

	public String getRequestParam(String name) {
		VariableList vList = platformData.getVariableList();	
		for ( int i = 0; i < vList.size(); i ++ ) {
			Variable variable = vList.get(i);
			if(variable.getId() == name) return variable.getValue().getString();
		}	
		return null;
//	    return variableList.get(name);
	}
	
//	private void setVariableList() {	
//		VariableList vList = platformData.getVariableList();	
//		variableList = new LinkedHashMap<String, String>();
//		for ( int i = 0; i < vList.size(); i ++ ) {
//			Variable variable = vList.get(i);
//			variableList.put(variable.getId(), variable.getValue().getString());
//		}		
//	}
	
	public Object getModelAttribute(String className) {
		return getModelAttribute(className, defaultInputName);
	}
	public List getModelAttributes(String className) {
		return getModelAttributes(className, defaultInputName);
	}

	public Object getModelAttribute(String className, String datasetName) {
		Class valueObjectClass = null;
		Object valueObject = null;
		try {
			valueObjectClass = Class.forName(className);
			valueObject = getNewInstance(valueObjectClass);
		} catch (Exception e) {
			throw new LotteRuntimeException(e);
		}

		Dataset ds = this.platformData.getDataset(datasetName);
		if (ds == null) {
			if (log.isDebugEnabled()) {
				log.debug("[datsetName] " + datasetName + " = " + ds);
			}
			return valueObject;
		}

		if (ds.getRowCount() > 1) {
			log.warn("해당 Dataset에 둘 이상의 데이터셋이 존재합니다. 해당 로직 점검 요망.");
		}

		if (ds.getRowCount() + ds.getDeleteRowCount() == 0)
			return valueObject;

		try {
			if (log.isDebugEnabled())
				log.debug("ValueObject Class : " + className);

			Map fieldMap = createFieldMap(valueObjectClass);

			int columnLength = ds.getColumnCount();
			String[] columnIds = new String[columnLength];
			for (int i = 0; i < columnIds.length; ++i) {
				columnIds[i] = ds.getColumnID(i);
			}
			if (ds.getRowCount() != 0) {
				setRowStatus(valueObject, RowStatus.COL_ROWSTATUS,
						convertRowType(ds.getRowStatus(0)));
				setValueObject(valueObject, ds, 0, fieldMap, columnIds, 
						convertRowType(ds.getRowStatus(0)));
			} else {
				setRowStatus(valueObject, RowStatus.COL_ROWSTATUS,
						RowStatus.ROWTYPE_DELETE);
				setValueObject(valueObject, ds, 0, fieldMap, columnIds,
						RowStatus.ROWTYPE_DELETE);
			}
		} catch (Exception e) {
			throw new LotteRuntimeException(e);
		}

		return valueObject;
	}

	public List getModelAttributes(String className, String datasetName) {
		Dataset ds = this.platformData.getDataset(datasetName);
		if (ds == null) {
			if (log.isDebugEnabled())
				log.debug("[datsetName] " + datasetName + " = " + ds);
			return null;
		}

		long startCreateList = System.currentTimeMillis();

		List list = new ArrayList();
		try {
			if (log.isDebugEnabled())
				log.debug("[VOClass] " + className);
			Class valueObjectClass = Class.forName(className);

			Map fieldMap = createFieldMap(valueObjectClass);

			int columnLength = ds.getColumnCount();
			String[] columnID = new String[columnLength];
			for (int i = 0; i < columnID.length; ++i) {
				columnID[i] = ds.getColumnID(i);
			}
			Object valueObject = null;

			int deleteRowLength = ds.getDeleteRowCount();
			for (int rowIndex = 0; rowIndex < deleteRowLength; ++rowIndex) {
				valueObject = getNewInstance(valueObjectClass);
				setRowStatus(valueObject, RowStatus.COL_ROWSTATUS,
						RowStatus.ROWTYPE_DELETE);
				setValueObject(valueObject, ds, rowIndex, fieldMap, columnID,
						RowStatus.ROWTYPE_DELETE);
				list.add(valueObject);
			}

			int insertUpdateRowLength = ds.getRowCount();
			for (int rowIndex = 0; rowIndex < insertUpdateRowLength; ++rowIndex) {
				valueObject = getNewInstance(valueObjectClass);
				setRowStatus(valueObject, RowStatus.COL_ROWSTATUS,
						convertRowType(ds.getRowStatus(rowIndex)));
				setValueObject(valueObject, ds, rowIndex, fieldMap, columnID,
						convertRowType(ds.getRowStatus(rowIndex)));
				list.add(valueObject);
			}
		} catch (Exception e) {
			throw new LotteRuntimeException(e);
		}

		if (log.isInfoEnabled())
			log.info("[Execute Time] extract list from xml data - "
					+ (System.currentTimeMillis() - startCreateList) + "ms");

		return list;
	}

	private void setValueObject(Object valueObject, Dataset ds, int rowIndex,
			Map fieldMap, String[] columnIds, Integer rowType)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		String uppercasedColumnID = null;
		Variant variant = null;
		Object variantValue = null;
		Class valueObjectClass = valueObject.getClass();
		boolean isMapType = Map.class.isAssignableFrom(valueObjectClass);

		for (int colIndex = 0; colIndex < columnIds.length; ++colIndex) {
			uppercasedColumnID = columnIds[colIndex].toUpperCase();
			if ((fieldMap.get(uppercasedColumnID) == null) && (!(isMapType))) {
				fieldNotExistWarn(valueObjectClass, columnIds[colIndex]);
			} else {
				if (RowStatus.ROWTYPE_DELETE == rowType)
					variant = ds.getDeleteColumn(rowIndex, columnIds[colIndex]);
				else
					variant = ds.getColumn(rowIndex, columnIds[colIndex]);

				if (isMapType) {
					variantValue = getVariantValue(variant, ds.getColumnInfo(
							colIndex).getColumnType());
					((Map) valueObject).put(columnIds[colIndex], variantValue);
				} else {
					variantValue = getVariantValue(variant, ((Field) fieldMap
							.get(uppercasedColumnID)).getType());
					PropertyUtils.setProperty(valueObject, ((Field) fieldMap
							.get(uppercasedColumnID)).getName(), variantValue);
				}
			}
		}
	}

	private void fieldNotExistWarn(Class valueObjectClass, String columnName) {
		log.warn("Given class has no field mapped to dataset column '"
				+ valueObjectClass.getName() + "." + columnName + "'");
	}

	private void setRowStatus(Object valueObject, String name, Object value) {
		try {
			if (Map.class.isAssignableFrom(valueObject.getClass())) {
				((Map) valueObject).put(name, value);
				return;
			}
			PropertyUtils.setProperty(valueObject, name, value);
		} catch (Exception localException) {
			log.warn("Given class's row status fields count't be set. "
					+ "check field name(rowStatus) and setter method. "
					+ localException);
			throw new LotteRuntimeException(localException);
		}

	}

	private Integer convertRowType(String rowType) {
		if (rowType.equalsIgnoreCase("normal"))
			return new Integer(1);
		if (rowType.equalsIgnoreCase("insert"))
			return new Integer(2);
		if (rowType.equalsIgnoreCase("update"))
			return new Integer(4);
		if (rowType.equalsIgnoreCase("delete"))
			return new Integer(8);

		throw new LotteRuntimeException("Abnormal ROWTYPE: " + rowType);
	}

	private Object getVariantValue(Variant variant, short columnType) {
		Object variantValue = null;

		if ((1 == columnType) || (12 == columnType)) {
			if (variant != null)
				variantValue = variant.toString();
		} else if (2 == columnType) {
			String value = null;
			if (variant != null)
				value = variant.toString();
			if (!(StringUtils.isBlank(value)))
				variantValue = new Integer(Double.valueOf(value).intValue());
			else
				variantValue = null;
		} else if (4 == columnType) {
			String value = null;
			if (variant != null)
				value = variant.toString();
			if (!(StringUtils.isBlank(value)))
				variantValue = Double.valueOf(value);
			else
				variantValue = null;
		} else if (8 == columnType) {
			Date value = null;
			if (variant != null)
				value = variant.getDate();
			if (value != null)
				variantValue = value;
			else
				variantValue = null;
		} else if (9 == columnType) {
			if (variant != null)
				variantValue = variant.getBinary();
			else
				variantValue = null;

		} else if (variant != null) {
			variantValue = variant.toString();
		}

		return variantValue;
	}

	private Object getVariantValue(Variant variant, Class type) {
		Object variantValue = null;

		if (type.getName().equals("java.lang.String")) {
			if (variant != null)
				variantValue = variant.toString();
		} else if (type.getName().equals("int")) {
			String value = null;
			if (variant != null)
				value = variant.toString();
			int param = 0;
			if (!(StringUtils.isBlank(value)))
				param = Double.valueOf(value).intValue();
			variantValue = new Integer(param);
		} else if (type.getName().equals("java.lang.Integer")) {
			String value = null;
			if (variant != null)
				value = variant.toString();
			if (!(StringUtils.isBlank(value)))
				variantValue = new Integer(Double.valueOf(value).intValue());
			else
				variantValue = null;
		} else if (type.getName().equals("double")) {
			String value = null;
			if (variant != null)
				value = variant.toString();
			if (!(StringUtils.isBlank(value)))
				variantValue = Double.valueOf(value);
			else
				variantValue = new Double("0");
		} else if (type.getName().equals("java.lang.Double")) {
			String value = null;
			if (variant != null)
				value = variant.toString();
			if (!(StringUtils.isBlank(value)))
				variantValue = Double.valueOf(value);
			else
				variantValue = null;
		} else if (type.getName().equals("long")) {
			String value = null;
			if (variant != null)
				value = variant.toString();
			long param = 0L;
			if (!(StringUtils.isBlank(value)))
				param = Double.valueOf(value).longValue();
			variantValue = new Long(param);
		} else if (type.getName().equals("java.lang.Long")) {
			String value = null;
			if (variant != null)
				value = variant.toString();
			if (!(StringUtils.isBlank(value)))
				variantValue = new Long(Double.valueOf(value).longValue());
			else
				variantValue = null;
		} else if (type.getName().equals("java.util.Date")) {
			Date value = null;
			if (variant != null)
				value = variant.getDate();
			if (value != null)
				variantValue = value;
			else
				variantValue = null;
		} else if (type.getName().equals("java.math.BigDecimal")) {
			String value = null;
			if (variant != null)
				value = variant.toString();
			if (!(StringUtils.isBlank(value)))
				variantValue = new BigDecimal(value);
			else
				variantValue = null;
		} else if (type.getName().equals("[B")) {
			if (variant != null)
				variantValue = variant.getBinary();
			else
				variantValue = null;
		} else {
			throw new RuntimeException(
					type.getName()
							+ " class has not supportable java type for miplatform data conversion.");
		}

		return variantValue;
	}

	private Map createFieldMap(Class valueObjectClass) {
		if (Map.class.isAssignableFrom(valueObjectClass))
			return new HashMap();

		Map fieldMap = new HashMap();
		Field[] fields = FieldUtils
				.getReadableFieldsIncludingSuper(valueObjectClass);

		for (int i = 0; i < fields.length; ++i) {
			fieldMap.put(fields[i].getName().toUpperCase(), fields[i]);
		}
		return fieldMap;
	}

	private Object getNewInstance(Class valueObjectClass) {
		if ("java.util.Map".equals(valueObjectClass.getName()))
			return new HashMap();

		Object valueObj = null;
		try {
			valueObj = valueObjectClass.newInstance();
		} catch (InstantiationException ie) {
			throw new RuntimeException(ie);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}
		return valueObj;
	}

}
