package Controller;

import Model.Login;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        RequestDispatcher dispatcher = null;
        switch (action) {
            case "logout":
                Login.user = null;
                resp.sendRedirect("/index.jsp");
                break;
            case "search":
                break;
            case "giohang":
                dispatcher = req.getRequestDispatcher("/giohang.jsp");
                dispatcher.forward(req,resp);
                break;
            default:
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
