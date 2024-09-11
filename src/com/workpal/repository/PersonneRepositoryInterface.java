package com.workpal.repository;

import com.workpal.model.Personne;
import java.util.Optional;

public interface PersonneRepositoryInterface {
    Optional<Personne> findByEmailAndPassword(String email, String password);
}
