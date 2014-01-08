package lotte.fw.common.validate;
/**
 * @Class Name : SecurityValidator.java
 * @Description :  사용자 입력 데이터에 대한 보안 취약점 검증 수행을 위한 유틸리티를 제공함
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class SecurityValidator {
	/**
	 * 동일 문자의 3회 이상 반복을 점검한
	 * 
	 * @param input
	 * @return boolean 동일 문자 3회 이상 반복 유무(true-3회 이상 반복, false-그외)
	 */
	public static final boolean validateCharRepeatation(String[] input) {
		boolean result = false;

		for (int i = 0; i < input.length; ++i) {
			String temp = input[i];
			result = validateCharRepeatation(temp);
			if (result)
				return result;
		}
		return result;
	}

	/**
	 * 동일 문자의 3회 이상 반복을 점검한
	 * 
	 * @param input
	 * @return boolean 동일 문자 3회 이상 반복 유무(true-3회 이상 반복, false-그외)
	 */
	public static final boolean validateCharRepeatation(String input) {
		int count = 1;
		int pos = 1;

		char prevChar = input.charAt(0);
		while (pos < input.length()) {
			char subChar = input.charAt(pos);
			if (subChar == prevChar)
				count++;
			else
				count = 1;

			if (count >= 3)
				return true;
			prevChar = subChar;
			pos++;
		}

		return false;
	}

	/**
	 * 문자와 숫자 조합으로 6자 이상을 검증함
	 * 
	 * @param input
	 * @return boolean 문자와 숫자 조합으로 6자 이상 문자열 유무(true-문자와 숫자 조합 6자 이상, false-그외)
	 */
	public static final boolean validateCharDigitSix(String[] input) {
		boolean result = false;

		for (int i = 0; i < input.length; ++i) {
			String temp = input[i];

			result = validateCharDigitSix(temp);
			if (result)
				return true;
		}
		return result;
	}

	/**
	 * 문자와 숫자 조합으로 6자 이상을 검증함
	 * 
	 * @param input
	 * @return boolean 문자와 숫자 조합으로 6자 이상 문자열 유무(true-문자와 숫자 조합 6자 이상, false-그외)
	 */
	// 6자 이상 문자/숫자
	private static final Pattern AlphaNumericSixPattern = Pattern.compile("\\p{Alnum}{5,}\\p{Alnum}");
	// 문자 반복
	private static final Pattern AllAlphaPattern = Pattern.compile("\\p{Alpha}+");
	// 숫자 반복
	private static final Pattern AllDigitPattern = Pattern.compile("\\p{Digit}+");

	public static final boolean validateCharDigitSix(String input) {
		return AlphaNumericSixPattern.matcher(input).matches() && !AllAlphaPattern.matcher(input).matches()
				&& !AllDigitPattern.matcher(input).matches();
	}

	/**
	 * 동일 아이디와 비밀번호인지 점검함
	 * @param id
	 * @param pw
	 * @return true -아이디와 비밀번호 일치함, false-아이디와 비밀번호 일치하지 않음
	 */
	public static final boolean validateSameIdPwComparison(String id, String pw) {
		return id.equals(pw);
	}

	/**
	 * 연속된 증가 혹은 감소 문자열인지 점검함
	 * @param input
	 *            점검대상 문자열
	 * @return boolean 연속된 증가 혹은 감소 문자열 포함 유무(true-연속 문자열, false-비연속 문자열)
	 */
	public static final boolean validateCharSequenceString(String[] input) {
		for (int i = 0; i < input.length; ++i) {
			if (validateCharSequenceString(input[i]))
				return true;
		}
		return false;
	}

	/**
	 * 연속된 증가 혹은 감소 문자열인지 점검함
	 * 
	 * @param input
	 *            점검대상 문자열
	 * @return boolean 연속된 증가 혹은 감소 문자열 유무(true-연속 문자열, false-비연속 문자열)
	 */
	public static final boolean validateCharSequenceString(String input) {
		return validateCharAscString(input) || validateCharDescString(input);
	}
	/**
	 * 연속된 증가 문자열인지 점검함
	 * @param input
	 *            점검대상 문자열
	 * @return boolean 연속된 증가 문자열 유무(true-연속 문자열, false-비연속 문자열)
	 */
	public static final boolean validateCharAscString(String[] input) {
		for (int i = 0; i < input.length; ++i) {
			if (validateCharAscString(input[i]))
				return true;
		}
		return false;
	}

	/**
	 * 연속된 증가 문자열인지 점검함
	 * 
	 * @param input
	 *            점검대상 문자열
	 * @return boolean 연속된 증가 문자열 유무(true-연속 문자열, false-비연속 문자열)
	 */
	public static final boolean validateCharAscString(String input) {
		boolean result = true;

		for (int j = 0; (j < input.length() - 1) && (result); ++j) {
			int frontChar = input.charAt(j);
			int endChar = input.charAt(j + 1);
			if (frontChar + 1 == endChar)
				result = true;
			else {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 연속된 감소 문자열 포함 유무인지 점검함
	 * 
	 * @param input
	 *            점검대상 문자열
	 * @return boolean 연속된 감소 문자열 유무(true-연속 문자열, false-비연속 문자열)
	 */
	public static final boolean validateCharDescString(String[] input) {
		for (int i = 0; i < input.length; ++i) {
			if (validateCharDescString(input[i]))
				return true;
		}
		return false;
	}

	/**
	 * 연속된 감소 문자열인지 점검함
	 * 
	 * @param input
	 *            점검대상 문자열
	 * @return boolean 연속된 감소 문자열 유무(true-연속 문자열, false-비연속 문자열)
	 */
	public static final boolean validateCharDescString(String input) {
		boolean result = true;

		for (int j = 0; (j < input.length() - 1) && (result); ++j) {
			int frontChar = input.charAt(j);
			int endChar = input.charAt(j + 1);
			if (frontChar - 1 == endChar)
				result = true;
			else {
				result = false;
			}
		}
		return result;
	}

	/**
	 * XSS(Cross Site Scripting) 에 해당하는 문자열을 점검함
	 * 
	 * @param input
	 * @return XSS 문자열 존재 유무 (true-XSS문자열 존재, false-XSS문자열 없음)
	 */
	public static final boolean validateCrossSiteScripting(String[] input) {
		for (int i = 0; i < input.length; ++i) {
			if (validateCrossSiteScripting(input[i]))
				return true;
		}
		return false;
	}

	/**
	 * XSS(Cross Site Scripting) 에 해당하는 문자열을 점검함
	 * 
	 * @param input
	 * @return XSS 문자열 존재 유무 (true-XSS문자열 존재, false-XSS문자열 없음)
	 */
	private static final Pattern CrossSiteScriptingPattern = Pattern.compile(".*\\p{Print}script\\p{Print}.*");

	public static final boolean validateCrossSiteScripting(String input) {
		return CrossSiteScriptingPattern.matcher(input).matches();
	}

	/**
	 * 유효 이메일 주소인지를 점검함
	 * 
	 * @param input
	 * @return 유효 이메일 주소 여부 (true-유효 이메일 주소, false-유효하지 않은 이메일 주소)
	 */
	public static final boolean validateEmailAddress(String[] input) {
		for (int i = 0; i < input.length; ++i) {
			if (validateEmailAddress(input[i]) == false)
				return false;
		}
		return true;
	}

	/**
	 * 유효 이메일 주소인지를 점검함
	 * 
	 * @param input
	 * @return 유효 이메일 주소 여부 (true-유효 이메일 주소, false-유효하지 않은 이메일 주소)
	 * 
	 */
	private static final Pattern EmailAddressPattern = Pattern.compile("^\\p{Alnum}+@\\p{Alnum}+(\\.\\p{Alnum}+)+$");

	public static final boolean validateEmailAddress(String input) {
		return EmailAddressPattern.matcher(input).matches();
	}

	/**
	 * SQL Injection 에 해당하는 문자열을 점검함
	 * 
	 * @param input
	 * @return SQL Injection 에 해당하는 문자열 존재 유무(true-SQL Injection 문자열이 있음,
	 *         false-SQL Injection 문자열이 없음)
	 */
	public static final boolean validateSQLInjection(String[] input) {
		for (int i = 0; i < input.length; ++i) {
			if (validateSQLInjection(input[i]) == false)
				return false;
		}
		return true;
	}

	/**
	 * SQL Injection 에 해당하는 문자열을 점검함
	 * 
	 * 단, PREPARED statement 을 사용함으로써 SQL Injection을 근원적으로 차단할 것를 권고함
	 * 
	 * @param input
	 * @return SQL Injection 에 해당하는 문자열 존재 유무(true-SQL Injection 문자열이 있음,
	 *         false-SQL Injection 문자열이 없음) 
	 */
	private static final Pattern SQLInjectionPattern1 = Pattern.compile("^.*'\\s*(or|OR|and|AND|;)\\s*.*$");
	private static final Pattern SQLInjectionPattern2 = Pattern
			.compile("^.*\\s*(or|OR)\\s*\\p{Digit}*(=)\\p{Digit}*\\s*.*$");

	public static final boolean validateSQLInjection(String input) {
		return SQLInjectionPattern1.matcher(input).matches() || SQLInjectionPattern2.matcher(input).matches();
	}

	/**
	 * Server Side Include 에 해당하는 문자열을 점검함
	 * 
	 * @param input
	 * @return Server Side Include 에 해당하는 문자열 존재 유무
	 * 			(true-SQL Injection 문자열이 있음, false-SQL Injection 문자열이 없음)
	 */
	public static final boolean validateServerSideInclude(String[] input) {
		for (int i = 0; i < input.length; ++i) {
			if (validateServerSideInclude(input[i])) return true;
		}
		return false;
	}

	/**
	 * Server Side Include 에 해당하는 문자열을 점검함
	 * 
	 * 단, WAS에서 SSI (Server Side Include) 설정을 하지 않음으로써, SSI를 근원적으로 차단할 것을 권고함
	 * 
	 * @param input
	 * @return Server Side Include 에 해당하는 문자열 존재 유무
	 * 			(true-SQL Injection 문자열이 있음, false-SQL Injection 문자열이 없음)
	 */
	private static final Pattern ServerSideIncludePattern = Pattern.compile(".*<!--#\\p{Alnum}.*");

	public static final boolean validateServerSideInclude(String input) {
		return ServerSideIncludePattern.matcher(input).matches();
	}

	/**
	 * 특수 문자를 체크함 (!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~)
	 * 
	 * @param input
	 * @return true-특수문자 포함함, false-특수문자 포함하지 않음
	 */
	public static final boolean validateSpecialCharacter(String[] input) {
		for (int i = 0; i < input.length; ++i) {
			if (validateSpecialCharacter(input[i]))
				return true;
		}
		return false;
	}

	/**
	 * 특수 문자를 체크함 (!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~)
	 * 
	 * @param input
	 * @return
	 */
	private static final Pattern SpecialCharacterPattern = Pattern.compile(".*\\p{Punct}.*");

	public static final boolean validateSpecialCharacter(String input) {
		return SpecialCharacterPattern.matcher(input).matches();
	}

	/**
	 * 주민등록번호 유효성 체크
	 * 
	 * @param value
	 * @return true - 유효 주민번호, false - 유효하지 않는 주민번호
	 */
	public static boolean validateIdentityNumber(String value) {

		// 값의 길이가 13자리이며, 7번째 자리가 1,2,3,4 중에 하나인지 check.
		String regex = "\\d{6}[1234]\\d{6}";
		if (!value.matches(regex)) {
			return false;
		}

		// 앞 6자리의 값이 유효한 날짜인지 check.
		try {

			String strDate = value.substring(0, 6);
			strDate = ((value.charAt(6) == '1' || value.charAt(6) == '2') ? "19" : "20") + strDate;
			strDate = strDate.substring(0, 4) + "/" + strDate.substring(4, 6) + "/" + strDate.substring(6, 8);

			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = dateformat.parse(strDate);
			String resultStr = dateformat.format(date);

			if (!resultStr.equals(strDate)) {
				return false;
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		// 주민등록번호 마지막 자리를 이용한 check.
		char[] charArray = value.toCharArray();
		long sum = 0;
		int[] arrDivide = new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5 };
		for (int i = 0; i < charArray.length - 1; i++) {
			sum += Integer.parseInt(String.valueOf(charArray[i])) * arrDivide[i];
		}

		int checkdigit = (int) ((int) (11 - sum % 11)) % 10;

		return (checkdigit == Integer.parseInt(String.valueOf(charArray[12]))) ? true : false;
	}

	/**
	 * 한글여부 체크
	 * 
	 * @param value
	 * @return true - 한글포함, false - 한글 포함하지 않음
	 */
	public static boolean validateKorean(String value) {
		for (int i = 0; i < value.length(); i++) {

			char c = value.charAt(i);

			// korean : one character (consonant or vowel)
			if ((0xAC00 <= c && c <= 0xD7A3) || (0x3131 <= c && c <= 0x318E)) {
				return true;
			} else if ((0x61 <= c && c <= 0x7A) || (0x41 <= c && c <= 0x5A)) {
				// english
			} else if (0x30 <= c && c <= 0x39) {
				// integer/decimal
			} else {
				// unknown
			}
		}

		return false;
	}

}
