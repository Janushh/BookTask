package pl.kurs.booktask.service;

import lombok.RequiredArgsConstructor;
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
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addBook(BookCommand command) {
        bookRepository.save(modelMapper.map(command, Book.class));
    }

    @Override
    public void removeBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    @Override
    public BookDTO updateBook(UpdateBookCommand updateBookCommand) {
        Book book = bookRepository.findById(updateBookCommand.getId())
                .orElseThrow(() -> new BookException("Book not found with id: " + updateBookCommand.getId()));
        modelMapper.map(updateBookCommand, book);
        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookException("Book not found with id: " + id));
        return modelMapper.map(book, BookDTO.class);
    }
}

