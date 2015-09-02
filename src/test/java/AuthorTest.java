import org.junit.*;
import static org.junit.Assert.*;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Author.all().size());
  }

  @Test
  public void getName_returnsName() {
    Author instance = new Author("Rick Ransom");
    assertEquals("Rick Ransom", instance.getName());
  }

  @Test
  public void getId_returnsIdAfterSave() {
    Author instance = new Author("Rick Ransom");
    instance.save();
    assertEquals(Author.all().get(0).getId(), instance.getId());
  }

  @Test
  public void equals_returnsTrueWhenParamsMatch() {
    Author firstInstance = new Author("Rick Ransom");
    Author secondInstance = new Author("Rick Ransom");
    assertEquals(true, firstInstance.equals(secondInstance));
  }

  @Test
  public void equals_returnsFalseWhenParamsMatch() {
    Author firstInstance = new Author("Rick Ransom");
    Author secondInstance = new Author("Butts McKenzie");
    assertEquals(false, firstInstance.equals(secondInstance));
  }

  @Test
  public void equals_returnsFalseAfterSave() {
    Author firstInstance = new Author("Rick Ransom");
    firstInstance.save();
    Author secondInstance = new Author("Rick Ransom");
    secondInstance.save();
    assertEquals(false, firstInstance.equals(secondInstance));
  }

  @Test
  public void save_addsToDatabase() {
    Author instance = new Author("Rick Ransom");
    instance.save();
    assertEquals(Author.all().get(0), instance);
  }

  @Test
  public void find_findsAuthorsInDatabase() {
    Author instance = new Author("Rick Ransom");
    instance.save();
    assertEquals(instance, Author.find(instance.getId()));
  }

  @Test
  public void update_changesName() {
    Author instance = new Author("Rick Ransom");
    instance.save();
    instance.update("Butts McKenzie");
    assertEquals("Butts McKenzie", Author.all().get(0).getName());
    assertEquals("Butts McKenzie", instance.getName());
  }

  @Test
  public void delete_deletesFromDatabase() {
    Author instance = new Author("Rick Ransom");
    instance.save();
    instance.delete();
    assertEquals(0, Author.all().size());
  }

  @Test
  public void getBooks_returnsAddedBooks() {
    Author instance = new Author("Rick Ransom");
    instance.save();
    Book newBook = new Book("Rick Ransom");
    newBook.save();
    instance.addBook(newBook);
    assertTrue(instance.getBooks().contains(newBook));
  }
}
