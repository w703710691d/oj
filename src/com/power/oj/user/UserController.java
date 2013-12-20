package com.power.oj.user;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import jodd.io.FileUtil;
import jodd.util.HtmlEncoder;
import jodd.util.StringBand;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.power.oj.admin.AdminInterceptor;
import com.power.oj.core.OjConfig;
import com.power.oj.core.OjConstants;
import com.power.oj.core.OjController;
import com.power.oj.core.OnlineListener;
import com.power.oj.user.interceptor.LoginInterceptor;
import com.power.oj.user.validator.SignupValidator;
import com.power.oj.user.validator.UpdateUserValidator;
import com.power.oj.util.FileKit;

/**
 * 
 * @author power
 * 
 */
public class UserController extends OjController
{
  public void index()
  {
    render("index.html");
  }

  @ActionKey("/login")
  public void login()
  {
    if (SecurityUtils.getSubject().isAuthenticated())
    {
      redirect(OjConfig.lastAccessURL, "You already login.", "error", "Error!");
      return;
    }

    setTitle("Login");

    boolean ajax = getParaToBoolean("ajax", false);
    if (ajax)
      render("ajax/login.html");
    else
      render("login.html");
  }

  @Before(POST.class)
  public void signin()
  {
    Subject currentUser = SecurityUtils.getSubject();
    if (currentUser.isAuthenticated())
    {
      redirect(OjConfig.lastAccessURL, "You already login.", "error", "Error!");
      return;
    }

    String name = getPara("name").trim();
    String password = getPara("password");
    UsernamePasswordToken token = new UsernamePasswordToken(name, password);
    token.setRememberMe(getParaToBoolean("rememberMe", false));

    try
    {
      currentUser.login(token);
      
      UserModel userModel = UserModel.dao.getUserByNameAndPassword(name, password);
      String token_token = UUID.randomUUID().toString();
      setCookie(OjConstants.TOKEN_NAME, name, OjConstants.TOKEN_AGE);
      if (getParaToBoolean("rememberMe", false))
        setCookie(OjConstants.TOKEN_TOKEN, token_token, OjConstants.TOKEN_AGE);

      userModel.updateLogin(token_token);
      Session session = currentUser.getSession(true);
      session.setAttribute(OjConstants.USER, userModel);

      int uid = userModel.getUid();
      if (userModel.isAdmin(uid))
        session.setAttribute(OjConstants.ADMIN_USER, uid);

      redirect(OjConfig.lastAccessURL);
      return;
    } catch (AuthenticationException ae)
    {
      log.warn("User signin failed.");
    }
   
      setAttr(OjConstants.MSG_TYPE, "error");
      setAttr(OjConstants.MSG_TITLE, "Error!");
      setAttr(OjConstants.MSG, "Sorry, you entered an invalid username or password.");
      keepPara("name");

      boolean ajax = getParaToBoolean("ajax", false);
      if (ajax)
        render("ajax/login.html");
      else
        render("login.html");
  }

  @Before(LoginInterceptor.class)
  @ActionKey("/logout")
  public void logout()
  {
    Subject currentUser = SecurityUtils.getSubject();
    UserModel userModel = getSessionAttr(OjConstants.USER);
    if (userModel != null)
    {
      userModel.set("token", null);
      userModel.update();
    }
    
    currentUser.logout();
    /*removeCookie(OjConstants.TOKEN_NAME);
    removeCookie(OjConstants.TOKEN_TOKEN);
    removeSessionAttr(OjConstants.USER);*/

    redirect(OjConfig.lastAccessURL);
  }

