package edu.co.icesi.introspringboot.service;

import edu.co.icesi.introspringboot.entity.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {

    Professor saveProfessor(Professor professor);

    List<Professor> findAll();

    Optional<Professor> findById(Integer id);

    void deleteById(Integer id);
}
