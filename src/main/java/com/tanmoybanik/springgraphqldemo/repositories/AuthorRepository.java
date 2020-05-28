package com.tanmoybanik.springgraphqldemo.repositories;

import com.tanmoybanik.springgraphqldemo.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
