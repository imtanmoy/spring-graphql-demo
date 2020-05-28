package com.tanmoybanik.springgraphqldemo;

import com.tanmoybanik.springgraphqldemo.entities.Author;
import com.tanmoybanik.springgraphqldemo.entities.Book;
import com.tanmoybanik.springgraphqldemo.exceptions.GraphQLErrorAdapter;
import com.tanmoybanik.springgraphqldemo.graphql.BookResolver;
import com.tanmoybanik.springgraphqldemo.graphql.Mutation;
import com.tanmoybanik.springgraphqldemo.graphql.Query;
import com.tanmoybanik.springgraphqldemo.repositories.AuthorRepository;
import com.tanmoybanik.springgraphqldemo.repositories.BookRepository;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringGraphqlDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGraphqlDemoApplication.class, args);
    }

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new GraphQLErrorHandler() {
            @Override
            public List<GraphQLError> processErrors(List<GraphQLError> errors) {
                List<GraphQLError> clientErrors = errors.stream()
                        .filter(this::isClientError)
                        .collect(Collectors.toList());

                List<GraphQLError> serverErrors = errors.stream()
                        .filter(e -> !isClientError(e))
                        .map(GraphQLErrorAdapter::new)
                        .collect(Collectors.toList());

                List<GraphQLError> e = new ArrayList<>();
                e.addAll(clientErrors);
                e.addAll(serverErrors);
                return e;
            }

            protected boolean isClientError(GraphQLError error) {
                return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
            }
        };
    }

    @Bean
    public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
        return (args) -> {
            Author author = new Author("Herbert", "Schildt");
            authorRepository.save(author);

            bookRepository.save(new Book("Java: A Beginner's Guide, Sixth Edition", "0071809252", 728, author));
        };
    }
}
