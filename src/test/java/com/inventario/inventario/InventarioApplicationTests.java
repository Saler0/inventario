package com.inventario.inventario;

import com.inventario.inventario.controllers.ProductoControllerTest;
import com.inventario.inventario.services.ProductoServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ProductoControllerTest.class,
        ProductoServiceTest.class
})
class InventarioApplicationTests {

}
