package com.tienda.service;

import com.tienda.model.Producto;
import com.tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    /**
     * Regla de negocio: Permite realizar la venta de un producto restando la cantidad solicitada del stock.
     * Lanza una excepcion si no hay suficiente stock.
     */
    public Producto realizarVenta(Long id, Integer cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        // Restamos la cantidad del stock y guardamos el cambio
        producto.setStock(producto.getStock() - cantidad);
        return productoRepository.save(producto);
    }
}
