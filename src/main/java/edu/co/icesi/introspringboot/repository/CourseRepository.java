package edu.co.icesi.introspringboot.repository;

import edu.co.icesi.introspringboot.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    Optional<Course> findByName(String name);

    boolean existsByName(String name);

    List<Course> findByProfessor_NameOrderByName(String name);

    Page<Course> findByProfessor_Name(String name, Pageable pageable);

    List<Course> findByCreditsEquals(int credits, Pageable pageable);
    //List, o Page
}
