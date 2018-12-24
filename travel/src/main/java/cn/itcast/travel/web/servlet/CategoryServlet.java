package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    public void findCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService service=new CategoryServiceImpl();
        String json=service.findCategory();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(json);
    }
}