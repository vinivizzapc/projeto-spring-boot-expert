package io.github.vvcampos.api.controller;

import io.github.vvcampos.domain.entity.Cliente;
import io.github.vvcampos.service.ClienteService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Api("Api Clientes")
public class ClienteController {

    private ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    public Cliente getClienteById( @PathVariable @ApiParam("Id do cliente") Integer id ){
      return service.getClienteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 404, message = "Erro de validação")
    })
    public Cliente save( @RequestBody @Valid Cliente cliente ) {
        return service.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClienteById( @PathVariable Integer id ){
        service.deleteClienteById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente( @PathVariable Integer id, @RequestBody @Valid Cliente cliente ) {
        service.update(id, cliente);
    }

    @GetMapping
    public List<Cliente> findClientes(Cliente clienteFiltro) {
        return service.find(clienteFiltro);
    }
}
