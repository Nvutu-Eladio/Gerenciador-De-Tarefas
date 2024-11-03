package com.nveladio.com.ao.Gerenciador.De.Tarefas.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.nveladio.com.ao.Gerenciador.De.Tarefas.model.UserModel;
import com.nveladio.com.ao.Gerenciador.De.Tarefas.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public List<UserModel> listAllActiveUsers() {
        return userRepository.findByativoTrue();
    }

    public UserModel createUser(UserModel userModel) {
        if (userRepository.findByUsername(userModel.getUsername()) != null) {
            throw new IllegalArgumentException("Usuário já existe");
        }

        String passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);

        return userRepository.save(userModel);
    }
}
