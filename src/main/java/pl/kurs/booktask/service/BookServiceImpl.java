package pl.kurs.booktask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.booktask.command.BookCommand;
import pl.kurs.booktask.command.UpdateBookCommand;
import pl.kurs.booktask.dto.BookDTO;
import pl.kurs.booktask.exception.BookException;
import pl.kurs.booktask.model.Book;
import pl.kurs.booktask.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addBook(BookCommand command) {
        bookRepository.save(modelMapper.map(command, Book.class));
        log.info("Book added successfully: {}", command);
    }

    @Override
    public void removeBook(Long id) {
        bookRepository.deleteById(id);
        log.info("Book removed successfully with ID: {}", id);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<BookDTO> books = bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
        log.info("Fetched all books, total count: {}", books.size());
        return books;
    }

    @Override
    public BookDTO updateBook(UpdateBookCommand updateBookCommand) {
        Book book = bookRepository.findById(updateBookCommand.getId())
                .orElseThrow(() -> new BookException("Book not found with id: " + updateBookCommand.getId()));
        modelMapper.map(updateBookCommand, book);
        BookDTO updatedBook = modelMapper.map(bookRepository.save(book), BookDTO.class);
        log.info("Book updated successfully: {}", updatedBook);
        return updatedBook;
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookException("Book not found with id: " + id));
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        log.info("Fetched book by ID: {}", id);
        return bookDTO;
    }
}

