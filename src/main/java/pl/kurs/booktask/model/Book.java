package pl.kurs.booktask.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book implements Serializable {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(nullable = false)
     private String title;

     @Column(nullable = false)
     private String author;

     @Column(name = "published_year", nullable = false)
     private Integer publishedYear;

     @Column(nullable = false, unique = true)
     private String isbn;


}
