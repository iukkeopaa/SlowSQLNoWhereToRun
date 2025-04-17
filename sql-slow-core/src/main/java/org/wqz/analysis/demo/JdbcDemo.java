package org.wqz.analysis.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemo {
    // 数据库连接信息
    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            // 插入数据
            insertData(connection, 1, "John");
            // 查询数据
            selectData(connection);
            // 更新数据
            updateData(connection, 1, "Jane");
            // 再次查询数据
            selectData(connection);
            // 删除数据
            deleteData(connection, 1);
            // 最后一次查询数据
            selectData(connection);
        } catch (SQLException e) {
            System.err.println("数据库操作出错: " + e.getMessage());
        }
    }

    // 获取数据库连接
    private static Connection getConnection() throws SQLException {
        try {
            // 加载 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("找不到 JDBC 驱动: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // 插入数据
    private static void insertData(Connection connection, int id, String name) throws SQLException {
        String sql = "INSERT INTO your_table_name (id, name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("数据插入成功");
            }
        }
    }

    // 查询数据
    private static void selectData(Connection connection) throws SQLException {
        String sql = "SELECT * FROM your_table_name";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            System.out.println("查询结果:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        }
    }

    // 更新数据
    private static void updateData(Connection connection, int id, String newName) throws SQLException {
        String sql = "UPDATE your_table_name SET name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("数据更新成功");
            }
        }
    }

    // 删除数据
    private static void deleteData(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM your_table_name WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("数据删除成功");
            }
        }
    }
}    