package com.nee.spring.boot.jdbc.repository;

import com.nee.spring.boot.jdbc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class UserRepository {

    private final DataSource dataSource;

    @Autowired
    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Boolean save(User user) {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            PreparedStatement psmt = connection.prepareStatement("INSERT INTO users(name) value(?);");
            psmt.setString(1, user.getName());
            psmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public Collection<User> findAll() {

        return null;
    }
}
