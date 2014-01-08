package lotte.fw.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
* @Class Name : BusinessException.java
* @Description : 권한이 없는경우 발생시키는 Exception이다.
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
public class NoAuthorityException extends LotteException {
    
    private static final long serialVersionUID = 1L;
    
    public NoAuthorityException() {
        super();
    }
    
    public NoAuthorityException(String msg) {
        super(msg);
    }
    
    public NoAuthorityException(String msg, Throwable e) {
        super(msg, e);
    }

    
	public NoAuthorityException(MessageSource messageSource, String messageKey) {
		super(messageSource, messageKey, null, null, Locale.getDefault(), null);
	}

	public NoAuthorityException(MessageSource messageSource, String messageKey, Throwable wrappedException) {
		super(messageSource, messageKey, null, null, Locale.getDefault(), wrappedException);
	}

	public NoAuthorityException(MessageSource messageSource, String messageKey, Locale locale, Throwable wrappedException) {
		super(messageSource, messageKey, null, null, locale, wrappedException);
	}

	public NoAuthorityException(MessageSource messageSource, String messageKey, Object[] messageParameters, Locale locale,
	        Throwable wrappedException) {
		super(messageSource, messageKey, messageParameters, null, locale, wrappedException);
	}

	public NoAuthorityException(MessageSource messageSource, String messageKey, Object[] messageParameters,
	        Throwable wrappedException) {
		super(messageSource, messageKey, messageParameters, null, Locale.getDefault(), wrappedException);
	}

	public NoAuthorityException(MessageSource messageSource, String messageKey, Object[] messageParameters,
	        String defaultMessage, Throwable wrappedException) {
		super(messageSource, messageKey, messageParameters, defaultMessage, Locale.getDefault(), wrappedException);
	}
}
