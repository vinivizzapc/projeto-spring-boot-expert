package io.github.vvcampos.api.controller;

import io.github.vvcampos.domain.entity.Cliente;
import io.github.vvcampos.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public Cliente getById( @PathVariable Integer id ){
      return service.getClienteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
