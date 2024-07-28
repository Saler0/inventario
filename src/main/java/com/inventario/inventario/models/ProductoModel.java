package com.inventario.inventario.models;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")

public class ProductoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_PROD;

    @Column
    private String NOMBRE;

    @Column
    private String DESCRIPCION;

    @Column
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
