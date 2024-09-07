package pl.kurs.booktask.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.booktask.command.BookCommand;
import pl.kurs.booktask.command.UpdateBookCommand;
import pl.kurs.booktask.dto.BookDTO;
import pl.kurs.booktask.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody BookCommand command) {
        bookService.addBook(command);
        log.info("Book added successfully");
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get all books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PutMapping
    public ResponseEntity<BookDTO> updateBook(@RequestBody UpdateBookCommand command) {
        return ResponseEntity.ok(bookService.updateBook(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.removeBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}