  public void profile()
  {
    String name = getPara(0);
    UserModel user = null;
    if (name == null)
    {
      user = getSessionAttr(OjConstants.USER);
      if (user == null)
      {
        redirect("/");
        return;
      }
    } else
    {
      user = UserModel.dao.getUserByName(name);
      if (user == null)
      {
        redirect("/");
        return;
      }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    setAttr("createTime", sdf.format(new Date(user.getInt("ctime") * 1000L)));
    setAttr("loginTime", sdf.format(new Date(user.getInt("login") * 1000L)));
    setAttr(OjConstants.USER, user);
    setAttr("userRank", UserModel.dao.getUserRank(user.getUid()));
    setTitle("User Profile");
    render("profile.html");
  }

  @Before(LoginInterceptor.class)
  public void avatar()
  {
    render("avatar.html");
  }

  @Before(
  { POST.class, LoginInterceptor.class })
  public void uploadAvatar()
  {
    UploadFile uploadFile = getFile("Filedata", "", 10 * 1024 * 1024, "UTF-8");
    UserModel user = getSessionAttr(OjConstants.USER);
    int uid = getParaToInt("uid", 0);

    if (uid != 0)
    {
      String ext = FileKit.getFileType(uploadFile.getOriginalFileName());
      String fileName = new StringBand(4).append(OjConfig.userAvatarPath).append(uid).append(".").append(user.getStr("avatar")).toString();

      try
      {
        FileUtil.deleteFile(fileName);
      } catch (IOException e)
      {
        log.warn(e.getLocalizedMessage());
      }

      fileName = new StringBand(3).append(OjConfig.userAvatarPath).append(uid).append(ext).toString();
      try
      {
        FileUtil.moveFile(uploadFile.getFile(), new File(fileName));
        user.set("avatar", ext.substring(1)).update();
        renderJson("FILEID:/oj/assets/images/user/" + uid + ext);
        return;
      } catch (IOException e)
      {
        log.error(e.getLocalizedMessage());
      }
    }

    renderJson("ERROR:true");
  }

  public void info()
  {
    int uid = 0;
    String name = "";
    UserModel user = null;

    if (isParaExists("uid"))
    {
      uid = getParaToInt("uid");
      user = UserModel.dao.getUserInfoByUid(uid);
    } else if (isParaExists("name"))
    {
      name = getPara("name");
      user = UserModel.dao.getUserInfoByName(name);
    }

    if (user == null)
    {
      renderJson("{error:true}");
    } else
    {
      user.remove("token").remove("pass").remove("realname").remove("phone").remove("data");
      renderJson(user);
    }
  }

  @ActionKey("/signup")
  public void signup()
  {
    if (getSessionAttr(OjConstants.USER) != null)// user already login
    {
      redirect("/");
      return;
    }

    setTitle("Signup");
    render("signup.html");
  }

  @Before(SignupValidator.class)
  public void save()
  {
    UserModel userModel = getModel(UserModel.class, "user");
    userModel.saveUser();

    userModel = userModel.findById(userModel.getUid());
    setSessionAttr(OjConstants.USER, userModel);

    redirect("/user/edit", "Congratulations!You have a new account now.<br>Please update your information.");
  }

  @Before(LoginInterceptor.class)
  public void edit()
  {
    setTitle("Account");
    UserModel user = UserModel.dao.findById(getAttr("userID"));
    setAttr(OjConstants.USER, user);
    setAttr(OjConstants.PROGRAM_LANGUAGES, OjConfig.program_languages);

    render("edit.html");
  }

  @Before(
  { LoginInterceptor.class, UpdateUserValidator.class })
  public void update()
  {
    UserModel userModel = getModel(UserModel.class, "user");
    userModel.updateUser();

    String redirectURL = new StringBand(2).append("/user/profile/").append(getAttr(OjConstants.USER_NAME)).toString();
    redirect(redirectURL, "The changes have been saved.");
  }

  public void delete()
  {
    renderText("TODO");
  }

  public void search()
  {
    String word = HtmlEncoder.text(getPara("word").trim());
    String scope = getPara("scope");
    setAttr(OjConstants.USER_LIST, UserModel.dao.searchUser(scope, word));
    setAttr("word", word);
    setAttr("scope", scope != null ? scope : "all");
    setTitle(new StringBand(2).append("Search user: ").append(word).toString());

    render("search.html");
  }

  /*
   * public void searchUser() { String word =
   * HtmlEncoder.text(getPara("word").trim()); String scope = getPara("scope");
   * int pageNumber = getParaToInt("p", 1); Page<UserModel> userList = null;
   * 
   * if(StringUtil.isNotBlank(word)) { StringBand sb = new
   * StringBand("FROM user WHERE "); if(StringUtil.isNotBlank(scope))
   * sb.append(scope).append(" LIKE '%").append(word).append("%' "); else
   * sb.append ("name LIKE '%").append(word).append("%' OR nick LIKE '%").append
   * (word).append("%' OR email LIKE '%").append(word).append("%'");
   * sb.append(" AND status=1 ORDER BY solved desc,submit,uid");
   * 
   * userList = UserModel.dao.paginate(pageNumber, 50,
   * "SELECT uid,name,nick,school,solved,submit", sb.toString()); }
   * 
   * renderJson(userList); }
   */

  @Before(AdminInterceptor.class)
  public void online()
  {
    setTitle("Online Users");
    setAttr("loginUserNum", Db.findFirst("SELECT COUNT(uid) AS count FROM session WHERE session_expires > UNIX_TIMESTAMP() AND uid>0 LIMIT 1").getLong("count"));
    // setAttr(OjConstants.USER_LIST, UserModel.dao.onlineUser());
    setAttr(OjConstants.USER_LIST, OnlineListener.getAccessLog());

    render("online.html");
  }

  @ActionKey("/rank")
  public void rank()
  {
    int pageNumber = getParaToInt("p", 1);
    int pageSize = getParaToInt("s", 20);
    Page<UserModel> userList = UserModel.dao.paginate(pageNumber, pageSize, "SELECT @rank:=@rank+1 AS rank,uid,name,nick,realname,solved,submit",
        "FROM user,(SELECT @rank:=?)r WHERE status=1 ORDER BY solved DESC,submit,uid", (pageNumber - 1) * pageSize);

    setTitle("Ranklist");
    setAttr(OjConstants.USER_LIST, userList);

    render("rank.html");
  }

  @Before(AdminInterceptor.class)
  public void build()
  {
    int uid = getParaToInt(0);
    UserModel user = UserModel.dao.findById(uid);

    if (user != null)
      user.build();

    String redirectURL = new StringBand(2).append("/user/profile/").append(user.getStr("name")).toString();
    redirect(redirectURL, "The user statistics have been saved.");
  }
}
