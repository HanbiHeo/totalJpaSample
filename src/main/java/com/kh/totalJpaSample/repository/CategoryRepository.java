package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category>findByMemberEmail(String email);
}
