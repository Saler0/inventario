package com.inventario.inventario.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "productos")

public class ProductoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("ID_PROD")
    private Integer ID_PROD;

    @Column
    @JsonProperty("nombre")
    private String NOMBRE;

    @Column
    @JsonProperty("descripcion")
    private String DESCRIPCION;

    @Column
    @JsonProperty("stock")
    private Integer STOCK;


    public Integer getID_PROD() {
        return ID_PROD;
    }

    public void setID_PROD(Integer ID_PROD) {
        this.ID_PROD = ID_PROD;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public Integer getSTOCK() {
        return STOCK;
    }

    public void setSTOCK(Integer STOCK) {
        this.STOCK = STOCK;
    }
}
