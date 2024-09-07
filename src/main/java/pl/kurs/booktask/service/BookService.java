package pl.kurs.booktask.service;

import pl.kurs.booktask.command.BookCommand;
import pl.kurs.booktask.command.UpdateBookCommand;
import pl.kurs.booktask.dto.BookDTO;

import java.util.List;

public interface BookService {
    void addBook(BookCommand command);
    void removeBook(Long id);
    List<BookDTO> getAllBooks();
    BookDTO updateBook(UpdateBookCommand bookCommand);
    BookDTO getBookById(Long id);
}
