package com.tecsup.services;

import com.tecsup.client.ClienteClient;
import com.tecsup.client.ProductoClient;
import com.tecsup.model.EstadoPedido;
import com.tecsup.model.Pedido;
import com.tecsup.repository.PedidoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private ProductoClient productoClient;

    public List<Pedido> listar() {
        List<Pedido> pedidos = repo.findAll();
        pedidos.forEach(this::completar);
        return pedidos;
    }

    public Pedido obtener(Long id) {
        Pedido pedido = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        return completar(pedido);
    }

    public Pedido guardar(Pedido p) {
        verificarExistencia(p);
        p.setId(null);
        p.setEstado(EstadoPedido.PENDIENTE);
        Pedido guardado = repo.save(p);
        return completar(guardado);
    }

    public Pedido cambiarEstado(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        return completar(repo.save(pedido));
    }

    // Los pedidos no se eliminan: se anulan (ver cambiarEstado)

    private void verificarExistencia(Pedido p) {
        try {
            clienteClient.obtenerCliente(p.getClienteId());
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no existe");
        }
        try {
            productoClient.obtenerProducto(p.getProductoId());
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto no existe");
        }
    }

    private Pedido completar(Pedido p) {
        p.setCliente(clienteClient.obtenerCliente(p.getClienteId()));
        p.setProducto(productoClient.obtenerProducto(p.getProductoId()));
        return p;
    }
}
