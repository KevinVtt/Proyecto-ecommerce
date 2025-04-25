package com.kevn.project.ecommerce.e_commerce.models.AOP;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kevn.project.ecommerce.e_commerce.models.Carrito;
import com.kevn.project.ecommerce.e_commerce.models.Usuario;
import com.kevn.project.ecommerce.e_commerce.services.CarritoService;

@Aspect
@Component
public class CarritoAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CarritoService service;

    @AfterReturning(
        pointcut = "execution(* com.kevn.project.ecommerce.e_commerce.services.UsuarioService.save(..))",
        returning = "usuario"
    )
    public void loggerAfterReturning(JoinPoint join,Usuario usuario){
        logger.info("Usuario creado: " + usuario);
        if(usuario != null){
            Carrito carrito = new Carrito();
            carrito.setUsuario(usuario);
            Carrito saveCarrito = service.save(carrito);
            logger.info("Carrito creado y asignado al Usuario: {}", saveCarrito.getId());
        }
    }

}
