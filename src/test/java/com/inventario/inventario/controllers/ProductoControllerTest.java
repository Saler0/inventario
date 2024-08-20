package com.inventario.inventario.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.inventario.inventario.models.ProductoModel;
import com.inventario.inventario.services.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    public void testSaveProducto() throws Exception {

        ProductoModel producto = new ProductoModel();
        producto.setID_PROD(1);
        producto.setNOMBRE("Coca Cola");
        producto.setDESCRIPCION("Bebida 3 litros");
        producto.setSTOCK(100);


        when(productoService.saveProducto(any(ProductoModel.class))).thenReturn(producto);

        mockMvc.perform(post("/producto")
                        .contentType("application/json")
                        .content(asJsonString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_PROD").value(1))
                .andExpect(jsonPath("$.nombre").value("Coca Cola"))
                .andExpect(jsonPath("$.descripcion").value("Bebida 3 litros"))
                .andExpect(jsonPath("$.stock").value(100));


    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetProductoById() throws Exception {

        ProductoModel producto = new ProductoModel();
        producto.setID_PROD(1);
        producto.setNOMBRE("Producto1");
        producto.setDESCRIPCION("Producto de prueba");
        producto.setSTOCK(200);

        when(productoService.getById(1)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_PROD").value(1))
                .andExpect(jsonPath("$.nombre").value("Producto1"))
                .andExpect(jsonPath("$.descripcion").value("Producto de prueba"))
                .andExpect(jsonPath("$.stock").value(200));
    }


    @Test
    public void testUpdateProductoById() throws Exception {

        ProductoModel producto = new ProductoModel();
        producto.setID_PROD(1);
        producto.setNOMBRE("Bebida Acuenta");
        producto.setDESCRIPCION("Producto Actualizado");
        producto.setSTOCK(150);

        when(productoService.updateById(any(ProductoModel.class), any(Integer.class))).thenReturn(producto);

        mockMvc.perform(put("/producto/1")
                        .contentType("application/json")
                        .content("{\"$.id_PROD\":1,\"nombre\":\"Bebida Acuenta\",\"descripcion\":\"Producto Actualizado\",\"stock\":150}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_PROD").value(1))
                .andExpect(jsonPath("$.nombre").value("Bebida Acuenta"))
                .andExpect(jsonPath("$.descripcion").value("Producto Actualizado"))
                .andExpect(jsonPath("$.stock").value(150));
    }

    @Test
    public void testDeleteById() throws Exception {
        // Mock del servicio
        when(productoService.deleteProducto(1)).thenReturn(true);

        mockMvc.perform(delete("/producto/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto con id 1 deleted!"));
    }

}
