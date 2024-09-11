package pl.kurs.booktask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "Books", description = "Operations related to managing books")
public class BookController {
    private final BookService bookService;

    @Operation(
            summary = "Add a new book",
            description = "Adds a new book to the collection based on the provided book command."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book added successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data provided",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    public ResponseEntity<Void> addBook(
            @RequestBody(
                    description = "Command containing information needed to create a new book",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BookCommand.class))
            ) BookCommand command) {
        bookService.addBook(command);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get all books",
            description = "Fetches a list of all books available in the collection."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of books retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(
            summary = "Update a book",
            description = "Updates the details of an existing book based on the provided update command."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping
    public ResponseEntity<BookDTO> updateBook(
            @RequestBody(description = "Command containing updated information for the book",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateBookCommand.class))
            ) UpdateBookCommand command) {
        return ResponseEntity.ok(bookService.updateBook(command));
    }

    @Operation(
            summary = "Delete a book",
            description = "Removes a book from the collection based on its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to be deleted", required = true)
            @PathVariable Long id) {
        bookService.removeBook(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get book by ID",
            description = "Fetches details of a specific book based on its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(
            @Parameter(description = "ID of the book to be retrieved", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}
