package com.kevn.project.ecommerce.e_commerce.services;

import java.util.List;
import java.util.Objects;

import com.kevn.project.ecommerce.e_commerce.exception.BadRequestException;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.Carrito;
import com.kevn.project.ecommerce.e_commerce.models.Pedido;
import com.kevn.project.ecommerce.e_commerce.models.Producto;
import com.kevn.project.ecommerce.e_commerce.repositories.ICarrito;
import com.kevn.project.ecommerce.e_commerce.repositories.IPedido;
import com.kevn.project.ecommerce.e_commerce.strategy.ActualizarProducto;
import com.kevn.project.ecommerce.e_commerce.strategy.AgregarNuevoProducto;
import com.kevn.project.ecommerce.e_commerce.strategy.AgregarProducto;
import com.kevn.project.ecommerce.e_commerce.strategy.ModificarPedido;

@Service
@Log4j2
public class CarritoService implements IService<Carrito>,ModificarPedido {

    private final ICarrito repository;

    private final ProductoService serviceProducto;

    private final IPedido servicePedido;

    public CarritoService(ICarrito repository, ProductoService serviceProducto, IPedido servicePedido) {
        this.repository = repository;
        this.serviceProducto = serviceProducto;
        this.servicePedido = servicePedido;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!this.repository.existsById(id)) {
            throw new NotFoundException("El Item de producto no existe");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Carrito> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Carrito findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("El ItemProducto no existe con id: " + id));
    }

    @Override
    @Transactional
    public Carrito save(Carrito t) {
        if (Objects.isNull(t.getUsuario())) {
            throw new BadRequestException("No puedes crear un itemProducto porque no existe el usuario");
        }

        return repository.save(t);

    }

    @Override
    @Transactional
    public List<Carrito> saveAll(List<Carrito> list) {
        return repository.saveAll(list);
    }

    @Override
    @Transactional
    public void updateEstadoPedido(Carrito it){
        
        if(!verificarSiExistenProductos(it)){
            throw new NotFoundException("No existen productos en tu cuenta");
        }
        Pedido pedido = asignarEstado(it);
        it.setPedido(pedido);
        servicePedido.save(pedido);
        repository.save(it);
    }

    public boolean verificarSiExistenProductos(Carrito carrito){
        return !carrito.getProductos().isEmpty();
    }

    @Override
    public Pedido asignarEstado(Carrito t){

        Pedido pedido = (t.getPedido() == null) ? null : servicePedido.findById(t.getPedido().getId()).orElseThrow() ;
        if(pedido == null){
            pedido = new Pedido();
            pedido.setEstado("Pendiente");
        }else{
            pedido.setEstado("Enviado");
        }
        return pedido;
    }

    @Transactional
    public void agregarProducto(Long carritoId, Long productoId, int cantidad) {

        Carrito carritoDb = repository.findById(carritoId).orElseThrow( () -> new NullPointerException("El objeto es nulo"));
        Producto producto = serviceProducto.findById(productoId);
        AgregarProducto strategy = carritoDb.getProductos().stream().anyMatch(p -> p.getProducto().equals(producto))
                ? new ActualizarProducto()
                : new AgregarNuevoProducto();
        
        strategy.agregar(carritoDb, producto, cantidad);
        save(carritoDb);
    }

    @Transactional
    public void eliminarProducto(Long carritoId, Long productoId) {
        Carrito carritoDb = findById(carritoId);
        Producto producto = serviceProducto.findById(productoId);
        carritoDb.eliminarProducto(producto);
        repository.save(carritoDb);
    }

    // Elimina los productos que se encuentren en ItemProducto.
    // Utilizarlo para vaciar la lista
    @Transactional
    public void eliminarTodosLosProductos(Long carritoId) {
        Carrito carritoDb = findById(carritoId);
    
        // Limpiar la lista de productos (orphanRemoval = true se encargará de eliminar los ProductoCantidad)
        carritoDb.getProductos().clear();
        carritoDb.setPedidoNulo();
    
        // No es necesario llamar a repository.save(itemProductoDb) porque @Transactional se encarga de ello
    }
}