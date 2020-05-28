package com.tanmoybanik.springgraphqldemo.repositories;

import com.tanmoybanik.springgraphqldemo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
