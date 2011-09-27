package org.supermy.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;


public class SecurityUtils {
	
	private static final SecurityUtils instance=new SecurityUtils();
	

	   /**
	 * @return the instance
	 */
	public static SecurityUtils getInstance() {
		return instance;
	}

	/**  
  * 设置SpringSecurity的登录用户权限。  
  *  
  * @param ctx  
  * @param auths  
  */   
 public static void setAuthToken(ProviderManager providerManager, GrantedAuthority[] auths) {   
     TestingAuthenticationToken token = new TestingAuthenticationToken(   
             "admin", "test", auths);   
     // Override the regular spring configuration   
//     ProviderManager providerManager = (ProviderManager) ctx   
//             .getBean("authenticationManager");   
     List<AuthenticationProvider> list = new ArrayList<AuthenticationProvider>();   
     TestingAuthenticationProvider testingAuthenticationProvider = new TestingAuthenticationProvider();   
     list.add(testingAuthenticationProvider);   
     providerManager.setProviders(list);   

     // Create and store the SpringSecurity SecureContext into the   
     // SecurityContextHolder.   
     SecurityContextImpl secureContext = new SecurityContextImpl();   
     secureContext.setAuthentication(token);   
     SecurityContextHolder.setContext(secureContext);   
     
 }   

 public static void setSimpleAuthToken(String user,String passwd) {        
     SecurityContextHolder.getContext().setAuthentication(
             new UsernamePasswordAuthenticationToken(user,passwd));
 }
 
 public static Object getSimplePrincipal() {        
	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 return principal;
 }
 
 
// /**  
//  * 构造权限组  
//  *  
//  * @param rolePrivileges  
//  * @return  
//  */   
// public static GrantedAuthority[] generateAuthorities(   
//         Collection<RbacRoleResource> privileges) {   
//     GrantedAuthority[] auths = new GrantedAuthority[privileges.size()];   
//     if (!privileges.isEmpty()) {   
//         int count = 0;   
//         for (RbacRoleResource rolePrivilege : privileges) {   
//             String privilegeName = rolePrivilege.getResource().getName();   
//             GrantedAuthority authority = new GrantedAuthorityImpl(   
//                     privilegeName);   
//             auths[count] = authority;   
//             count++;   
//         }   
//     }   
//     return auths;   
// } 

 /**  
  * 构造权限组  
  *  
  * @param rolePrivileges  
  * @return  
  */   
 public static GrantedAuthority[] generateAuthorities(String[]  privileges) {   
     GrantedAuthority[] auths = new GrantedAuthority[privileges.length];   
         int count = 0;   
         for (String rolePrivilege : privileges) {   
//             String privilegeName = rolePrivilege.getResource().getName();   
             GrantedAuthority authority = new GrantedAuthorityImpl(   
            		 rolePrivilege);   
             auths[count] = authority;   
             count++;   
         }   
     return auths;   
 }  
 
}
