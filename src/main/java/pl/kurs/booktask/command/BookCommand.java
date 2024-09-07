package pl.kurs.booktask.command;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookCommand {
    private String title;
    private String author;
    private Integer publishedYear;
    private String isbn;

}
