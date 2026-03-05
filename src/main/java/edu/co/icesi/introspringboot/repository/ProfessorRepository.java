package edu.co.icesi.introspringboot.repository;

import edu.co.icesi.introspringboot.entity.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor, Long> {
}
