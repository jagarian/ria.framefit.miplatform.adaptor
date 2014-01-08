package lotte.fw.tracelog;


/**
 * <PRE>
 * log 맨 앞에 key를 할당하기 위해 thread local 변수를 처리하는 class
 * </PRE>
 * @Date     : 2009. 10. 28.
 * @작성자   : 이건희
 * @변경이력 : 
 */
public final class LogKeyUtil {
    
    private LogKeyUtil() {}
    
    //log key
    private static int logKey = 0;
    
    /** 
     * thread Local 변수
     */
    private static ThreadLocal<String> threadLogKey = new ThreadLocal<String>();
    
    /**
     * thread에 세팅된 logKey를 가져온다.
     * @author     : 이건희
     * @return
     */
    public static String getLogKey() {
        
        String key = threadLogKey.get();
        if(key == null) {
            key = "";
        }
        
        return key;
    }
    
    /**
     * logKey를 thread변수로 지정한다.
     * @author     : 이건희
     * @param txMode
     */
    public static void setLogKey(final String key) {
        threadLogKey.set(key);
    }
    
    /**
     * logKey를 생성하여 thread변수로 지정한다.
     * @author     : 이건희
     * @param txMode
     */
    public static void startLogKey() {
        setLogKey(System.getProperty("a1.machine.name") + makeLogKey() + " ");
    }
    
    /**
     * logKey를 초기화한다.
     * @author     : 이건희
     */
    public static void clearLogKey() {
        threadLogKey.set(null);
    }
    
    /**
     * log key를 증가하여 return 한다.
     * @author     : 이건희
     * @return
     */
    public synchronized static int makeLogKey() { // NOPMD by gunhlee on 09. 11. 3 오후 5:01
        if(logKey < Integer.MAX_VALUE) {
            logKey++;
        } else {
            logKey = 0;
        }
        return logKey;
    }
}
