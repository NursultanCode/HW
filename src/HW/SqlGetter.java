package HW;

import HW.UserModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlGetter {
    public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_STRING =
            "jdbc:mysql://localhost/library?user=root&password=samsung94&useLegacyDatetimeCode=false&serverTimezone=UTC";

        public static List<UserModel.User> userModelReader() throws ClassNotFoundException, SQLException {
        List<UserModel.User> customers = new ArrayList<>();
            Class.forName(DRIVER_NAME);
            try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            ){
                Statement cmd = conn.createStatement();
                String sql = "SELECT * FROM library.users";

                ResultSet result = cmd.executeQuery(sql);
                while (result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String username = result.getString("username");
                    String password = result.getString("password");

                    UserModel.User user = new UserModel.User(id,name,username,password);
                    customers.add(user);
                }
                return customers;
            }


        }
    public static List<BookModel.Book> bookModelReader() throws ClassNotFoundException, SQLException {
        List<BookModel.Book> books = new ArrayList<>();
        Class.forName(DRIVER_NAME);
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
        ){
            Statement cmd = conn.createStatement();
            String sql = "SELECT * FROM library.book";

            ResultSet result = cmd.executeQuery(sql);
            while (result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                String author = result.getString("author");
                boolean inLibrary = result.getBoolean("inLibrary");
                String linkImg = result.getString("linkImg");
                System.out.println(inLibrary);

                BookModel.Book book = new BookModel.Book(id,name, author, inLibrary, linkImg);
                books.add(book);
            }
            return books;
        }

    }
}
