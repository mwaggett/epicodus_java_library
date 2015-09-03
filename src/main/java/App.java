import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.Map;

public class App {

  public static void main(String[] args) {

    staticFileLocation("/public"); // Relative path for images, css, etc.
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      List<Patron> patrons = Patron.all();

      model.put("patrons", patrons);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/admin", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Book> books = Book.all();
      model.put("books", books);
      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/admin/title/search", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String searchTitle = request.queryParams("booktitle");
      List<Book> searchResults = Book.search(searchTitle);

      model.put("books", searchResults); //books here refers to books from search result,
      model.put("template", "templates/admin.vtl");//use same template as the admin search page, itll look similar but be a different page
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/admin/author/search", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String searchAuthor = request.queryParams("bookauthor");
      List<Author> authorResults = Author.search(searchAuthor);
      ArrayList<Book> searchResults = new ArrayList<Book>();
      for (Author author : authorResults) {//for each author this search returns
        List<Book> authorBooks = author.getBooks();//get which books they've written
        for (Book book : authorBooks) {//for each book they've written
          searchResults.add(book);//add the book to the search results array
        }
      }

      model.put("books", searchResults); //books here refers to books from search result
      model.put("template", "templates/admin.vtl");//use same template as the admin search page, itll look similar but be a different page
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // get("/admin/books/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Book book = Book.find(Integer.parseInt(request.params(":id")));
    //
    //   model.put("book", book);
    //   model.put("template", "templates/edit-book-form.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

    get("/admin/books/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      List<Author> authors = Author.all();

      model.put("authors", authors);
      model.put("template", "templates/new-book-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/admin/books/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Book newBook = new Book(request.queryParams("title"));
      newBook.save();

      String newAuthorName = request.queryParams("newAuthor");
      String oldAuthorId = request.queryParams("oldAuthor");

      if (newAuthorName.equals("")) {
        Author author = Author.find(Integer.parseInt(oldAuthorId));
        newBook.addAuthor(author);
      } else {
        Author author = new Author(newAuthorName);
        author.save();
        newBook.addAuthor(author);
      }

      response.redirect("/admin");
      return null;
    });


    post("/patrons/create", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String addName = request.queryParams("name");

      Patron newPatron = new Patron(addName);
      newPatron.save();

      response.redirect("/");
      return null;
    });

  }

}
