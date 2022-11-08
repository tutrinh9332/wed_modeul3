package Controller;

import Dao.SanPhamDao;
import Model.SanPham;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@WebServlet(urlPatterns = "/sanpham")
public class SanPhamServlet extends HttpServlet {
    SanPhamDao sanPhamDao = new SanPhamDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    createSanpham(request, response);
                    break;
                case "edit":
                    editSanpham(request, response);
                    break;
                case "delete":
                    deleteSanpham(request, response);
                    break;
            }
        }catch (SQLException | ParseException ex){
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    showDeleteForm(request, response);
                    break;
                default:
                    listStudent(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<SanPham> listSanpham = sanPhamDao.getAll();

        request.setAttribute("listSanpham", listSanpham);
        RequestDispatcher dispatcher = request.getRequestDispatcher("web/list.jsp");
        dispatcher.forward(request,response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("web/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        SanPham existingSanpham= sanPhamDao.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("web/edit.jsp");
        request.setAttribute("sanpham", existingSanpham);
        dispatcher.forward(request,response);
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        SanPham existingSanpham = sanPhamDao.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("web/delete.jsp");
        request.setAttribute("sanpham", existingSanpham);
        dispatcher.forward(request, response);
    }

    private void createSanpham(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ParseException {
        String tensp = request.getParameter("tensp");
        String dvt = request.getParameter("dvt");
        String mota = request.getParameter("mota");
        Float gia = Float.valueOf(request.getParameter("gia"));
        String img = request.getParameter("img");
        String loaisp = request.getParameter("loaisp");
        SanPham sanpham = new SanPham(tensp, dvt, mota, gia, img, loaisp);
        sanPhamDao.create(sanpham);
        listStudent(request,response);
    }

    private void editSanpham(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ParseException {
        int idsp = Integer.parseInt(request.getParameter("id"));
        String tensp = request.getParameter("tensp");
        String dvt = request.getParameter("dvt");
        String mota = request.getParameter("mota");
        Float gia = Float.valueOf(request.getParameter("gia"));
        String img = request.getParameter("img");
        String loaisp = request.getParameter("loaisp");
        SanPham sanpham = new SanPham(idsp, tensp, dvt, mota, gia, img, loaisp);
        sanPhamDao.edit(idsp,sanpham);
        listStudent(request,response);
    }

    private void deleteSanpham(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        sanPhamDao.delete(id);
        listStudent(request,response);
    }
}
