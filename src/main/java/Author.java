import org.sql2o.*;
import java.util.List;

public class Author {

  private int id;
  private String name;

  public Author (String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherAuthorInstance) {
    if (!(otherAuthorInstance instanceof Author)) {
      return false;
    } else {
      Author newAuthorInstance = (Author) otherAuthorInstance;
      return this.getName().equals(newAuthorInstance.getName()) &&
             this.getId() == newAuthorInstance.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
          .addParameter("name", name)
          .executeUpdate()
          .getKey();
    }
  }

  public void update(String name) {
    this.name = name;
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE authors SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }

  }

  public void delete() {
    try(Connection con = DB.sql2o.open()){
     String sql = "DELETE FROM authors WHERE id = :id";
     con.createQuery(sql)
     .addParameter("id", id)
     .executeUpdate();

     String deleteBooksAuthors = "DELETE FROM books_authors WHERE author_id = :id";
     con.createQuery(deleteBooksAuthors)
     .addParameter("id", id)
     .executeUpdate();
    }
  }


  public static List<Author> all() {
    String sql = "SELECT * FROM authors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  public static Author find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM authors WHERE id=:id";
      Author book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Author.class);
      return book;
    }
  }

  public void addBook(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_authors (book_id, author_id) VALUES (:book_id, :author_id)";
      con.createQuery(sql)
        .addParameter("book_id", book.getId())
        .addParameter("author_id", id)
        .executeUpdate();
    }
  }

  public List<Book> getBooks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT books.* FROM authors JOIN books_authors ON (authors.id = books_authors.author_id) JOIN books ON (books_authors.book_id = books.id) WHERE authors.id = :id";
      List<Book> books = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Book.class);
      return books;
    }
  }

  public static List<Author> search(String query) {
    try(Connection con = DB.sql2o.open()) {
      String searchQuery = "%"+query+"%";
      String sql = "SELECT * FROM authors WHERE name LIKE :searchQuery";
      List<Author> authors = con.createQuery(sql)
        .addParameter("searchQuery", searchQuery)
        .executeAndFetch(Author.class);
      return authors;
    }
  }

}
