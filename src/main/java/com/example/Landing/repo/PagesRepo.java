package com.example.Landing.repo;

import com.example.Landing.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagesRepo extends JpaRepository<Page, Long> {
}
