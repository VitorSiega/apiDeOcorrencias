package com.example.aplicativo.produto.service;

import org.springframework.stereotype.Service;

import com.example.aplicativo.produto.dto.ProdutoDto;
import com.example.aplicativo.produto.model.ProdutoModel;
import com.example.aplicativo.produto.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void cadastrarProduto(ProdutoDto produtodto) {
        ProdutoModel produto = ProdutoModel.builder()
                .codigo(produtodto.codigo())
                .nome(produtodto.nome())
                .categoria(produtodto.categoria())
                .marca(produtodto.marca())
                .modelo(produtodto.modelo())
                .status(produtodto.status())
                .build();

        produtoRepository.save(produto);
    }
}
