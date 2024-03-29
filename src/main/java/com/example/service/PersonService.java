package com.example.service;

import com.example.entity.Person;
import com.example.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public void save(Person person){
        personRepository.save(person);
        person.setPassword(passwordEncoder.encode(person.getPassword())); // шифрование пароля
        person.setRole("ROLE_USER"); // роль для пользователей
    }

    public void update(int id, Person updatePerson){
        updatePerson.setId(id);
        personRepository.save(updatePerson);
    }

    public Optional<Person> getPersonByName(String name){
        return personRepository.findByUsername(name);
    }

    public void deleteById(int id) {
        personRepository.deleteById(id);
    }
    public Person getPersonById(int id) {
        return personRepository.findById(id).get();
    }

    public List<Person> getPersonByEmail(String email){
        return personRepository.findByEmail(email);
    }
}