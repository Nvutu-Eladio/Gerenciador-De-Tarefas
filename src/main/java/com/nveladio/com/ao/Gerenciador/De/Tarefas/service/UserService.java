package com.nveladio.com.ao.Gerenciador.De.Tarefas.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.nveladio.com.ao.Gerenciador.De.Tarefas.client.viacep.ViacepClient;
import com.nveladio.com.ao.Gerenciador.De.Tarefas.model.UserModel;
import com.nveladio.com.ao.Gerenciador.De.Tarefas.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ViacepClient viacepClient;

    // Regex para validar DDI (por exemplo, 1-3 dígitos, como "55")
    private static final Pattern DDI_PATTERN = Pattern.compile("^\\d{1,3}$");

    // Regex para validar DDD (por exemplo, 2 dígitos, como "11")
    private static final Pattern DDD_PATTERN = Pattern.compile("^\\d{2}$");

    // Regex para validar telefone (por exemplo, 8 ou 9 dígitos, como "912345678")
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{8,9}$");

    public boolean isValidDdi(String ddi) {
        return DDI_PATTERN.matcher(ddi).matches();
    }

    public boolean isValidDdd(String ddd) {
        return DDD_PATTERN.matcher(ddd).matches();
    }

    public boolean isValidPhone(String telefone) {
        return PHONE_PATTERN.matcher(telefone).matches();
    }


    public List<UserModel> listAllActiveUsers() {
        return userRepository.findByativoTrue();
    }

    public UserModel createUser(UserModel userModel) {
        if (userRepository.findByUsername(userModel.getUsername()) != null) {
            throw new IllegalArgumentException("Usuário já existe");
        }

        validaCelular(userModel);


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

    private void validaCelular(UserModel userModel){

        if (userModel.getDdd() == null || userModel.getDdd().isEmpty() ||
                userModel.getDdd().stream().anyMatch(ddd -> !isValidDdd(ddd))) {
            throw new IllegalArgumentException("DDD inválido");
        }


        if (userModel.getDdi() == null || userModel.getDdi().isEmpty() ||
                userModel.getDdi().stream().anyMatch(ddi -> !isValidDdi(ddi))) {
            throw new IllegalArgumentException("DDI inválido");
        }

        if (userModel.getTelefone() == null || userModel.getTelefone().isEmpty() ||
                userModel.getTelefone().stream().anyMatch(telefone -> !isValidPhone(telefone))) {
            throw new IllegalArgumentException("Telefone inválido");
        }
    }



    // Use esse código não for trabalhar com listas
//    private void validaCelular(UserModel userModel){
//
//        if (!isValidDdd(userModel.getDdd()) || userModel.getDdd() == null){
//            throw new IllegalArgumentException("DDD inválido");
//        }
//
//        if (!isValidDdi(userModel.getDdi())){
//            throw new IllegalArgumentException("DDI inválido");
//        }
//
//        if (!isValidPhone(userModel.getTelefone())){
//            throw new IllegalArgumentException("Telefone inválido");
//        }
//    }

}
