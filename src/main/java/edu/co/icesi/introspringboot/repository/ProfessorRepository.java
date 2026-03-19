package edu.co.icesi.introspringboot.repository;

import edu.co.icesi.introspringboot.entity.Professor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfessorRepository extends CrudRepository<Professor, Integer> {

    List<Professor> findByNameContainingIgnoreCase(String segment);

}
