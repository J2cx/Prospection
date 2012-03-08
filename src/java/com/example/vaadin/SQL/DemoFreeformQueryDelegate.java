package com.example.vaadin.SQL;

import com.example.vaadin.MyApplication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.vaadin.addon.sqlcontainer.RowItem;
import com.vaadin.addon.sqlcontainer.TemporaryRowId;
import com.vaadin.addon.sqlcontainer.Util;
import com.vaadin.addon.sqlcontainer.query.FreeformQueryDelegate;
import com.vaadin.addon.sqlcontainer.query.FreeformStatementDelegate;
import com.vaadin.addon.sqlcontainer.query.OrderBy;
import com.vaadin.addon.sqlcontainer.query.generator.StatementHelper;
import com.vaadin.addon.sqlcontainer.query.generator.filter.QueryBuilder;
import com.vaadin.data.Container.Filter;
import java.sql.*;


@SuppressWarnings("serial")
public class DemoFreeformQueryDelegate implements FreeformQueryDelegate {

    private List<Filter> filters;
    private List<OrderBy> orderBys;

    @Deprecated
    public String getQueryString(int offset, int limit)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Use getQueryStatement method.");
    }

    public StatementHelper getQueryStatement(int offset, int limit)
            throws UnsupportedOperationException {
        StatementHelper sh = new StatementHelper();
        StringBuffer query = new StringBuffer("SELECT * FROM vwtest ");
        if (filters != null) {
            query.append(QueryBuilder.getWhereStringForFilters(
                    filters, sh));
        }
        query.append(getOrderByString());
        if (offset != 0 || limit != 0) {
            query.append(" LIMIT ").append(limit);
            query.append(" OFFSET ").append(offset);
        }
        sh.setQueryString(query.toString());
        return sh;
    }

    private String getOrderByString() {
        StringBuffer orderBuffer = new StringBuffer("");
        if (orderBys != null && !orderBys.isEmpty()) {
            orderBuffer.append(" ORDER BY ");
            OrderBy lastOrderBy = orderBys.get(orderBys.size() - 1);
            for (OrderBy orderBy : orderBys) {
                orderBuffer.append(Util.escapeSQL(orderBy.getColumn()));
                if (orderBy.isAscending()) {
                    orderBuffer.append(" ASC");
                } else {
                    orderBuffer.append(" DESC");
                }
                if (orderBy != lastOrderBy) {
                    orderBuffer.append(", ");
                }
            }
        }
        return orderBuffer.toString();
    }

    @Deprecated
    public String getCountQuery() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Use getCountStatement method.");
    }

    public StatementHelper getCountStatement()
            throws UnsupportedOperationException {
        StatementHelper sh = new StatementHelper();
        StringBuffer query = new StringBuffer("SELECT COUNT(*) FROM vwtest ");
        if (filters != null) {
            query.append(QueryBuilder.getWhereStringForFilters(
                    filters, sh));
        }
        sh.setQueryString(query.toString());
        return sh;
    }

    public void setFilters(List<Filter> filters)
            throws UnsupportedOperationException {
        this.filters = filters;
    }

    public void setOrderBy(List<OrderBy> orderBys)
            throws UnsupportedOperationException {
        this.orderBys = orderBys;
    }

    @Override
    public int storeRow(Connection conn, RowItem row) throws SQLException {
        MyApplication.debug(3, "in demoFreemFormQueryDelegate!!!");
        PreparedStatement statement = null;
        if (row.getId() instanceof TemporaryRowId) {
            statement = conn
                    .prepareStatement("INSERT INTO vwtest VALUES(0000000, ?, ?, ?)");
            setRowValues(statement, row);
        } else {
            statement = conn
                    .prepareStatement("UPDATE vwtest SET aux = ?, ann_datepar = ?, ann_prix = ? WHERE id = ?");
            setRowValues(statement, row);
            statement
                    .setInt(4, (Integer) row.getItemProperty("id").getValue());
        }

        int retval = statement.executeUpdate();
        statement.close();
        return retval;
    }

    private void setRowValues(PreparedStatement statement, RowItem row)
            throws SQLException {
        statement.setInt(1, (Integer) row.getItemProperty("aux")
                .getValue());
        statement.setTimestamp(2, (Timestamp)row.getItemProperty("ann_datepar")
                .getValue());
        statement.setInt(3, (Integer) row.getItemProperty("ann_prix")
                .getValue());
    }

    public boolean removeRow(Connection conn, RowItem row)
            throws UnsupportedOperationException, SQLException {
        PreparedStatement statement = conn
                .prepareStatement("DELETE FROM vwtest WHERE id = ?");
        statement.setInt(1, (Integer) row.getItemProperty("id").getValue());
        int rowsChanged = statement.executeUpdate();
        statement.close();
        return rowsChanged == 1;
    }

    @Deprecated
    public String getContainsRowQueryString(Object... keys)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Please use getContainsRowQueryStatement method.");
    }

    public StatementHelper getContainsRowQueryStatement(Object... keys)
            throws UnsupportedOperationException {
        StatementHelper sh = new StatementHelper();
        StringBuffer query = new StringBuffer(
                "SELECT * FROM vwtest WHERE id = ?");
        sh.addParameterValue(keys[0]);
        sh.setQueryString(query.toString());
        return sh;
    }
}
