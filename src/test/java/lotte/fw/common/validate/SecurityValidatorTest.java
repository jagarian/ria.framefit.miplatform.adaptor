package lotte.fw.common.validate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SecurityValidatorTest {

	@Test
	public void testValidateCharAscString() {
		String validInput = "ABCDEF";
		String invalidInput = "ADFGTH";
		assertEquals("연속문자열", SecurityValidator.validateCharAscString(validInput), true);
		assertEquals("비연속문자열", SecurityValidator.validateCharAscString(invalidInput), false);
	}

	@Test
	public void testValidateCharDescString() {
		String validInput = "FEDCBA";
		String invalidInput = "ABCDEF";
		assertEquals("연속문자열", SecurityValidator.validateCharDescString(validInput), true);
		assertEquals("비연속문자열", SecurityValidator.validateCharDescString(invalidInput), false);
	}
	@Test
	public void testValidateCharSequenceString() {
		String validInput = "FEDCBA";
		String invalidInput = "ABCDEF";
		assertEquals("연속문자열", SecurityValidator.validateCharDescString(validInput), true);
		assertEquals("비연속문자열", SecurityValidator.validateCharDescString(invalidInput), false);
	}
	
	
	@Test
	public void testValidateCharDigitSix() {
		String validInput = "ABC3EF";
		String invalidInput1 = "ABCDEF";
		String invalidInput2 = "123456";
		String invalidInput3 = "abcDEF";
		assertEquals("문자와 숫자 조합으로 6자 이상 OK", SecurityValidator.validateCharDigitSix(validInput), true);
		assertEquals("문자와 숫자 조합으로 6자 이상 False", SecurityValidator.validateCharDigitSix(invalidInput1), false);
		assertEquals("문자와 숫자 조합으로 6자 이상 False", SecurityValidator.validateCharDigitSix(invalidInput2), false);
		assertEquals("문자와 숫자 조합으로 6자 이상 False", SecurityValidator.validateCharDigitSix(invalidInput3), false);
	}

	@Test
	public void testValidateCharRepeatation() {
		String validInput1 = "ABCCCF";
		String validInput2 = "CCCFAB";
		String validInput3 = "FABCCC";
		String invalidInput1 = "FEDDCBBA";
		String invalidInput2 = "<html><header><script>alert('hello')</script><body>hello</body></header></html>";
		assertEquals("동일 문자의 3회 이상 반복", SecurityValidator.validateCharRepeatation(validInput1), true);
		assertEquals("동일 문자의 3회 이상 반복", SecurityValidator.validateCharRepeatation(validInput2), true);
		assertEquals("동일 문자의 3회 이상 반복", SecurityValidator.validateCharRepeatation(validInput3), true);
		assertEquals("동일 문자의 3회 이상 반복 False", SecurityValidator.validateCharRepeatation(invalidInput1), false);
		assertEquals("동일 문자의 3회 이상 반복 False", SecurityValidator.validateCharRepeatation(invalidInput2), false);
	}

	@Test
	public void testValidateCrossSiteScripting() {
		String validInput1 = "<html><header><script>alert('hello')</script><body>hello</body></header></html>";
		String validInput2 = "<script>";
		String validInput3 = "<script><body>hello</body></header></html>";
		String validInput4 = "<html><header><script>";
		String invalidInput = "<html><header><body>hello</body></header></html>";

		assertEquals("XSS 문자열 포함", SecurityValidator.validateCrossSiteScripting(validInput1), true);
		assertEquals("XSS 문자열 포함", SecurityValidator.validateCrossSiteScripting(validInput2), true);
		assertEquals("XSS 문자열 포함", SecurityValidator.validateCrossSiteScripting(validInput3), true);
		assertEquals("XSS 문자열 포함", SecurityValidator.validateCrossSiteScripting(validInput4), true);
		assertEquals("XSS 문자열 없음", SecurityValidator.validateCrossSiteScripting(invalidInput), false);
	}

	@Test
	public void testValidateEmailAddress() {
		String validInput1 = "aaa@lotte.net";
		String validInput2 = "bbb@lotte.co.kr";
		String validInput3 = "bbb@mail.lotte.co.kr";
		String invalidInput1 = "3*@lotte.net";
		String invalidInput2 = "@lotte.net";
		String invalidInput3 = "aaa@lotte";
		String invalidInput4 = "aaa@lotte.";

		assertEquals("EmailAddress OK", SecurityValidator.validateEmailAddress(validInput1), true);
		assertEquals("EmailAddress OK", SecurityValidator.validateEmailAddress(validInput2), true);
		assertEquals("EmailAddress OK", SecurityValidator.validateEmailAddress(validInput3), true);
		assertEquals("EmailAddress False", SecurityValidator.validateEmailAddress(invalidInput1), false);
		assertEquals("EmailAddress False", SecurityValidator.validateEmailAddress(invalidInput2), false);
		assertEquals("EmailAddress False", SecurityValidator.validateEmailAddress(invalidInput3), false);
		assertEquals("EmailAddress False", SecurityValidator.validateEmailAddress(invalidInput4), false);
	}

	@Test
	public void testValidateIdentityNumber() {
		String validInput = "";
		String invalidInput = "7501101815113";
//		assertEquals("IdentityNumber OK", SecurityValidator.validateIdentityNumber(validInput), true);
		assertEquals("IdentityNumber False", SecurityValidator.validateIdentityNumber(invalidInput), false);
	}

	@Test
	public void testValidateKorean() {
		String validInput1 = "한";
		String validInput2 = "1ㄱ";
		String validInput3 = "eㅎ1";
		String validInput4 = "Korean & 한글";
		String validInput5 = "Korean & 한 GUL";
		String validInput6 = "한 GUL & Korean";
		String invalidInput1 = "1";
		String invalidInput2 = "e";		
		String invalidInput3 = "Korean & English";

		assertEquals("Korean OK", SecurityValidator.validateKorean(validInput1), true);
		assertEquals("Korean OK", SecurityValidator.validateKorean(validInput2), true);
		assertEquals("Korean OK", SecurityValidator.validateKorean(validInput3), true);
		assertEquals("Korean OK", SecurityValidator.validateKorean(validInput4), true);
		assertEquals("Korean OK", SecurityValidator.validateKorean(validInput5), true);
		assertEquals("Korean OK", SecurityValidator.validateKorean(validInput6), true);
		assertEquals("Korean False", SecurityValidator.validateKorean(invalidInput1), false);
		assertEquals("Korean False", SecurityValidator.validateKorean(invalidInput2), false);
		assertEquals("Korean False", SecurityValidator.validateKorean(invalidInput3), false);
	}

	@Test
	public void testValidateSameIdPwComparison() {
		assertEquals("ServerSideInclude OK", SecurityValidator.validateSameIdPwComparison("kschoi", "kschoi"), true);
		assertEquals("ServerSideInclude OK", SecurityValidator.validateSameIdPwComparison("kschoi", "kkshoi"), false);
	}

	@Test
	// WAS에서 SSI (Server Side Include) 설정을 하지 않을 것을 권고함
	public void testValidateServerSideInclude() {
		String validInput = "Server side include - date is <!--#echo var=\"DATE_LOCAL\" -->";
		String invalidInput = "Server side include - date is <!--echo var=\"DATE_LOCAL\" -->";
		assertEquals("ServerSideInclude OK", SecurityValidator.validateServerSideInclude(validInput), true);
		assertEquals("ServerSideInclude False", SecurityValidator.validateServerSideInclude(invalidInput), false);
	}

	@Test
	public void testValidateSpecialCharacter() { // !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
		String validInput = "Server side include - date is <!--#echo var=\"DATE_LOCAL\" -->";
		String invalidInput = "Server side include";
		assertEquals("ServerSideInclude OK", SecurityValidator.validateSpecialCharacter(validInput), true);
		assertEquals("ServerSideInclude False", SecurityValidator.validateSpecialCharacter(invalidInput), false);
	}

	@Test
	// PREPARE statement 를 권고함
	// 참조: http://unixwiz.net/techtips/sql-injection.html
	public void testValidateSQLInjection() {
		// String query =
		// "select fieldlist from table where field = 'injectedQuery'";

		String validInput1 = "anything' OR "; // where filed = 'anything' OR
		// 'x'='x'
		String validInput2 = "x' AND email IS NULL; --"; // where filed = 'x'
		// AND email IS
		// NULL; --'
		String validInput3 = "x' AND 1=(SELECT COUNT(*) FROM tabname); --"; // where
		// filed
		// =
		// 'x'
		// AND
		// 1=(SELECT
		// COUNT(*)
		// FROM
		// tabname);
		// --'
		String validInput4 = "x' OR full_name LIKE '%Bob%"; // where filed = 'x'
		// OR full_name LIKE
		// '%Bob%'
		String validInput5 = "x'; DROP TABLE members; --"; // where filed = 'x';
		// DROP TABLE
		// members; --'
		String validInput6 = "x'; INSERT INTO members ('email','passwd','login_id','full_name') "
				+ "VALUES ('steve@unixwiz.net','hello','steve','Steve Friedl');--";
		// where filed = 'x'; INSERT INTO members
		// ('email','passwd','login_id','full_name') VALUES
		// ('steve@unixwiz.net','hello','steve','Steve Friedl');--'
		String validInput7 = "23 OR 1=1"; // where filed = '23 OR 1=1'

		String invalidInput1 = "3";
		String invalidInput2 = "aaa\'ab";

		assertEquals("SQLInjection OK", SecurityValidator.validateSQLInjection(validInput1), true);
		assertEquals("SQLInjection OK", SecurityValidator.validateSQLInjection(validInput2), true);
		assertEquals("SQLInjection OK", SecurityValidator.validateSQLInjection(validInput3), true);
		assertEquals("SQLInjection OK", SecurityValidator.validateSQLInjection(validInput4), true);
		assertEquals("SQLInjection OK", SecurityValidator.validateSQLInjection(validInput5), true);
		assertEquals("SQLInjection OK", SecurityValidator.validateSQLInjection(validInput6), true);
		assertEquals("SQLInjection OK", SecurityValidator.validateSQLInjection(validInput7), true);
		assertEquals("SQLInjection False", SecurityValidator.validateSQLInjection(invalidInput1), false);
		assertEquals("SQLInjection False", SecurityValidator.validateSQLInjection(invalidInput2), false);
	}
}
