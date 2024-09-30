package dao;

import datasource.MariaDbConnection;
import entity.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDao {

    public List<String> getAllCodes() {
        String sql = "SELECT code FROM currency";
        List<String> codes = new ArrayList<>();

        try (Connection conn = MariaDbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                codes.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return codes;
    }

    public double getRateByCode(String code) {
        String sql = "SELECT exchange_rate FROM currency WHERE `code`=?";
        double rate = 0;

        try (Connection conn = MariaDbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rate = rs.getDouble(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rate;
    }
}
