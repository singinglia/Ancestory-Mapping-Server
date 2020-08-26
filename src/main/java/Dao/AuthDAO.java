package Dao;

import model.AuthToken;
import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAO extends dao {

    Connection connection;

    public AuthDAO(Connection conn)
    {
        this.connection = conn;
    }

    /**
     * Inserts an auth_token into the database
     *
     * @param authToken authToken to add
     *
     * @throws SQLException If the SQL error encountered
     */
    public void insert(AuthToken authToken) throws DataAccessException {
        String sql = "insert into AuthTokens (auth_token, username) values (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, authToken.getAuth_token());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
            //System.out.println("Inserted user " + user);

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }


    }

    /**
     * Updates an auth_token in the database
     *
     * @param authToken AuthToken
     *
     * @throws SQLException If the SQL error encountered
     */
    public void updateAuth(model.AuthToken authToken) throws DataAccessException {

        String sql = "update AuthTokens " +
                "set auth_token = ? " +
                "where person_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, authToken.getAuth_token());
            stmt.setString(2, authToken.getPersonID());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public AuthToken find(String authTokenID) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE auth_token = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, authTokenID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("auth_token"), rs.getString("username"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}
