package pl.kurs.booktask.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookCommand {
    private Long id;
    private String title;
    private String author;
    private Integer publishedYear;
    private String isbn;
}
