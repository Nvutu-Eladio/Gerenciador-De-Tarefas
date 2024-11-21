package com.nveladio.com.ao.Gerenciador.De.Tarefas.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_users")
public class UserModel {


    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;
    private String name;
    private String password;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String cep;

    private Boolean ativo = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
