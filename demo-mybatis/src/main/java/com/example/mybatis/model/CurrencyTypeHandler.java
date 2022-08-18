package com.example.mybatis.model;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyTypeHandler extends BaseTypeHandler<Currency> {

    @Override public void setNonNullParameter(PreparedStatement ps, int i, Currency parameter, JdbcType jdbcType)
        throws SQLException {
        ps.setString(i,parameter.getCode());
    }

    @Override public Currency getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String currency = rs.getString(columnName);
        return Currency.getCurrencyByCode(currency);
    }

    @Override public Currency getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override public Currency getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
