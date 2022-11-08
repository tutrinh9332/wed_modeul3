package Controller;

import Dao.UserDao;
import Model.Login;
import Model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Login.user != null) {
            if (Login.user.getChucvu().equals("admin")) {
                resp.sendRedirect("/admin.jsp");
            } else {
                resp.sendRedirect("/menu.jsp");
            }
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
            dispatcher.forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        String password = req.getParameter("pass");

        User user = userDao.getUser(username, password);
        if (user != null) {

            Login.user = user;
            if (user.getChucvu().equals("admin")) {
                resp.sendRedirect("/admin.jsp");
            } else {
                resp.sendRedirect("/menu.jsp");
            }
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
            dispatcher.forward(req, resp);
        }
    }


}
