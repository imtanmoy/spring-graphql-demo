package com.tanmoybanik.springgraphqldemo.graphql;

import com.tanmoybanik.springgraphqldemo.entities.Author;
import com.tanmoybanik.springgraphqldemo.entities.Book;
import com.tanmoybanik.springgraphqldemo.exceptions.BookNotFoundException;
import com.tanmoybanik.springgraphqldemo.repositories.AuthorRepository;
import com.tanmoybanik.springgraphqldemo.repositories.BookRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author newAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorRepository.save(author);

        return author;
    }

    public Book newBook(String title, String isbn, Integer pageCount, Long authorId) {
        Book book = new Book();
        book.setAuthor(new Author(authorId));
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount != null ? pageCount : 0);

        bookRepository.save(book);

        return book;
    }

    public boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = null;
        if (optionalBook.isPresent()){
            book = optionalBook.get();
        }
        if(book == null) {
            throw new BookNotFoundException("The book to be updated was found", id);
        }
        book.setPageCount(pageCount);
        bookRepository.save(book);
        return book;
    }
}
