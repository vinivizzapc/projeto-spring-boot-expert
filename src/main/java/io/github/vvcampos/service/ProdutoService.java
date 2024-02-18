package io.github.vvcampos.service;

import io.github.vvcampos.domain.entity.Produto;

import java.util.List;

public interface ProdutoService {

    Produto getProdutoById(Integer id );

    Produto save (Produto produto );

    void delete (Integer id );

    void update (Integer id, Produto produto );

    List<Produto> find (Produto filtro);

}