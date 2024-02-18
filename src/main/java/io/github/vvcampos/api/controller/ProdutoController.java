package io.github.vvcampos.api.controller;

import io.github.vvcampos.domain.entity.Produto;
import io.github.vvcampos.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public Produto getProdutoById( @PathVariable  Integer id ) {
        return service.getProdutoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save ( @RequestBody @Valid Produto produto ) {
        return service.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete ( @PathVariable Integer id ) {
        service.delete(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody @Valid Produto produto ) {
        service.update(id, produto);
    }

    @GetMapping
    public List<Produto> find(Produto clienteFiltro) {
        return service.find(clienteFiltro);
    }
}
