package io.github.vvcampos.service.impl;

import io.github.vvcampos.api.dto.ItemPedidoDTO;
import io.github.vvcampos.api.dto.PedidoDTO;
import io.github.vvcampos.domain.entity.Cliente;
import io.github.vvcampos.domain.entity.ItemPedido;
import io.github.vvcampos.domain.entity.Pedido;
import io.github.vvcampos.domain.entity.Produto;
import io.github.vvcampos.domain.entity.enums.StatusPedido;
import io.github.vvcampos.domain.repository.ClienteRepository;
import io.github.vvcampos.domain.repository.ItemPedidoRepository;
import io.github.vvcampos.domain.repository.PedidoRepository;
import io.github.vvcampos.domain.repository.ProdutoRepository;
import io.github.vvcampos.exception.PedidoNaoEncontradoException;
import io.github.vvcampos.exception.RegraNegocioException;
import io.github.vvcampos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;


    @Override
    @Transactional
    public Pedido salvar( PedidoDTO dto ) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidos = converterItens(pedido, dto.getItens());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id)
                .map( pedido -> {
                     pedido.setStatus(statusPedido);
                     return pedidoRepository.save(pedido);
                }).orElseThrow( () -> new PedidoNaoEncontradoException() );
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens");
        }

        return itens
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository
                        .findById(idProduto)
                        .orElseThrow(
                            () -> new RegraNegocioException(
                                    "Código de produto inválido: "+ idProduto
                            ));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());

    }
}
