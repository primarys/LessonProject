package com.project.interceptor.admin;

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
@ParentPackage("admins")
@Namespace("/manage/admin")
public class AdminInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		//登录拦截
		String url = WebApplication.getRequest().getRequestURI();
		String regex = ".*/manage/admin/.*";
		if(ValidateUtils.check(regex, url)){
			HttpSession session = WebApplication.getSession();
			if(session.getAttribute(Const.USER_ADMIN) == null){
				WebApplication.getResponse().sendRedirect(WebApplication.getRequest().getContextPath()+"/login");
				return null;
			}
		}
		return actionInvocation.invoke();
	}

}
