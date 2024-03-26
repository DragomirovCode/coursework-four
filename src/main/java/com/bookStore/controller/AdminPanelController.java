package com.bookStore.controller;

import com.bookStore.entity.Book;
import com.bookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminPanelController {
    @Autowired
    private BookService service;
    @GetMapping("/admin_panel")
    public String getAdminPanel(){
        return "admin/adminPanel";
    }
    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b) {
        // Проверяем, что количество книг больше нуля
        if (b.getQuantity() <= 0) {
            // Обработка ошибки или вывод сообщения пользователю
            // Например, можно выбросить исключение или отобразить сообщение пользователю и
            // перенаправить его обратно на страницу ввода книги.
            return "redirect:/book_register";
        }
        service.save(b);
        return "redirect:/book_register";
    }
    @GetMapping("/book_register")
    public String bookRegister() {
        return "admin/bookRegister";
    }

}
