package io.github.vvcampos.service;

import io.github.vvcampos.api.dto.PedidoDTO;
import io.github.vvcampos.domain.entity.Pedido;
import io.github.vvcampos.domain.entity.enums.StatusPedido;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar ( PedidoDTO dto );

    Optional<Pedido> obterPedidoCompleto (Integer id);

    void atualizaStatus (Integer id, StatusPedido statusPedido);
}
