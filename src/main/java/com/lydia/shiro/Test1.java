package com.lydia.shiro;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
public class Test1 {
	public static void main(String[] args) {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		// 2,实例化securityManager
		SecurityManager securityManager = factory.getInstance();
		// 3，使其以单例模式生效
		SecurityUtils.setSecurityManager(securityManager);
		// 获取当前操作的用户凭证
		Subject sub = SecurityUtils.getSubject();
		if (!sub.isAuthenticated()) {// 如果用户尚未验证凭证
			UsernamePasswordToken upt = new UsernamePasswordToken("jane", "123");// 进行登录
			upt.setRememberMe(true);// 记住我这个功能需要自己实现，没有内置的
			sub.login(upt);// 执行登录操作
		}
		System.out.println("当前登陆的用户为：" + sub.getPrincipal());
		//判断user表中是否有role表中的角色
		if (sub.hasRole("超级管理员")) {// 检查用户是否具备某种角色
			System.out.println("该用户具备超级管理员角色");
		} else {
			System.out.println("该用户不具备超级管理员角色");
		}
		//判断user表中是否有permission表中的权限
		if(sub.isPermitted("增加")){
			System.out.println("该用户有增加权限");
		}else{
			System.out.println("该用户没有增加权限");
		}
	}
}

