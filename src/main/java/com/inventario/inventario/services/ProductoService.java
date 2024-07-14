package com.inventario.inventario.services;

import com.inventario.inventario.models.ProductoModel;
import com.inventario.inventario.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

// los service son intermediario entre controllers y repositories. Contiene la logica de negocio.
// cualquier operacion que requiera manipulacion de datos se debe hacer aqui.
@Service
public class ProductoService {
    @Autowired
    ProductoRepository productoRepository;

    public ArrayList<ProductoModel> getProductos(){
        return (ArrayList<ProductoModel>) productoRepository.findAll();
    }

    public Optional<ProductoModel> getById(Integer id){
        return productoRepository.findById(id);
    }

    public ProductoModel saveProducto(ProductoModel producto){
        return productoRepository.save(producto);
    }

    public ProductoModel updateById(ProductoModel request, Integer id){
        ProductoModel producto = productoRepository.findById(id).get();

        producto.setNOMBRE(request.getNOMBRE());
        producto.setDESCRIPCION(request.getDESCRIPCION());
        producto.setSTOCK(request.getSTOCK());

        return producto;
    }

    public Boolean deleteProducto (Integer id){
        try{
            productoRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
