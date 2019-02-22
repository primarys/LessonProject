package com.project.interceptor.teacher;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.project.common.WebApplication;
import com.project.utils.Const;
import com.project.utils.ValidateUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.servlet.http.HttpSession;

/**
 * 
 * @author Dobility
 *
 */
@ParentPackage("teachers")
@Namespace("/manage/teacher")
public class TeacherInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		//登录拦截
		String url = WebApplication.getRequest().getRequestURI();
		String regex = ".*/manage/teacher/.*";
		if(ValidateUtils.check(regex, url)){
			HttpSession session = WebApplication.getSession();
			if(session.getAttribute(Const.USER_TEACHER) == null){
				WebApplication.getResponse().sendRedirect(WebApplication.getRequest().getContextPath()+"/login");
				return null;
			}
		}
		return actionInvocation.invoke();
	}

}
