package com.example.aplicativo.produto.dto;

public record ProdutoDto(
        Long codigo,
        String nome,
        String categoria,
        String marca,
        String modelo,
        String status) {

}
