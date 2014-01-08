package lotte.fw.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
* @Class Name : BusinessException.java
* @Description : 시스템 오류 발생으로 개발자가 처리할 수 없는 예외인 경우 생성하는 Exception이다. 
 * LotteRuntimeException은 Unchecked Exception 이므로 명시적으로 던지지 않아도 된다.
 * 비즈니스 오류는 LotteException 을 참조한다.
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
public class LotteRuntimeException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private String logMessage;
    
    public LotteRuntimeException() {
        super();
    }
    
    public LotteRuntimeException(String msg) {
        super(msg);
    }
    
    public LotteRuntimeException(String msg, Throwable e) {
        super(msg, e);
    }
    
    public LotteRuntimeException(String msg, String logMessage) {
        super(msg);
        this.logMessage = logMessage;
    }
    
    public LotteRuntimeException(String msg, String logMessage, Throwable e) {
        super(msg, e);
        this.logMessage = logMessage;
    }
    
    public String getLogMessage() {
        return logMessage;
    }
 
    
	/**
	 * BaseException 생성자 
	 * @param wrappedException 발생한 Exception 내포함.
	 */
	public LotteRuntimeException(Throwable wrappedException) {
		this("LotteRuntimeException without message", null, wrappedException);
	}
    
    /**
	 * BaseException 생성자 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 */
	public LotteRuntimeException(MessageSource messageSource, String messageKey) {
		this(messageSource, messageKey, null, null, Locale.getDefault(), null);
	}

	/**
	 * BaseException 생성자 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 */
	public LotteRuntimeException(MessageSource messageSource, String messageKey, Throwable wrappedException) {
		this(messageSource, messageKey, null, null, Locale.getDefault(), wrappedException);
	}
	/**
	 * BaseException 생성자 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param locale 국가/언어지정
	 * @param wrappedException 발생한 Exception 내포함.
	 */
	public LotteRuntimeException(MessageSource messageSource, String messageKey, Locale locale, Throwable wrappedException) {
		this(messageSource, messageKey, null, null, locale, wrappedException);
	}
	/**
	 * BaseException 생성자 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param locale 국가/언어지정
	 * @param wrappedException 발생한 Exception 내포함.
	 */
	public LotteRuntimeException(MessageSource messageSource, String messageKey, Object[] messageParameters, Locale locale,
	        Throwable wrappedException) {
		this(messageSource, messageKey, messageParameters, null, locale, wrappedException);
	}
	/**
	 * BaseException 생성자 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param wrappedException 발생한 Exception 내포함.
	 */
	public LotteRuntimeException(MessageSource messageSource, String messageKey, Object[] messageParameters,
	        Throwable wrappedException) {
		this(messageSource, messageKey, messageParameters, null, Locale.getDefault(), wrappedException);
	}
	/**
	 * BaseException 생성자 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param defaultMessage 메세지 지정(변수지정)
	 * @param wrappedException 발생한 Exception 내포함.
	 */
	public LotteRuntimeException(MessageSource messageSource, String messageKey, Object[] messageParameters,
	        String defaultMessage, Throwable wrappedException) {
		this(messageSource, messageKey, messageParameters, defaultMessage, Locale.getDefault(), wrappedException);
	}
	
	/**
	 * BaseException 생성자 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param defaultMessage 메세지 지정(변수지정)
	 * @param locale 국가/언어지정
	 * @param wrappedException 발생한 Exception 내포함.
	 */
	public LotteRuntimeException(MessageSource messageSource, String messageKey, Object[] messageParameters,
	        String defaultMessage, Locale locale, Throwable wrappedException) {
		super(wrappedException);

		this.logMessage = messageSource.getMessage(messageKey, messageParameters, defaultMessage, locale);
	}
}
