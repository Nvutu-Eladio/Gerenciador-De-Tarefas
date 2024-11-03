package com.nveladio.com.ao.Gerenciador.De.Tarefas.repository;

import com.nveladio.com.ao.Gerenciador.De.Tarefas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {

    List<UserModel> findByativoTrue();

    UserModel findByUsername(String username);
}
