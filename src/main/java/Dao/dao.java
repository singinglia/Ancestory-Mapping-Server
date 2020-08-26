package Dao;

import java.sql.*;

public class dao {

    /**
     * Removes Data from a table
     *
     * @param connection to database
     * @param tableName specifies the table to clear
     *
     * @throws SQLException throws SQL error
     */
    private void removeAllFromTable(Connection connection, String tableName) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "delete from " + tableName;
            stmt = connection.prepareStatement(sql);

            int count = stmt.executeUpdate();

            System.out.printf("Deleted %d %s\n", count, tableName);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * Removes an object from a table
     *
     * @param connection Connection to database
     * @param tableName table where the object is found
     * @param objIdentifier identifier of the object to clear
     *
     * @throws SQLException Throws SQL error
     */
    private void removeObject(Connection connection, String tableName, String objIdentifier) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "delete from " + tableName +" " +
                    "where person_id = ?";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, objIdentifier);

            stmt.executeUpdate();

        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    protected boolean clearTable(Connection connection, String tableName) throws DataAccessException {
        try (Statement stmt = connection.createStatement()){
            String sql = "DELETE FROM " + tableName;
            int deleted = stmt.executeUpdate(sql);
            if (deleted == 0) {
                return false;
            }
            else {
                return true;
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }

    /**
     * Clears all tables from database
     *
     * @param connection to Database
     */
    public void clearDatabase(Connection connection){}


}

