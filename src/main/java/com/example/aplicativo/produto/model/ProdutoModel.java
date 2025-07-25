package com.example.aplicativo.produto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "produto")
@Entity
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigoproduto", nullable = false, unique = true, columnDefinition = "BIGINT")
    private long codigo;

    @Column(name = "nomeproduto", nullable = false, unique = false, columnDefinition = "VARCHAR(100)")
    private String nome;

    @Column(name = "categoriaproduto", nullable = false, unique = false, columnDefinition = "VARCHAR(100)")
    private String categoria;

    @Column(name = "marcaproduto", nullable = false, unique = false, columnDefinition = "VARCHAR(100)")
    private String marca;

    @Column(name = "modeloproduto", nullable = false, unique = false, columnDefinition = "VARCHAR(100)")
    private String modelo;

    @Column(name = "statusproduto", nullable = false, unique = false, columnDefinition = "VARCHAR(50)")
    private String status;
}
