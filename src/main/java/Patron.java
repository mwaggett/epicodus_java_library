import org.sql2o.*;
import java.util.List;

public class Patron {

  private int id;
  private String name;

  public Patron (String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherPatronInstance) {
    if (!(otherPatronInstance instanceof Patron)) {
      return false;
    } else {
      Patron newPatronInstance = (Patron) otherPatronInstance;
      return this.getName().equals(newPatronInstance.getName()) &&
             this.getId() == newPatronInstance.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
          .addParameter("name", name)
          .executeUpdate()
          .getKey();
    }
  }

  public void update(String name) {
    this.name = name;
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE patrons SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }

  }

  public void delete() {
    try(Connection con = DB.sql2o.open()){
     String sql = "DELETE FROM patrons WHERE id = :id";
     con.createQuery(sql)
     .addParameter("id", id)
     .executeUpdate();

     String deleteCheckouts = "UPDATE books SET patron_id = 0 WHERE patron_id = :id";
     con.createQuery(deleteCheckouts)
     .addParameter("id", id)
     .executeUpdate();
    }
  }

  public static List<Patron> all() {
    String sql = "SELECT * FROM patrons";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patron.class);
    }
  }

  public static Patron find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons WHERE id=:id";
      Patron patron = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patron.class);
      return patron;
    }
  }

  public List<Book> getBooks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE patron_id=:patron_id";
      List<Book> books = con.createQuery(sql)
        .addParameter("patron_id", id)
        .executeAndFetch(Book.class);
      return books;
    }
  }

  public List<Book> getHistory() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT books.* FROM patrons JOIN checkouts ON (patrons.id = checkouts.patron_id) JOIN books ON (checkouts.book_id = books.id) WHERE patrons.id = :id";
      List<Book> books = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Book.class);
    return books;
    }
  }

}
