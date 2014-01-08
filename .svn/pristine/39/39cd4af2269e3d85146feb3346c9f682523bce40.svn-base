package lotte.fw.common.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Class Name : LogManageAspect.java
 * @Description :  User 정보가 있는 경우 User의 정보를 로그로 남긴다.
 * @Modification Information  
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
@Resource(name = "LogManageService")
public class LogManageAspect {
	private static Log log = LogFactory.getLog(LogManageAspect.class);
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);
	Date currentTime = new Date();

//	public Object writeAccessLogConsole(ProceedingJoinPoint call) throws Throwable {
//
//		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
//		WebAuthenticationDetails details = (WebAuthenticationDetails) currentUser.getDetails();
//		String address = details.getRemoteAddress();
//		Signature signature = call.getSignature();
//		StringBuffer sb = new StringBuffer();
//		sb.append(currentUser.getName()).append("\t");
//		sb.append(call.getTarget().getClass().getName()).append(".");
//		sb.append(signature.getName()).append("\t");
//
//		sb.append(address).append("\t");
//		sb.append(formatter.format(currentTime));
//		log.info(sb.toString());
//		return call.proceed();
//	}

//	public Object writeAccessLogDB(ProceedingJoinPoint call) throws Throwable {
//		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
//		UserLog userLog = new UserLog();
//		userLog.getUserId();
//		userLog.getClassName();
//		userLog.getMethodName();
//		userLog.getLogCurrentTime();
//
//		logManageService.logInsertSysLog(userLog);
//	}
}
