package Controller;

import Dao.UserDao;
import Model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegisterServelet extends HttpServlet {
    UserDao userDao = new UserDao();
    User user = null;
    RequestDispatcher dispatcher;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String tk = req.getParameter("tk");
        String mk = req.getParameter("mk");
        String chucvu = req.getParameter("chucvu");
        String sdt = req.getParameter("sdt");
        String ten = req.getParameter("ten");
        String gioitinh = req.getParameter("gioitinh");
        String diachi = req.getParameter("diachi");
        user = new User(tk, mk, chucvu, sdt, ten, gioitinh, diachi);
        if (userDao.create(user)) {
            resp.sendRedirect("/login");
        }else {
            req.setAttribute("message", "Tạo tài khoản không thành công, vui lòng thử lại");
            dispatcher = req.getRequestDispatcher("userView/register.jsp");
            dispatcher.forward(req, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatcher = req.getRequestDispatcher("userView/register.jsp");
        dispatcher.forward(req, resp);
    }
}
