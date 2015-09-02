import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Book.all().size());
  }

  @Test
  public void getTitle_returnsTitle() {
    Book instance = new Book("Moby Dick");
    assertEquals("Moby Dick", instance.getTitle());
  }

  @Test
  public void getId_returnsIdAfterSave() {
    Book instance = new Book("Moby Dick");
    instance.save();
    assertEquals(Book.all().get(0).getId(), instance.getId());
  }

  @Test
  public void equals_returnsTrueWhenParamsMatch() {
    Book firstInstance = new Book("Moby Dick");
    Book secondInstance = new Book("Moby Dick");
    assertEquals(true, firstInstance.equals(secondInstance));
  }

  @Test
  public void equals_returnsFalseWhenDifferent() {
    Book firstInstance = new Book("Moby Dick");
    Book secondInstance = new Book("Great Expectations");
    assertEquals(false, firstInstance.equals(secondInstance));
  }

  @Test
  public void equals_returnsFalseAfterSave() {
    Book firstInstance = new Book("Moby Dick");
    firstInstance.save();
    Book secondInstance = new Book("Moby Dick");
    secondInstance.save();
    assertEquals(false, firstInstance.equals(secondInstance));
  }

  @Test
  public void save_addsToDatabase() {
    Book instance = new Book("Moby Dick");
    instance.save();
    assertEquals(Book.all().get(0), instance);
  }

  @Test
  public void find_findsBooksInDatabase() {
    Book instance = new Book("Moby Dick");
    instance.save();
    assertEquals(instance, Book.find(instance.getId()));
  }

  @Test
  public void update_changesTitle() {
    Book instance = new Book("Moby Dick");
    instance.save();
    instance.update("Great Expectations");
    assertEquals("Great Expectations", Book.all().get(0).getTitle());
    assertEquals("Great Expectations", instance.getTitle());
  }

  @Test
  public void delete_deletesFromDatabase() {
    Book instance = new Book("Moby Dick");
    instance.save();
    instance.delete();
    assertEquals(0, Book.all().size());
  }

  @Test
  public void getAuthors_returnsAddedAuthors() {
    Book instance = new Book("Moby Dick");
    instance.save();
    Author newAuthor = new Author("Rick Ransom");
    newAuthor.save();
    instance.addAuthor(newAuthor);
    assertTrue(instance.getAuthors().contains(newAuthor));
  }

  @Test
  public void search_returnsPartialMatches() {
    Book instance = new Book("Moby Dick");
    instance.save();
    List<Book> searchResults = Book.search("ob");
    assertTrue(searchResults.contains(instance));
  }

  @Test
  public void numberOfCopies_countsBooksWithSameTitle() {
    Book firstInstance = new Book("Moby Dick");
    firstInstance.save();
    Book secondInstance = new Book("Moby Dick");
    secondInstance.save();
    assertEquals(2, firstInstance.numberOfCopies());
  }
}
