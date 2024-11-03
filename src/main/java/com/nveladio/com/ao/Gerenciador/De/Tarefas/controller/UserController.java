package com.nveladio.com.ao.Gerenciador.De.Tarefas.controller;


import com.nveladio.com.ao.Gerenciador.De.Tarefas.model.UserModel;
import com.nveladio.com.ao.Gerenciador.De.Tarefas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserModel>> listAllActiveUsers() {
        List<UserModel> users = userService.listAllActiveUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody UserModel userModel) {
        try {
            var userCreated = userService.createUser(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
