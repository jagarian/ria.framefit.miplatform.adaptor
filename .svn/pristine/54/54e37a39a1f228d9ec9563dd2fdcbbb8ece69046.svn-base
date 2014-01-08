package lotte.fw.web.ria;

import javax.servlet.http.HttpServletRequest;

/**
 * <PRE>
 * request를 적합한 형식으로 변환하여주는 인터페이스로 convert를 제공한다.
 * X-internet등을 사용하여 특정한 형식으로의 변환이 필요한 경우 본 interface를 구현한다.
 * </PRE>
 */
public interface UiAdaptor {

	/**
	 * request를 조작하여 원하는 data 형식으로 변환한다.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object convert(HttpServletRequest request) throws Exception;

	/**
	 * <PRE>
	 * convert로 변환한 Object의 Class를 반환한다.
	 * 이 Class는 controller의 argument 형식과 비교하여
	 * matching되는 경우에 convert method가 수행된다.
	 * <PRE>
	 * @return
	 */
	public Class getModelName();

}
