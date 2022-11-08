package Controller;

import Model.Login;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
@WebServlet(urlPatterns = "/giohang/*")
public class CheckLogin implements Filter {
    RequestDispatcher dispatcher = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (Login.user == null) {
            dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            if (Login.user.getChucvu().equals("admin")) {
                dispatcher = request.getRequestDispatcher("/admin.jsp");
                dispatcher.forward(request, response);
            } else {
                dispatcher = request.getRequestDispatcher("/giohang.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
