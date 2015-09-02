import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Patron.all().size());
  }

  @Test
  public void getName_returnsName() {
    Patron instance = new Patron("Seymour Buttz");
    assertEquals("Seymour Buttz", instance.getName());
  }

  @Test
  public void getId_returnsIdAfterSave() {
    Patron instance = new Patron("Seymour Buttz");
    instance.save();
    assertEquals(Patron.all().get(0).getId(), instance.getId());
  }

  @Test
  public void equals_returnsTrueWhenParamsMatch() {
    Patron firstInstance = new Patron("Seymour Buttz");
    Patron secondInstance = new Patron("Seymour Buttz");
    assertEquals(true, firstInstance.equals(secondInstance));
  }

  @Test
  public void equals_returnsFalseWhenParamsMatch() {
    Patron firstInstance = new Patron("Seymour Buttz");
    Patron secondInstance = new Patron("Butts McKenzie");
    assertEquals(false, firstInstance.equals(secondInstance));
  }

  @Test
  public void equals_returnsFalseAfterSave() {
    Patron firstInstance = new Patron("Seymour Buttz");
    firstInstance.save();
    Patron secondInstance = new Patron("Seymour Buttz");
    secondInstance.save();
    assertEquals(false, firstInstance.equals(secondInstance));
  }

  @Test
  public void save_addsToDatabase() {
    Patron instance = new Patron("Seymour Buttz");
    instance.save();
    assertEquals(Patron.all().get(0), instance);
  }

  @Test
  public void find_findsPatronsInDatabase() {
    Patron instance = new Patron("Seymour Buttz");
    instance.save();
    assertEquals(instance, Patron.find(instance.getId()));
  }

  @Test
  public void update_changesName() {
    Patron instance = new Patron("Seymour Buttz");
    instance.save();
    instance.update("Butts McKenzie");
    assertEquals("Butts McKenzie", Patron.all().get(0).getName());
    assertEquals("Butts McKenzie", instance.getName());
  }

  @Test
  public void delete_deletesFromDatabase() {
    Patron instance = new Patron("Seymour Buttz");
    instance.save();
    instance.delete();
    assertEquals(0, Patron.all().size());
  }

  @Test
  public void getBooks_returnsCheckedOutBooks() {
    Patron instance = new Patron("Seymour Buttz");
    instance.save();
    Book newBook = new Book("Moby Dick");
    newBook.save();
    Book savedBook = Book.find(newBook.getId());
    savedBook.checkout(instance);
    assertTrue(instance.getBooks().contains(savedBook));
  }

}
