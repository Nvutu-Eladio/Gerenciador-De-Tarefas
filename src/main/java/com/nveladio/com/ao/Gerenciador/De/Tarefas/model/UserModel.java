package com.nveladio.com.ao.Gerenciador.De.Tarefas.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
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

//    @Column(name = "ddi", columnDefinition = "TEXT")
    private List<String> ddi;

//    @Column(name = "ddd", columnDefinition = "TEXT")
    private List<String> ddd;

//    @Column(name = "telefone", columnDefinition = "TEXT")
    private List<String> telefone;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String cep;
    @JsonIgnore
    private String numero;

    private Boolean ativo = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

}




