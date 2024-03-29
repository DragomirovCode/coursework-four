package com.bookStore.controller;


import com.bookStore.entity.Person;
import com.bookStore.service.PersonService;
import com.bookStore.service.RegistrationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationServices registrationServices;

    private final PersonService personService;

    @Autowired
    public AuthController(RegistrationServices registrationServices, PersonService personService) {
        this.registrationServices = registrationServices;
        this.personService = personService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }
    @GetMapping("/registration")
    public String FormRegistration(@ModelAttribute("person") Person person){
        return "auth/registration";
    }
    @PostMapping("/registration")
    public String register(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "auth/registration";
        }
        // Проверяем, существует ли пользователь с таким же адресом электронной почты
        Optional<Person> existingUsers = personService.getPersonByName(person.getUsername());
        if (existingUsers.isPresent()) {
            // Если пользователь существует, необходимо предпринять действия, например, вернуть ошибку или выбросить исключение
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        // Если пользователя с таким адресом электронной почты не существует, сохраняем нового пользователя
        registrationServices.register(person);
        return "redirect:/auth/login";
    }
}