package pl.kurs.booktask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.kurs.booktask.command.BookCommand;
import pl.kurs.booktask.command.UpdateBookCommand;
import pl.kurs.booktask.dto.BookDTO;
import pl.kurs.booktask.service.BookService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private BookDTO bookDTO;

    @MockBean
    private BookService bookService;

    @Test
    public void testGetAllBooks() throws Exception {
        // Given
        doReturn(List.of(bookDTO)).when(bookService).getAllBooks();
        doReturn(1L).when(bookDTO).getId();
        doReturn("Book Title").when(bookDTO).getTitle();
        doReturn("Author Name").when(bookDTO).getAuthor();

        // When/Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"title\":\"Book Title\",\"author\":\"Author Name\"}]"));
    }

    @Test
    public void testAddBook() throws Exception {
        // Given
        BookCommand command = new BookCommand();
        command.setTitle("New Book");
        command.setAuthor("New Author");

        doNothing().when(bookService).addBook(any(BookCommand.class));

        // When/Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(command))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Given
        UpdateBookCommand command = new UpdateBookCommand();
        command.setId(1L);
        command.setTitle("Updated Book Title");
        command.setAuthor("Updated Author");

        BookDTO updatedBook = new BookDTO();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Book Title");
        updatedBook.setAuthor("Updated Author");

        doReturn(updatedBook).when(bookService).updateBook(any(UpdateBookCommand.class));

        // When/Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(command))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"title\":\"Updated Book Title\",\"author\":\"Updated Author\"}"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Given
        Long bookId = 1L;
        doNothing().when(bookService).removeBook(bookId);

        // When/Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/books/{id}", bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookById() throws Exception {
        // Given
        Long bookId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookId);
        bookDTO.setTitle("Book Title");
        bookDTO.setAuthor("Author Name");

        doReturn(bookDTO).when(bookService).getBookById(bookId);

        // When/Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/books/{id}", bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"title\":\"Book Title\",\"author\":\"Author Name\"}"));
    }
}