package com.workpal.service;

import com.workpal.model.Personne;
import com.workpal.repository.PersonneRepository;

import java.util.Optional;

public class PersonneService {
    private final PersonneRepository personneRepository;

    public PersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    public String seConnecter(String email, String password) {
        Optional<Personne> personne = personneRepository.findByEmailAndPassword(email, password);
        if (personne.isPresent()) {
            return personne.get().getRole();
        }
        return null;
    }
}
