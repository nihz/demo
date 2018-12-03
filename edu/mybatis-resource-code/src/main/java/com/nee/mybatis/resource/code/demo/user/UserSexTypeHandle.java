package com.nee.mybatis.resource.code.demo.user;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(UserSexEnum.class)
public class UserSexTypeHandle extends BaseTypeHandler<Enum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Enum parameter, JdbcType jdbcType) throws SQLException {
        if (parameter.equals(UserSexEnum.M)){
            ps.setLong(i, 1);
        } else {
            ps.setLong(i, 2);
        }
    }

    @Override
    public Enum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        long sex = rs.getLong(columnName);
        if (sex == 1) {
            return UserSexEnum.M;
        } else {
            return UserSexEnum.F;
        }
    }

    @Override
    public Enum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        long sex = rs.getLong(columnIndex);
        if (sex == 1) {
            return UserSexEnum.M;
        } else {
            return UserSexEnum.F;
        }
    }

    @Override
    public Enum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        long sex = cs.getLong(columnIndex);
        if (sex == 1) {
            return UserSexEnum.M;
        } else {
            return UserSexEnum.F;
        }
    }
}
