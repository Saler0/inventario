package com.inventario.inventario.repositories;

import com.inventario.inventario.models.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// los repository son las clases que hacen las consultas a la base de datos.
@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Integer> {
}
