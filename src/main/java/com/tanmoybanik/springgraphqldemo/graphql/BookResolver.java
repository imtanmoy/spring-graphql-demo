package com.tanmoybanik.springgraphqldemo.graphql;

import com.tanmoybanik.springgraphqldemo.entities.Author;
import com.tanmoybanik.springgraphqldemo.entities.Book;
import com.tanmoybanik.springgraphqldemo.repositories.AuthorRepository;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Book book) {
        Optional<Author> optionalAuthor = authorRepository.findById(book.getAuthor().getId());
        return optionalAuthor.orElse(null);
    }
}
