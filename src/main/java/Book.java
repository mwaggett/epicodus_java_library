import org.sql2o.*;
import java.util.List;

public class Book {

  private int id;
  private String title;
  //private int patron_id;

  public Book (String title) {
    this.title = title;
    //this.patron_id = null;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  // public int getPatronId() {
  //   return patron_id;
  // }

  @Override
  public boolean equals(Object otherBookInstance) {
    if (!(otherBookInstance instanceof Book)) {
      return false;
    } else {
      Book newBookInstance = (Book) otherBookInstance;
      return this.getTitle().equals(newBookInstance.getTitle()) &&
             //this.getPatronId().equals(newBookInstance.getPatronId()) &&
             this.getId() == newBookInstance.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (title) VALUES (:title);";
      this.id = (int) con.createQuery(sql, true)
          .addParameter("title", title)
          .executeUpdate()
          .getKey();
    }
  }

  public void update(String title) {
    this.title = title;
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE books SET title = :title WHERE id = :id";
      con.createQuery(sql)
      .addParameter("title", title)
      .addParameter("id", id)
      .executeUpdate();
    }

  }

  public void delete() {
    try(Connection con = DB.sql2o.open()){
     String sql = "DELETE FROM books WHERE id = :id";
     con.createQuery(sql)
     .addParameter("id", id)
     .executeUpdate();

     String deleteBooksAuthors = "DELETE FROM books_authors WHERE book_id = :id";
     con.createQuery(deleteBooksAuthors)
     .addParameter("id", id)
     .executeUpdate();

     String deleteCheckouts = "DELETE FROM checkouts WHERE book_id = :id";
     con.createQuery(deleteCheckouts)
     .addParameter("id", id)
     .executeUpdate();
    }
  }


  public static List<Book> all() {
    String sql = "SELECT * FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE id=:id";
      Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void addAuthor(Author author) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_authors (book_id, author_id) VALUES (:book_id, :author_id)";
      con.createQuery(sql)
        .addParameter("book_id", id)
        .addParameter("author_id", author.getId())
        .executeUpdate();
    }
  }

  public List<Author> getAuthors() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT authors.* FROM books JOIN books_authors ON (books.id = books_authors.book_id) JOIN authors ON (books_authors.author_id = authors.id) WHERE books.id = :id";
      List<Author> authors = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Author.class);
      return authors;
    }
  }

  public static List<Book> search(String query) {
    try(Connection con = DB.sql2o.open()) {
      String searchQuery = "%"+query+"%";
      String sql = "SELECT * FROM books WHERE title LIKE :searchQuery";
      List<Book> books = con.createQuery(sql)
        .addParameter("searchQuery", searchQuery)
        .executeAndFetch(Book.class);
      return books;
    }
  }

  // public void checkout(Patron patron) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "UPDATE books SET patron_id = :patron_id";
  //     con.createQuery(sql)
  //       .addParameter("patron_id", patron.getId());
  //       .executeUpdate();
  //   }
  // }



}
