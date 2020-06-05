package com.springmvc.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandler  implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (!(handler instanceof HandlerMethod)){
			return null;
		}
		HandlerMethod hand = (HandlerMethod)handler;
		if (!(hand.getBean() instanceof BaseController))	return null;
		boolean isAjax = false;
		if(hand.getMethod().isAnnotationPresent(ResponseBody.class))	isAjax = true;
		System.out.println(hand.getMethod().getReturnType().getTypeName().equals("void"));
		BaseController controller = (BaseController) hand.getBean();
		controller.setRequest(request);
		controller.setResponse(response);
		if(isAjax)
			try {
				controller.writeError(ex.getMessage());
			} catch (Exception e) {
				controller.log.error(e);
			}
		return null;
	}

}
