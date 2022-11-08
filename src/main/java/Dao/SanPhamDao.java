package Dao;

import Connect.ConnectMySQL;
import Model.SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDao implements CRUD<SanPham> {

    private static final String INSERT_SANPHAM_SQL = "INSERT INTO sanpham (tensp,dvt,mota,gia,img,loaisp) VALUES (?,?,?,?,?,?);";
    private static final String SELECT_SANPHAM_BY_ID = "SELECT tensp,dvt,mota,gia,img,loaisp from sanpham where idsp= ?";
    private static final String SELECT_ALL_SANPHAM = "SELECT * from sanpham";
    private static final String DELETE_SANPHAM_SQL = "DELETE from sanpham where idsp=?;";
    private static final String UPDATE_SANPHAM_SQL = "UPDATE sanpham set tensp = ?,dvt = ?,mota = ?,gia = ?,img = ?,loaisp= ? where idsp = ?;";


    @Override
    public List<SanPham> getAll() {
        List<SanPham> sanphams = new ArrayList<>();

        ConnectMySQL ConnectMySql;
        try (Connection connection = ConnectMySQL.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SANPHAM);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int idsp = rs.getInt("idsp");
                String tensp = rs.getString("tensp");
                String dvt = rs.getString("dvt");
                String mota = rs.getString("mota");
                Float gia = Float.valueOf(rs.getString("gia"));
                String img = rs.getString("img");
                String loaisp = rs.getString("loaisp");

                sanphams.add(new SanPham(idsp, tensp, dvt, mota, gia, img, loaisp));
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return sanphams;
    }


    @Override
    public boolean create(SanPham sanpham) {
        try (Connection connection = ConnectMySQL.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SANPHAM_SQL);
            preparedStatement.setString(1, sanpham.getTensp());
            preparedStatement.setString(2, sanpham.getDvt());
            preparedStatement.setString(3, sanpham.getMota());
            preparedStatement.setFloat(4, sanpham.getGia());
            preparedStatement.setString(5, sanpham.getImg());
            preparedStatement.setString(6, sanpham.getLoaisp());

            return preparedStatement.execute();
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
    }


    @Override
    public boolean edit(int id, SanPham sanpham) {
        try (Connection connection = ConnectMySQL.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SANPHAM_SQL);
            preparedStatement.setInt(7, sanpham.getIdsp());
            preparedStatement.setString(1, sanpham.getTensp());
            preparedStatement.setString(2, sanpham.getDvt());
            preparedStatement.setString(3, sanpham.getMota());
            preparedStatement.setFloat(4, sanpham.getGia());
            preparedStatement.setString(5, sanpham.getImg());
            preparedStatement.setString(6, sanpham.getLoaisp());
            return preparedStatement.execute();
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = ConnectMySQL.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SANPHAM_SQL);
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
    }

    @Override
    public SanPham findById(int idsp) {
        SanPham sanpham = null;
        try (Connection connection = ConnectMySQL.getConnect();
        ) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SANPHAM_BY_ID);) {
                preparedStatement.setInt(1, idsp);
                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    String tensp = rs.getString("tensp");
                    String dvt = rs.getString("dvt");
                    String mota = rs.getString("mota");
                    Float gia = Float.valueOf(rs.getString("gia"));
                    String img = rs.getString("img");
                    String loaisp = rs.getString("loaisp");
                    sanpham = new SanPham(idsp, tensp, dvt, mota, gia, img, loaisp);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return sanpham;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace();
                System.out.println("SQLState:" + ((SQLException) e).getSQLState());
                System.out.println("Error Code:" + ((SQLException) e).getSQLState());
                System.out.println("Message:" + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause:" + t);
                    t = t.getCause();
                }
            }
        }
    }

}
