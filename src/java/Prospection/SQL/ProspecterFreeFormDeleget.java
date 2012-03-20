/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prospection.SQL;

import Prospection.vaadin.MyApplication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.vaadin.addon.sqlcontainer.RowItem;
import com.vaadin.addon.sqlcontainer.TemporaryRowId;
import com.vaadin.addon.sqlcontainer.Util;
import com.vaadin.addon.sqlcontainer.query.FreeformQueryDelegate;
import com.vaadin.addon.sqlcontainer.query.OrderBy;
import com.vaadin.addon.sqlcontainer.query.generator.StatementHelper;
import com.vaadin.addon.sqlcontainer.query.generator.filter.QueryBuilder;
import com.vaadin.data.Container.Filter;

/**
 *
 * @author weizhe.jiao
 */

@SuppressWarnings("serial")
public class ProspecterFreeFormDeleget implements FreeformQueryDelegate{
    
    private List<Filter> filters;
    private List<OrderBy> orderBys;

    private String viewName;
    private int id_Annonce;
    private int aux;
    
    @Deprecated
    @Override
    public String getQueryString(int offset, int limit)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Use getQueryStatement method.");
    }
    
    public ProspecterFreeFormDeleget(String view, int annonce, int idaux)
    {
        super();
        MyApplication.debug(3, "in ProspecterFreeFormDeleget");
        this.viewName=view;
        this.id_Annonce=annonce;
        this.aux=idaux;
    }
    
    

    public StatementHelper getQueryStatement(int offset, int limit)
            throws UnsupportedOperationException {
         StatementHelper sh = new StatementHelper();
        StringBuffer query = new StringBuffer("SELECT * FROM "+viewName+"  where id = "
                    +" ( select top 1 id from "
                    +viewName
                    +" where  aux = ?"
                    +" order by ann_datepar desc)");
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
    @Override
    public String getCountQuery() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Use getCountStatement method.");
    }

    public StatementHelper getCountStatement()
            throws UnsupportedOperationException {
        StatementHelper sh = new StatementHelper();
        StringBuffer query = new StringBuffer("SELECT COUNT(*) FROM "+viewName+"where id = "
                    +" ( select top 1 id from "
                    +viewName
                    +" where  aux = ?"
                    +" order by ann_datepar desc)");
        if (filters != null) {
            query.append(QueryBuilder.getWhereStringForFilters(
                    filters, sh));
        }
        sh.setQueryString(query.toString());
        return sh;
    }

    @Override
    public void setFilters(List<Filter> filters)
            throws UnsupportedOperationException {
        this.filters = filters;
    }

    @Override
    public void setOrderBy(List<OrderBy> orderBys)
            throws UnsupportedOperationException {
        this.orderBys = orderBys;
    }

    @Override
    public int storeRow(Connection conn, RowItem row) throws SQLException {
        MyApplication.debug(3, "in ProspecterFreemFormQueryDelegate!!!");
        PreparedStatement statement = null;
        if (row.getId() instanceof TemporaryRowId) {
            /*statement = conn
                    .prepareStatement("INSERT INTO vwtest VALUES(0000000, ?, ?, ?)");
            setRowValues(statement, row);*/
        } else {
            statement = conn
                    .prepareStatement("UPDATE "+viewName
                    +" SET  coAdresse = ?, isInterphone = ?, coInterphone = ?"
                    + ", isDigicode = ?, coDigicode = ?, isGarkdien = ?"
                    + ", isAscenseur = ? "
                    +" where id = ( select top 1 id from "
                    +viewName
                    +" where  aux = ?"
                    +" order by ann_datepar desc)");
            setRowValues(statement, row);
            statement
                    .setInt(8, (Integer) row.getItemProperty("aux").getValue());
            
            MyApplication.debug(3, "statement: "+statement.toString());
        }

        int retval = statement.executeUpdate();
        statement.close();
        return retval;
    }

    private void setRowValues(PreparedStatement statement, RowItem row)
            throws SQLException {
        MyApplication.debug(3, "store Row Value: "+(String)row.getItemProperty("coAdresse")
                .getValue());
        statement.setString(1, (String) row.getItemProperty("coAdresse")
                .getValue());
        statement.setBoolean(2, (Boolean) row.getItemProperty("isInterphone")
                .getValue());
        statement.setString(3, (String) row.getItemProperty("coInterphone")
                .getValue());
        statement.setBoolean(4, (Boolean) row.getItemProperty("isDigicode")
                .getValue());
        statement.setString(5, (String) row.getItemProperty("coDigicode")
                .getValue());
        statement.setBoolean(6, (Boolean) row.getItemProperty("isGardien")
                .getValue());
       statement.setBoolean(7, (Boolean) row.getItemProperty("isAscenseur")
                .getValue());
       }

    @Override
    public boolean removeRow(Connection conn, RowItem row)
            throws UnsupportedOperationException, SQLException {
        PreparedStatement statement = conn
                .prepareStatement("DELETE FROM "+viewName+"  where id = "
                    +" ( select top 1 id from "
                    +viewName
                    +" where  aux = ?"
                    +" order by ann_datepar desc)");
        statement.setInt(1, (Integer) row.getItemProperty("id").getValue());
        int rowsChanged = statement.executeUpdate();
        statement.close();
        return rowsChanged == 1;
    }

    @Deprecated
    @Override
    public String getContainsRowQueryString(Object... keys)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Please use getContainsRowQueryStatement method.");
    }

    public StatementHelper getContainsRowQueryStatement(Object... keys)
            throws UnsupportedOperationException {
        StatementHelper sh = new StatementHelper();
        StringBuffer query = new StringBuffer(
                "SELECT * FROM "+viewName+"  where id = "
                    +" ( select top 1 id from "
                    +viewName
                    +" where  aux = ?"
                    +" order by ann_datepar desc)"
                    );
        sh.addParameterValue(keys[0]);
        sh.setQueryString(query.toString());
        return sh;
    }
}

