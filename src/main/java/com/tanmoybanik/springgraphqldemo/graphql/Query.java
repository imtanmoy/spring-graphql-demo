package com.tanmoybanik.springgraphqldemo.graphql;

import com.tanmoybanik.springgraphqldemo.entities.Author;
import com.tanmoybanik.springgraphqldemo.entities.Book;
import com.tanmoybanik.springgraphqldemo.repositories.AuthorRepository;
import com.tanmoybanik.springgraphqldemo.repositories.BookRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public long countBooks() {
        return bookRepository.count();
    }
    public long countAuthors() {
        return authorRepository.count();
    }
}