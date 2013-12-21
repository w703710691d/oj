package com.power.oj.user;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.jfinal.log.Logger;
import com.power.oj.core.OjConstants;

public class UserService
{
  private static final Logger log = Logger.getLogger(UserService.class);
  private static final UserService userService = new UserService();
  private UserModel dao = UserModel.dao;
  
  public static boolean login(String name, String password, boolean rememberMe)
  {
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    UsernamePasswordToken token = new UsernamePasswordToken(name, password);
    token.setRememberMe(rememberMe);

    try
    {
      currentUser.login(token);
      
      UserModel userModel = (UserModel) currentUser.getPrincipal();
      
      int uid = userModel.getUid();
      if (userModel.isAdmin(uid))
        session.setAttribute(OjConstants.ADMIN_USER, uid);

    } catch (AuthenticationException ae)
    {
      log.warn("User signin failed.");
      return false;
    }
    
    return true;
  }
  /*
  public static boolean autoLogin()
  {
    Subject currentUser = SecurityUtils.getSubject();
    if (currentUser.isRemembered())
    {
      UserModel userModel = (UserModel) currentUser.getPrincipal();
      UsernamePasswordToken token = new UsernamePasswordToken(userModel.getStr("name"), userModel.getStr("pass"));
      token.setRememberMe(true);

      currentUser.login(token);
    }
    
    return true;
  }
  */
  public static void logout()
  {
    Subject currentUser = SecurityUtils.getSubject();
    UserModel userModel = (UserModel) currentUser.getPrincipal();
    if (userModel != null)
    {
      userModel.set("token", null);
      userModel.update();
    }
    
    currentUser.logout();
  }

  public static Subject getCurrentUser()
  {
    return SecurityUtils.getSubject();
  }
  
  public static UserModel getPrincipal()
  {
    Object principal = getCurrentUser().getPrincipal();
    if (principal instanceof UserModel)
    {
      return (UserModel) principal;
    }
    
    if (principal != null)
      log.warn(principal.toString());
    
    return null;
  }
  
  public static boolean isUser()
  {
    return getCurrentUser() != null && getCurrentUser().getPrincipal() != null;
  }
  
  public static boolean isGuest()
  {
    return !isUser();
  }
  
}