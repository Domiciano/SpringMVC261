package edu.co.icesi.introspringboot.service.impl;

import edu.co.icesi.introspringboot.entity.Professor;
import edu.co.icesi.introspringboot.repository.ProfessorRepository;
import edu.co.icesi.introspringboot.service.ProfessorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public Professor saveProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Professor> findAll() {
        List<Professor> result = new ArrayList<>();
        professorRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Optional<Professor> findById(Integer id) {
        return professorRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        professorRepository.deleteById(id);
    }
}
