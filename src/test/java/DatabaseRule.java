import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteBooksQuery = "DELETE FROM books *;";
      String deleteAuthorsQuery = "DELETE FROM authors *;";
      String deleteBooksAuthorsQuery = "DELETE FROM books_authors *;";
      String deletePatronsQuery = "DELETE FROM patrons *;";
      con.createQuery(deleteBooksQuery).executeUpdate();
      con.createQuery(deleteAuthorsQuery).executeUpdate();
      con.createQuery(deleteBooksAuthorsQuery).executeUpdate();
      con.createQuery(deletePatronsQuery).executeUpdate();
    }
  }
}
