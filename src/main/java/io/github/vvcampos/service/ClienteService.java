package io.github.vvcampos.service;

import io.github.vvcampos.domain.entity.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente getClienteById(Integer id );

    Cliente save(Cliente cliente );

    void deleteClienteById( Integer id );

    void update( Integer id, Cliente cliente ) ;

    List<Cliente> find(Cliente filtro);
}
