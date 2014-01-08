/**
 * 
 */
package lotte.fw.miplatform.util;

import java.io.Serializable;

import lcn.module.framework.ibatis.CamelUtil;

/**
* @Class Name : BusinessException.java
* @Description : A1Map을 확장한 class로 query 결과를 담기위해 사용하는 Map이다.
 * put() method에서 column을 camel case로 변환한다.
 * 예)USER_DEPT_NAME ==> userDeptName
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
public class CastResultMap<K,V> extends CastMap<Object,Object> implements Serializable {
    private static final long serialVersionUID = 6723434363225852261L;
    
    public CastResultMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public CastResultMap(int initialCapacity) {
        super(initialCapacity);
    }
    
    public CastResultMap() {
        super();
    }
    
    public CastResultMap(
            int initialCapacity,
            float loadFactor,
            boolean accessOrder) {
            super(initialCapacity, loadFactor, accessOrder);
    }
    
    /**
     * key 에 대하여 Camel Case 변환하여 super.put 을 호출한다.
     * @param key - '_' 가 포함된 변수명
     * @param value - 명시된 key 에 대한 값 (변경 없음)
     * @return previous value associated with specified key, or null if there was no mapping for key
     */
    @Override
    public Object put(Object key, Object value) {
        return super.put(CamelUtil.convert2CamelCase((String)key), value);
    }
    
}
