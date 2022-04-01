package Models.SQL;


/**
 * This class is meant to universally handle the creation of SQL queries and their execution.
 *
 * @author Sebastian Schwagerl
 * @Version Nov 25, 2018
 */

import java.sql.*;
import java.util.Map;
import view.AlertBox;


public class SQLQueryActions {

    /**
     * Forms a Delete Statement out of a provided table, and a set of limitors for the WHERE clause.
     * @param _table
     * @param _limitors
     * @return boolean
     */
    public boolean deleteObject(String _table, Map<String, String> _limitors) {

        String limitors = "";
        boolean executionSuccess;

        // Sort through the Column/Value pairs and form the WHERE statement.
        for (Map.Entry<String, String> entry : _limitors.entrySet()) {
            if (limitors.length() == 0) {
                limitors = limitors + entry.getKey() + " = '" + entry.getValue() + "'";
            } else {
                limitors = limitors + " AND " + entry.getKey() + " = '" + entry.getValue() + "'";
            }
        }

        // Pair everything together and send the SQLQuery off for execution.
        String sqlQuery = "DELETE FROM " + _table + " WHERE " + limitors;
        executionSuccess = executionQuery(sqlQuery);
        return executionSuccess;
    }


    /**
     * Generates and executes the insert command upon receiving the table to be
     * inserted into, and the column + entry pairs.
     * @param _table
     * @param _valuePairs
     * @return boolean
     */
    public boolean insertObject(String _table, Map<String, String> _valuePairs) {

        String columns = "";
        String entries = "";
        boolean executionSuccess;

        // For each entry in _valuePairs, pull out the Column name and associated value to be inserted.
        for (Map.Entry<String, String> entry : _valuePairs.entrySet()) {
            if (columns.length() == 0) {
                columns = columns + entry.getKey();
                entries = entries + "'" + entry.getValue() + "'";
            } else {
                columns = columns + ", " + entry.getKey();
                entries = entries + ", '" + entry.getValue() + "'";
            }
        }

        // Pair everything together and send the SQLQuery off for execution.
        String sqlQuery = "INSERT INTO " + _table + " (" + columns + ") VALUES (" + entries + ");";
        executionSuccess = executionQuery(sqlQuery);
        return executionSuccess;
    }


    /**
     * Forms a Select Statement out of a provided list of tables, columns, and
     * a set of limitors for the WHERE clause.
     * @param _tables
     * @param _columns
     * @param _limitors
     * @param _orderBy
     * @return ResultSet
     */
    public ResultSet selectObjectBasic(String[] _tables, String[] _columns,
            Map<String, String> _limitors, String[] _orderBy) {

        String columns = "";
        String tables = "";
        String whereLimitors = "";
        String orderBy = "";
        ResultSet results;

        // Sort through the array of Columns provided and form the SELECT part of the SELECT statement.
        for (int i = 0; i < _columns.length; i++) {
            if (columns.length() == 0) {
                columns = columns + _columns[i];
            } else {
                columns = columns + ", " + _columns[i];
            }
        }

        // Sort through the array of Tables provided and form the FROM part of the SELECT statement.
        for (int i = 0; i < _tables.length; i++) {
            if (tables.length() == 0) {
                tables = tables + _tables[i];
            } else {
                tables = tables + ", " + _tables[i];
            }
        }

        // Sort through the Column/Value pairs and form the WHERE statement.
        for (Map.Entry<String, String> entry : _limitors.entrySet()) {
            if (whereLimitors.length() == 0) {
                whereLimitors = whereLimitors + " WHERE " + entry.getKey() + " = '" + entry.getValue() + "'";
            } else {
                whereLimitors = whereLimitors + " AND " + entry.getKey() + " = '" + entry.getValue() + "'";
            }
        }

        // Sort through the array of Columns provided and form the ORDERBY part of the SELECT statement.
        for (int i = 0; i < _orderBy.length; i++) {
            if (orderBy.length() == 0) {
                orderBy = orderBy + " ORDER BY " + _orderBy[i].toString();
            } else {
                orderBy = orderBy + ", " + _orderBy[i].toString();
            }
        }

        // Pair everything together and send the SQLQuery off for execution.
        String sqlQuery = "SELECT " + columns + " FROM " + tables + whereLimitors + orderBy;
        results = selectQuery(sqlQuery);
        return results;
    }


    /**
     * Forms a Update Statement out of a provided table, list of Column Value
     * pairs, and a set of limitors for the WHERE clause.
     * @param _table
     * @param _valuePairs
     * @param _limitors
     * @return boolean
     */
    public boolean updateObject(String _table, Map<String, String> _valuePairs,
            Map<String, String> _limitors) {

        String changes = "";
        String whereLimitors = "";
        boolean executionSuccess;

        // Sort through the Column/Value pairs and form the UPDATE statement.
        for (Map.Entry<String, String> entry : _valuePairs.entrySet()) {
            if (changes.length() == 0) {
                changes = changes + entry.getKey() + " = '" + entry.getValue() + "'";
            } else {
                changes = changes + ", " + entry.getKey() + " = '" + entry.getValue() + "'";
            }
        }

        // Sort through the Column/Value pairs and form the WHERE statement.
        for (Map.Entry<String, String> entry : _limitors.entrySet()) {
            if (whereLimitors.length() == 0) {
                whereLimitors = whereLimitors + " WHERE " + entry.getKey() + " = '" + entry.getValue() + "'";
            } else {
                whereLimitors = whereLimitors + " AND " + entry.getKey() + " = '" + entry.getValue() + "'";
            }
        }

        // Pair everything together and send the SQLQuery off for execution.
        String sqlQuery = "UPDATE " + _table + " SET " + changes + whereLimitors;
        executionSuccess = executionQuery(sqlQuery);
        return executionSuccess;
    }


    /**
     * It takes in the formed query from Insert, Update, and Delete, and uses "executeUpdate".
     * @param _sqlQuery
     * @return boolean
     */
    private boolean executionQuery(String _sqlQuery) {

        try {
            // Establish a connection to the database.
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String path = "./Database/SST.accdb";
            String url = "jdbc:ucanaccess://" + path;
            Connection connection = DriverManager.getConnection(url);

            // Attempt to run the SQLQuery on the database.
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(_sqlQuery);
                return true;
            } catch (Exception e) {
                AlertBox.display(e);
                return false;
            }
        } catch (Exception x) {
            AlertBox.display(x);
            return false;
        }
    }


    /**
     * It takes in the formed query from Select Statements and returns the ResultSet
     * @param _sqlQuery
     * @return ResultSet
     */
    private ResultSet selectQuery(String _sqlQuery) {

        ResultSet results = null;
        try {
            // Establish a connection to the database.
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String path = "./Database/SST.accdb";
            String url = "jdbc:ucanaccess://" + path;
            Connection connection = DriverManager.getConnection(url);

            // Attempt to run the SQLQuery on the database.
            try (Statement statement = connection.createStatement()) {
                results = statement.executeQuery(_sqlQuery);
                return results;
            } catch (Exception e) {
                AlertBox.display(e);
                return results;
            }

        } catch (Exception x) {
            AlertBox.display(x);
            return results;
        }
    }
}