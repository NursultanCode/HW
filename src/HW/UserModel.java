package HW;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private List<User> customers = new ArrayList<>();

    public UserModel() {
        customers.add(new User(1,"Marco"));
        customers.add(new User(2,"Winston"));
        customers.add(new User(3,"Amos"));
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

        public User(int id, String name) {
            this.id = id;
            this.name = name;
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
    }

}

