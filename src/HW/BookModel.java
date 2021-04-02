package HW;



import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookModel {
    private List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public BookModel() {
        try {
            books = SqlGetter.bookModelReader();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Book {
        private int id;
        private String name;
        private String author;
        private boolean inLibrary;
        private String imgLink;

        public Book(int id, String name, String author, boolean inLibrary, String imgLink) {
            this.id = id;
            this.name = name;
            this.author = author;
            this.inLibrary = inLibrary;
            this.imgLink = imgLink;
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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public boolean isInLibrary() {
            return inLibrary;
        }

        public void setInLibrary(boolean inLibrary) {
            this.inLibrary = inLibrary;
        }

        public String getImgLink() {
            return imgLink;
        }

        public void setImgLink(String imgLink) {
            this.imgLink = imgLink;
        }
    }


}

