package lotte.fw.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;


/**
* @Class Name : BusinessException.java
* @Description : fetch 하는 data가 propertyDao에서 지정한 maxFetchingsize를 초과시 발생하는 exception
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
public class MaxFetchingException extends LotteException {
    
    private static final long serialVersionUID = 1L;
    
    public MaxFetchingException() {
        super();
    }
    
    public MaxFetchingException(String msg) {
        super(msg);
    }
    
    public MaxFetchingException(String msg, Throwable e) {
        super(msg, e);
    }
    
  
	public MaxFetchingException(MessageSource messageSource, String messageKey) {
		super(messageSource, messageKey, null, null, Locale.getDefault(), null);
	}

	public MaxFetchingException(MessageSource messageSource, String messageKey, Throwable wrappedException) {
		super(messageSource, messageKey, null, null, Locale.getDefault(), wrappedException);
	}

	public MaxFetchingException(MessageSource messageSource, String messageKey, Locale locale, Throwable wrappedException) {
		super(messageSource, messageKey, null, null, locale, wrappedException);
	}

	public MaxFetchingException(MessageSource messageSource, String messageKey, Object[] messageParameters, Locale locale,
	        Throwable wrappedException) {
		super(messageSource, messageKey, messageParameters, null, locale, wrappedException);
	}

	public MaxFetchingException(MessageSource messageSource, String messageKey, Object[] messageParameters,
	        Throwable wrappedException) {
		super(messageSource, messageKey, messageParameters, null, Locale.getDefault(), wrappedException);
	}

	public MaxFetchingException(MessageSource messageSource, String messageKey, Object[] messageParameters,
	        String defaultMessage, Throwable wrappedException) {
		super(messageSource, messageKey, messageParameters, defaultMessage, Locale.getDefault(), wrappedException);
	}
}
