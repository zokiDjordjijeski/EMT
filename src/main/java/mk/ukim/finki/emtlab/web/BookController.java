package mk.ukim.finki.emtlab.web;

import mk.ukim.finki.emtlab.model.Book;
import mk.ukim.finki.emtlab.model.dtos.BookDto;
import mk.ukim.finki.emtlab.model.enums.Category;
import mk.ukim.finki.emtlab.service.BookService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private final BookService bookService;
    
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping("list-books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/list-categories")
    public List<String> getAllCategories() {
        return Arrays.stream(Category.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/paged")
    public List<Book> getBooksByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return bookService.getAllBooksByPage(PageRequest.of(page, size));
    }
    
    @GetMapping("/list-{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }
    
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDto bookDto) {
        Book book = bookService.save(bookDto);
        if (book == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(book);
    }
    
    @PutMapping("/update-{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        Book book = bookService.update(id, bookDto);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }
    
    @DeleteMapping("/delete-{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/mark-taken")
    public ResponseEntity<Void> markBookAsTaken(@PathVariable Long id) {
        bookService.markBookAsTaken(id);
        return ResponseEntity.ok().build();
    }
}
