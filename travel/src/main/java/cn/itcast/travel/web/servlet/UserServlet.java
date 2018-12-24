package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        ResultInfo info=new ResultInfo();
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check) ){
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(info,response);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        User registUser =new User();
        try {
            BeanUtils.populate(registUser,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service=new UserServiceImpl();
        Boolean flag=service.regist(registUser);
        if(flag){
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        writeValue(info,response);
    }
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         response.setContentType("text/html;charset=utf-8");
        String code = request.getParameter("code");
        if(code!=null){
            UserService service=new UserServiceImpl();
            User user=service.activeUser(code);
            if(user==null){
                response.getWriter().write("激活失败");
            }else {
                response.getWriter().write("激活成功,请<a href='http://localhost:80/travel/login.html'>登录</a>");

            }
        }
    }
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        ResultInfo info=new ResultInfo();
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check) ){
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(info,response);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        User loginUser =new User();
        try {
            BeanUtils.populate(loginUser,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service=new UserServiceImpl();
        User user=service.login(loginUser);
        if(user==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        if(user!=null && "N".equals(user.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("你还没有激活,请激活");
        }
        if(user!=null && "Y".equals(user.getStatus())){
            request.getSession().setAttribute("user",user);
            info.setFlag(true);
        }
        writeValue(info,response);
        }
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        writeValue(user,response);
    }
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}