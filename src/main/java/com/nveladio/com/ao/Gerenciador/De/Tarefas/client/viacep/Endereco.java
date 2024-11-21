package com.nveladio.com.ao.Gerenciador.De.Tarefas.client.viacep;

public record Endereco(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf
) {
}
