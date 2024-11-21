package com.nveladio.com.ao.Gerenciador.De.Tarefas.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.nveladio.com.ao.Gerenciador.De.Tarefas.client.viacep.ViacepClient;
import com.nveladio.com.ao.Gerenciador.De.Tarefas.model.UserModel;
import com.nveladio.com.ao.Gerenciador.De.Tarefas.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ViacepClient viacepClient;

    public List<UserModel> listAllActiveUsers() {
        return userRepository.findByativoTrue();
    }

    public UserModel createUser(UserModel userModel) {
        if (userRepository.findByUsername(userModel.getUsername()) != null) {
            throw new IllegalArgumentException("Usuário já existe");
        }

        if (userModel.getCep() != null){
            var endereco = viacepClient.getEndereco(userModel.getCep());

            if (endereco != null){
                userModel.setEstado(endereco.uf());
                userModel.setCidade(endereco.localidade());
                userModel.setBairro(endereco.bairro());
                userModel.setRua(endereco.logradouro());
            }
        }


        String passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);


        return userRepository.save(userModel);
    }
}
