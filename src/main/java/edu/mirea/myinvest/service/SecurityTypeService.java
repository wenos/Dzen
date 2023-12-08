package edu.mirea.myinvest.service;


import edu.mirea.myinvest.domain.model.SecurityType;
import edu.mirea.myinvest.exception.type.security.SecurityTypeNotFoundProblem;
import edu.mirea.myinvest.repository.SecurityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityTypeService {

    private final SecurityTypeRepository repository;


    public Optional<SecurityType> findById(Long id) {
        return repository.findById(id);
    }


    public SecurityType getById(Long id) {
        return findById(id).orElseThrow(() -> new SecurityTypeNotFoundProblem(id));
    }


    public List<SecurityType> getAll() {
        return repository.findAll();
    }

}
