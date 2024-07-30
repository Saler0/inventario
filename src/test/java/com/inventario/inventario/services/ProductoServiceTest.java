package com.inventario.inventario.services;

import static org.junit.jupiter.api.Assertions.*;

import com.inventario.inventario.models.ProductoModel;
import com.inventario.inventario.repositories.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    public void testGetProductos() {
        ArrayList<ProductoModel> productos = new ArrayList<>();
        productos.add(new ProductoModel());
        when(productoRepository.findAll()).thenReturn(productos);

        ArrayList<ProductoModel> result = productoService.getProductos();

        assertEquals(1, result.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    public void testGetById() {
        ProductoModel producto = new ProductoModel();
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Optional<ProductoModel> result = productoService.getById(1);

        assertTrue(result.isPresent());
        assertEquals(producto, result.get());
        verify(productoRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveProducto() {
        ProductoModel producto = new ProductoModel();
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(producto);

        ProductoModel result = productoService.saveProducto(producto);

        assertEquals(producto, result);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    public void testUpdateById() {
        ProductoModel producto = new ProductoModel();
        producto.setID_PROD(1);
        producto.setNOMBRE("Original");
        ProductoModel request = new ProductoModel();
        request.setNOMBRE("Updated");

        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(producto);

        ProductoModel result = productoService.updateById(request, 1);

        assertEquals("Updated", result.getNOMBRE());
        verify(productoRepository, times(1)).findById(1);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    public void testDeleteProducto() {
        doNothing().when(productoRepository).deleteById(1);

        Boolean result = productoService.deleteProducto(1);

        assertTrue(result);
        verify(productoRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteProductoException() {
        doThrow(new RuntimeException()).when(productoRepository).deleteById(1);

        Boolean result = productoService.deleteProducto(1);

        assertFalse(result);
        verify(productoRepository, times(1)).deleteById(1);
    }
}
