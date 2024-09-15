package pl.kurs.booktask.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testAddBook() throws Exception {
        BookCommand command = new BookCommand();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAllBooks() throws Exception {
        BookDTO bookDTO = new BookDTO();
        given(bookService.getAllBooks()).willReturn(Collections.singletonList(bookDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(bookDTO.getId()))
                .andDo(print());
    }

    @Test
    public void testDeleteBook() throws Exception {
        Long bookId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/{id}", bookId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetBookById() throws Exception {
        Long bookId = 1L;
        BookDTO bookDTO = new BookDTO();
        given(bookService.getBookById(bookId)).willReturn(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/{id}", bookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookDTO.getId()))
                .andDo(print());
    }
}