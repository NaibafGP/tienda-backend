package com.tienda.service;

import com.tienda.model.Producto;
import com.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks anotados con @Mock
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void realizarVenta_Exito_RestaStock() {
        // 1. Arrange (Preparar los datos de prueba y mockear el comportamiento)
        Producto mockProducto = new Producto(1L, "Laptop HP", 2500.0, 10);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(mockProducto));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Act (Ejecutar la accion a probar)
        Producto resultado = productoService.realizarVenta(1L, 3);

        // 3. Assert (Verificar que los resultados sean los esperados)
        assertNotNull(resultado);
        assertEquals(7, resultado.getStock()); // Tenia 10, vendio 3, deben quedar 7
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(mockProducto);
    }

    @Test
    void realizarVenta_Falla_StockInsuficiente() {
        // 1. Arrange (Preparar stock menor al solicitado, en este caso stock = 2)
        Producto mockProducto = new Producto(1L, "Mouse Logitech", 80.0, 2);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(mockProducto));

        // 2. Act & Assert (Ejecutar y comprobar que lance la excepcion esperada)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.realizarVenta(1L, 5); // Solicita 5 pero solo hay 2
        });

        // Comprobamos que el mensaje de error sea el correcto
        assertTrue(exception.getMessage().contains("Stock insuficiente"));
        
        // Verificamos que NUNCA se llamo a guardar el producto en BD porque fallo la validacion
        verify(productoRepository, never()).save(any(Producto.class));
    }
}
