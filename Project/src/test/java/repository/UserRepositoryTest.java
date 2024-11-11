package repository;

import com.asaki0019.project.model.User;
import com.asaki0019.project.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserRepositoryTest.class.getName());

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        clearUsersTable();
    }

    @AfterEach
    void tearDown() {
        clearUsersTable();
    }

    private void clearUsersTable() {
        String sql = "DELETE FROM users";
        try (Connection conn = userRepository.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while clearing users table", e);
            fail("Error clearing users table");
        }
    }

    @Test
    void testSaveUser() {
        User user = new User("testUser", "testPassword", "Test Name", "student");
        userRepository.save(user);
        User foundUser = userRepository.findByUsername("testUser");
        assertNotNull(foundUser, "User should be saved and found in the database");
        assertEquals("testUser", foundUser.getAccount(), "Username should match");
    }

    @Test
    void testFindUserByUsername() {
        User user = new User("testUser", "testPassword", "Test Name", "student");
        userRepository.save(user);
        User foundUser = userRepository.findByUsername("testUser");
        assertNotNull(foundUser, "User should be found in the database");
        assertEquals("testUser", foundUser.getAccount(), "Username should match");
    }

    @Test
    void testUpdateUser() {
        User user = new User("testUserToUpdate", "oldPassword", "Old Name", "student");
        userRepository.save(user);
        User foundUser = userRepository.findByUsername("testUserToUpdate");
        assertNotNull(foundUser, "User should be saved and found in the database");

        foundUser.setPassword("newPassword");
        foundUser.setName("New Name");
        userRepository.update(foundUser);

        User updatedUser = userRepository.findByUsername("testUserToUpdate");
        assertNotNull(updatedUser, "Updated user should be found in the database");
        assertEquals("newPassword", updatedUser.getPassword(), "Password should be updated");
        assertEquals("New Name", updatedUser.getName(), "Name should be updated");
    }

    @Test
    void testDeleteUser() {
        User user = new User("testUserToDelete", "testPassword", "Test Name", "student");
        userRepository.save(user);
        User foundUser = userRepository.findByUsername("testUserToDelete");
        assertNotNull(foundUser, "User should be saved and found in the database");

        userRepository.delete("testUserToDelete");
        foundUser = userRepository.findByUsername("testUserToDelete");
        assertNull(foundUser, "User should be deleted from the database");
    }
}