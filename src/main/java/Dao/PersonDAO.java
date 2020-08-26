package Dao;

import model.Event;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO extends dao {

    Connection connection;

    public PersonDAO(Connection conn)
    {
        this.connection = conn;
    }


    /**
     * Searches database for person associated with personID
     *
     * @param personID takes personID for which you want data
     *
     * @return Person associated with person_ID in person model
     *
     * @throws SQLException If the SQL error encountered
     */
    public model.Person getPerson(String personID) throws DataAccessException{
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE person_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("person_id"), rs.getString("username"),
                        rs.getString("first_name"), rs.getString("last_name"), rs.getString("gender"),
                        rs.getString("father_id"), rs.getString("mother_id"), rs.getString("spouse_id"));
                return person;
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

    public ArrayList<Person> getAllPersons(String username) throws DataAccessException{
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE username = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            ArrayList<Person> personList = new ArrayList<Person>();
            while (rs.next()) {
                person = new Person(rs.getString("person_id"), rs.getString("username"),
                        rs.getString("first_name"), rs.getString("last_name"), rs.getString("gender"),
                        rs.getString("father_id"), rs.getString("mother_id"), rs.getString("spouse_id"));
                personList.add(person);
            }
            return personList;
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
    }

    /**
     * Inserts a person into the database
     *
     * @param person Person model to add
     *
     * @throws SQLException If the SQL error encountered
     */
    public void insert(Person person) throws DataAccessException {

        String sql = "insert into persons (person_id, username, first_name, last_name, gender, father_id, mother_id, spouse_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
            //System.out.println("Inserted user " + user);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }

        /*
        //Father ID
        if (person.getFatherID() != null) {
            sql = "insert into persons (father_id) values (?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, person.getFatherID());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("Error encountered while inserting into the database");
            }
        }

        //Mother ID
        if (person.getMotherID() != null) {
            sql = "insert into persons (mother_id) values (?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, person.getMotherID());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("Error encountered while inserting into the database");
            }
        }

        //Spouse ID
        if (person.getSpouseID() != null) {
            sql = "insert into persons (spouse_id) values (?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, person.getSpouseID());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("Error encountered while inserting into the database");
            }
        }
         */
    }


    /**
     * Updates a person into the database
     *
     * @param person Person model to update
     *
     * @throws SQLException If the SQL error encountered
     */
    public void updatePerson(Person person) throws DataAccessException {
        String sql = "update persons " +
                "set username = ?, first_name = ?, last_name = ?, gender = ? " +
                "where person_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(5, person.getPersonID());
            stmt.setString(1, person.getAssociatedUsername());
            stmt.setString(2, person.getFirstName());
            stmt.setString(3, person.getLastName());
            stmt.setString(4, person.getGender());

            stmt.executeUpdate();
            //System.out.println("Inserted user " + user);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }

        //Father ID
        if (person.getFatherID() != null){
            sql = "update persons " +
                    "set father_id = ? " +
                    "where person_id = ?";
            try(PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, person.getFatherID());
                stmt.setString(2, person.getPersonID());

                stmt.executeUpdate();
            }catch (SQLException e) {
                throw new DataAccessException("Error encountered while inserting into the database");
            }
        }

        //Mother ID
        if (person.getMotherID() != null){
            sql = "update persons " +
                    "set mother_id = ? " +
                    "where person_id = ?";
            try(PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, person.getMotherID());
                stmt.setString(2, person.getPersonID());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("Error encountered while inserting into the database");
            }
        }

        //Spouse ID
        if (person.getSpouseID() != null){
            sql = "update persons " +
                    "set spouse_id = ? " +
                    "where person_id = ?";

            try(PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, person.getSpouseID());
                stmt.setString(2, person.getPersonID());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("Error encountered while inserting into the database");
            }
        }
    }

    /**
     * Removes all persons in table
     *
     * @throws SQLException If the SQL error encountered
     */
    public void removeAllPeople() throws DataAccessException {
        String sql = "delete from persons";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {

            int count = stmt.executeUpdate();

            System.out.printf("Deleted %d people\n", count);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Removes a person from database
     *
     * @param person Person model to remove
     *
     * @throws SQLException If the SQL error encountered
     */
    public void removePerson(Person person) throws DataAccessException {
        String sql = "delete from persons " +
                "where person_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql);){
            stmt.setString(1, person.getPersonID());

            int count = stmt.executeUpdate();

            System.out.printf("Deleted %d people\n", count);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }


    public boolean clearPersons() throws DataAccessException{
        //Returns if delete successful
        return clearTable(connection, "Persons");
    }
}
