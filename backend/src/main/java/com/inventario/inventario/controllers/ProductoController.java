package com.inventario.inventario.controllers;

import com.inventario.inventario.models.ProductoModel;
import com.inventario.inventario.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
//aqui se definen las peticiones HTTP y rutas
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ArrayList<ProductoModel> getProductos(){
        return this.productoService.getProductos();
    }

    @GetMapping(path = "/{id}")
    public Optional <ProductoModel> getProductoById(@PathVariable("id") Integer id){
        return this.productoService.getById(id);
    }

    @PostMapping
    public ProductoModel saveProducto(@RequestBody ProductoModel producto){
        return this.productoService.saveProducto(producto);
    }

    @PutMapping(path = "/{id}")
    public ProductoModel updateProductoById(@RequestBody ProductoModel request,@PathVariable("id") Integer id){
        return this.productoService.updateById(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public  String deleteById(@PathVariable("id") Integer id){
        boolean ok = this.productoService.deleteProducto(id);

        if(ok){
            return "Producto con id " + id + " deleted!";
        }else {
            return "Error, hay un problema y no se pudo eliminar el id";
        }
    }
}
