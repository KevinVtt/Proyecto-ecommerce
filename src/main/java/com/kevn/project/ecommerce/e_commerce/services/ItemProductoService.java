package com.kevn.project.ecommerce.e_commerce.services;

import java.util.List;
import java.util.Objects;

import com.kevn.project.ecommerce.e_commerce.exception.BadRequestException;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;
import com.kevn.project.ecommerce.e_commerce.models.Pedido;
import com.kevn.project.ecommerce.e_commerce.models.Producto;
import com.kevn.project.ecommerce.e_commerce.repositories.IItemProducto;
import com.kevn.project.ecommerce.e_commerce.repositories.IPedido;
import com.kevn.project.ecommerce.e_commerce.repositories.IProductoCantidad;
import com.kevn.project.ecommerce.e_commerce.strategy.ActualizarProducto;
import com.kevn.project.ecommerce.e_commerce.strategy.AgregarNuevoProducto;
import com.kevn.project.ecommerce.e_commerce.strategy.AgregarProducto;
import com.kevn.project.ecommerce.e_commerce.strategy.ModificarPedido;

@Service
@Log4j2
public class ItemProductoService implements IService<ItemProducto>,ModificarPedido {

    private final IItemProducto repository;

    private final ProductoService serviceProducto;

    private final IPedido servicePedido;

    private final IProductoCantidad productoCantidadRepository;

    public ItemProductoService(IItemProducto repository, ProductoService serviceProducto, IPedido servicePedido,
            IProductoCantidad productoCantidadRepository) {
        this.repository = repository;
        this.serviceProducto = serviceProducto;
        this.servicePedido = servicePedido;
        this.productoCantidadRepository = productoCantidadRepository;
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
    public List<ItemProducto> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemProducto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("El ItemProducto no existe con id: " + id));
    }

    @Override
    @Transactional
    public ItemProducto save(ItemProducto t) {
        if (Objects.isNull(t.getUsuario())) {
            throw new BadRequestException("No puedes crear un itemProducto porque no existe el usuario");
        }

        return repository.save(t);

    }

    @Override
    @Transactional
    public List<ItemProducto> saveAll(List<ItemProducto> list) {
        return repository.saveAll(list);
    }

    @Transactional
    public void updateEstadoPedido(ItemProducto it){
        
        Pedido pedido = asignarEstado(it);
        it.setPedido(pedido);
        servicePedido.save(pedido);
        repository.save(it);
    }

    @Override
    public Pedido asignarEstado(ItemProducto t){

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
    public void agregarProducto2(Long itemProductoId, Long productoId, int cantidad) {

        ItemProducto itemProducto = repository.findById(itemProductoId).orElseThrow();
        Producto producto = serviceProducto.findById(productoId);

        AgregarProducto strategy = itemProducto.getProductos().stream().anyMatch(p -> p.getProducto().equals(producto))
                ? new ActualizarProducto()
                : new AgregarNuevoProducto();

        strategy.agregar(itemProducto, producto, cantidad);
        repository.save(itemProducto);

    }

    @Transactional
    public void eliminarProducto(Long itemProductoId, Long productoId) {
        ItemProducto itemProductoDb = findById(itemProductoId);
        Producto producto = serviceProducto.findById(productoId);
        itemProductoDb.getProductos().removeIf(p -> p.getProducto().equals(producto));
        repository.save(itemProductoDb);
    }

    // Elimina los productos que se encuentren en ItemProducto.
    // Utilizarlo para vaciar la lista
    @Transactional
    public void eliminarTodosLosProductos(Long itemProductoId) {
        ItemProducto itemProductoDb = findById(itemProductoId);

        // Eliminar todos los ProductoCantidad asociados al ItemProducto
        productoCantidadRepository.deleteAll(itemProductoDb.getProductos());

        // Limpiar la lista en memoria
        itemProductoDb.getProductos().clear();
        // Seteamos nulo el pedido
        itemProductoDb.setPedido(null);

        // Guardar los cambios (opcional, ya que @Transactional se encarga de esto)
        // repository.save(itemProductoDb);
    }
}