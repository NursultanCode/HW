package HW;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private List<User> customers = new ArrayList<>();
    public UserModel() {
        try {
            customers = SqlGetter.userModelReader();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static User checkForContain(String email, List<User> users) throws SQLException, ClassNotFoundException {
        for (User user: users
             ) {
            if (user.email.equals(email)) return user;
        }
        return null;
    }

    public List<User> getCustomers() {
        return customers;
    }

    public void setCustomers(List<User> customers) {
        this.customers = customers;
    }

    public static class User {
        private int id;
        private String name;
        private String email;
        private String password;

        public User(int id, String name, String username, String password) {
            this.id = id;
            this.name = name;
            this.email = username;
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }
}

