
package lotte.fw.service;

/**
 * X-Internet Clinet와 연계시 다중 데이터의 Row를 VO로 변환시 
 * CRUD 처리를 위한 Row Status를 저장할 수 있도록 제공하는 인터페이스임
 * UiAdaptor 인터페이스 구현체는 VO 변환시 RowStatus 인터페이스를 이용하여
 * 상태 정보를 저장함
 * 
 * @author 롯데프레임워크 
 *
 */
public interface RowStatus {

    public static final String COL_ROWSTATUS = "rowStatus";
    public static final Integer ROWTYPE_NORMAL = new Integer(1);
    public static final Integer ROWTYPE_INSERT = new Integer(2);//Dataset.ROWTYPE_INSERT; //2
    public static final Integer ROWTYPE_UPDATE = new Integer(4);//Dataset.ROWTYPE_UPDATE; //4
    public static final Integer ROWTYPE_DELETE = new Integer(8);//Dataset.ROWTYPE_DELETE; //8
    
    public static final String COL_ROWKEYVALUE = "rowKeyValue";
    
    public void setRowStatus(Integer rowStatus);
    public Integer getRowStatus();
    
    public void setRowKeyValue(String rowKeyValue);
    public String getRowKeyValue();
    
    
}
