package com.kevn.project.ecommerce.e_commerce.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;
import com.kevn.project.ecommerce.e_commerce.models.Pedido;
import com.kevn.project.ecommerce.e_commerce.models.Producto;
import com.kevn.project.ecommerce.e_commerce.models.ProductoCantidad;
import com.kevn.project.ecommerce.e_commerce.repositories.IItemProducto;
import com.kevn.project.ecommerce.e_commerce.repositories.IProductoCantidad;

@Service
public class ItemProductoService implements IService<ItemProducto> {


    private static final Logger logger = LoggerFactory.getLogger(ItemProductoService.class);

    @Autowired
    private IItemProducto repository;

    @Autowired
    private ProductoService repositoryProducto;

    @Autowired
    private PedidoService servicePedido;

    @Autowired
    private IProductoCantidad productoCantidadRepository;


    @Override
    @Transactional
    public void delete(Long id) {
/*
es innecesario hacer esto ya que findById(id), lanza una excepción si no encuentra el id
no necesitas envolverlo en un Optional ya que nunca será null
        Optional<ItemProducto> optional = Optional.of(findById(id));
        if (optional.isPresent()) {
            repository.delete(optional.orElseThrow());
        } else {
            throw new NotFoundException("Error al eliminar el ItemProducto con id: " + id);
        }
*/
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
        if (t.getId() != null && t.getId() > 0) {
            ItemProducto itemProductoDb = findById(t.getId());
            if (itemProductoDb.getPedido() == null) {
                Pedido pedido = new Pedido();
                pedido.setEstado("Pendiente");
                pedido.setFecha(LocalDateTime.now());
                servicePedido.save(pedido);
                itemProductoDb.setPedido(pedido);
                return repository.save(itemProductoDb);
            }
            return repository.save(itemProductoDb);
        } else {
            if (t.getUsuario() == null) {
                throw new RuntimeException("No puedes crear un itemProducto porque no existe el usuario");
            }
            return repository.save(t);
        }
    }

    @Override
    @Transactional
    public List<ItemProducto> saveAll(List<ItemProducto> list) {
        return (List<ItemProducto>) repository.saveAll(list);
    }

    @Transactional
    public void agregarProducto(Long itemProductoId, Long productoId, int cantidad) {

        // Buscar el ItemProductoId.
        ItemProducto itemProductoDb = repository.findById(itemProductoId).orElseThrow();
        // if(itemProductoDb == null){
        //     repository.save(new ItemProducto());
        // }
        logger.info("ID del itemProductoDb: " + itemProductoDb.getId());
        // Buscar el productoId
        Producto producto = repositoryProducto.findById(productoId);
        logger.info("Producto: " + producto.getNombre() + " producto id: " + Long.toString(producto.getId()));
        /* Si existe el mismo producto tenemos que subirle la cantidad en productoCantidad y bajarle la cantidad en producto */
        List<ProductoCantidad> listaProductoCantidad_itemProductoDb = itemProductoDb.getProductos();

        // Verificamos si existe el mismo producto en nuestra listaProductoCantidadDb del itemProducto.
        logger.info("entrando al existeProductoEnItemProducto");
        // ProductoCantidad existeProductoEnItemProducto =  listaProductoCantidad_itemProductoDb.stream().filter(p -> p.getProducto().getId().equals(producto.getId())).findFirst().orElseGet(null);
        boolean bandera = false;
        int indice = 0;
        ProductoCantidad productoCantidad_ItemProducto = new ProductoCantidad();
        int tam = listaProductoCantidad_itemProductoDb.size();
        while (!bandera && indice < tam) {
            bandera = (listaProductoCantidad_itemProductoDb
                    .get(indice)
                    .getProducto() != null &&
                    listaProductoCantidad_itemProductoDb
                            .get(indice)
                            .getProducto().equals(producto)) ? true : false;
            if (bandera) {
                productoCantidad_ItemProducto = listaProductoCantidad_itemProductoDb.get(indice);
            }
            indice++;
        }
        logger.info("saliendo al existeProductoEnItemProducto");

        if (bandera) {
            logger.info("Entramos en el if");
            // Si esta presente debemos subirle la cantidad del productoCantidad y bajarle la cantidad del producto
            productoCantidad_ItemProducto.setCantidad(productoCantidad_ItemProducto.getCantidad() + cantidad);
            productoCantidad_ItemProducto.getProducto().setCantidad(productoCantidad_ItemProducto.getProducto().getCantidad() - cantidad);
            repository.save(itemProductoDb);
        } else {
            logger.info("Entramos en el else");
            /* Si no existe, debemos agregar ese producto a la lista. */
            ProductoCantidad nuevoProductoCantidad = new ProductoCantidad();
            int cantidadProducto = producto.getCantidad();
            nuevoProductoCantidad.setProducto(producto);
            nuevoProductoCantidad.setCantidad(cantidad);
            nuevoProductoCantidad.setItemProducto(itemProductoDb);

            nuevoProductoCantidad.getProducto().setCantidad(cantidadProducto - cantidad);
            listaProductoCantidad_itemProductoDb.add(nuevoProductoCantidad);
            repository.save(itemProductoDb);
        }

    }

    @Transactional
    public void eliminarProducto(Long itemProductoId, Long productoId) {
        ItemProducto itemProductoDb = findById(itemProductoId);
        Producto producto = repositoryProducto.findById(productoId);
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