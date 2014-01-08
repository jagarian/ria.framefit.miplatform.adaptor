package lotte.fw.miplatform.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FieldUtils {
	private static Log logger = LogFactory.getLog(FieldUtils.class);

	public static Field[] getReadableFieldsIncludingSuper(Class clazz) {
		Class currentClazz = clazz;
		Field[] fields = (Field[]) null;
		Field[] tempFields = (Field[]) null;

		boolean doLoop = true;
		while (doLoop) {
			tempFields = getReadableFields(currentClazz);
			fields = mergeFields(fields, tempFields);
			currentClazz = currentClazz.getSuperclass();
			if (!("java.lang.Object".equals(currentClazz.getName())))
				continue;
			doLoop = false;
		}
		return fields;
	}

	public static Field[] getReadableFields(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();

		if (fields.length > 0) {
			Map methodsMap = new HashMap();
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; ++i) {
				if (methods[i].getName().startsWith("get")) {
					methodsMap.put(methods[i].getName().substring(3)
							.toLowerCase(), methods[i].getReturnType());
				}
			}

			List readbleFieldList = new ArrayList();
			for (int i = 0; i < fields.length; ++i) {
				if ((methodsMap.get(fields[i].getName().toLowerCase()) == null)
						|| (methodsMap.get(fields[i].getName().toLowerCase()) != fields[i]
								.getType()))
					continue;
				readbleFieldList.add(fields[i]);
			}

			Field[] readableFields = new Field[readbleFieldList.size()];
			int i = 0;
			while (true) {
				readableFields[i] = ((Field) readbleFieldList.get(i));

				++i;
				if (i >= readableFields.length) {
					return readableFields;
				}
			}
		}
		return new Field[0];
	}

	public static Field[] getFieldsIncludingSuper(Class clazz) {
		Class currentClazz = clazz;
		Field[] fields = (Field[]) null;
		Field[] tempFields = (Field[]) null;

		boolean doLoop = true;
		while (doLoop) {
			tempFields = currentClazz.getDeclaredFields();
			fields = mergeFields(fields, tempFields);
			currentClazz = currentClazz.getSuperclass();
			if (!("java.lang.Object".equals(currentClazz.getName())))
				continue;
			doLoop = false;
		}
		return fields;
	}

	public static Field[] mergeFields(Field[] fields1, Field[] fields2) {
		int fieldLength = 0;
		fieldLength = (fields1 == null) ? fieldLength : fieldLength
				+ fields1.length;
		fieldLength = (fields2 == null) ? fieldLength : fieldLength
				+ fields2.length;
		Field[] fields = new Field[fieldLength];

		int index = 0;

		if (fields1 != null) {
			for (int i = 0; i < fields1.length; ++i) {
				fields[(index++)] = fields1[i];
			}
		}

		if (fields2 != null) {
			for (int i = 0; i < fields2.length; ++i) {
				fields[(index++)] = fields2[i];
			}
		}

		return fields;
	}
}
