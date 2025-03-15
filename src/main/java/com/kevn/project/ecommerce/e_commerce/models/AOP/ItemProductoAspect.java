package com.kevn.project.ecommerce.e_commerce.models.AOP;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kevn.project.ecommerce.e_commerce.models.ItemProducto;
import com.kevn.project.ecommerce.e_commerce.models.Usuario;
import com.kevn.project.ecommerce.e_commerce.services.ItemProductoService;

@Aspect
@Component
public class ItemProductoAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ItemProductoService serviceProducto;

    @AfterReturning(
        pointcut = "execution(* com.kevn.project.ecommerce.e_commerce.services.UsuarioService.save(..))",
        returning = "usuario"
    )
    public void loggerAfterReturning(JoinPoint join,Usuario usuario){
        logger.info("Usuario creado: " + usuario);
        if(usuario != null){
            ItemProducto it = new ItemProducto();
            it.setUsuario(usuario);
            ItemProducto savItemProducto = serviceProducto.save(it);
            logger.info("ItemProducto creado y asignado al Usuario: {}", savItemProducto.getId());
        }
    }

}
