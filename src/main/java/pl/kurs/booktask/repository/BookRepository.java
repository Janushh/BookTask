package pl.kurs.booktask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.booktask.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
