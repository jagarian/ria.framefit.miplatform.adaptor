package lotte.fw.tracelog;


import org.apache.log4j.spi.LoggingEvent;

/**
 * <PRE>
 * log4j의 PatternLayout을 확장한다.
 * logging시 특정한 정보(여기서는 request key)를 추가하기 위한 용도로 사용한다.
 * </PRE>
 * @Date     : 2009. 10. 29.
 * @작성자   : 이건희
 * @변경이력 : 
 */
public class PatternLayout extends org.apache.log4j.PatternLayout {
    /* (non-Javadoc)
     * @see org.apache.log4j.PatternLayout#format(org.apache.log4j.spi.LoggingEvent)
     */
    public String format(LoggingEvent event) {
        return LogKeyUtil.getLogKey() + super.format(event);
    }
}
