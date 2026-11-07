package com.veterinaria.mascota.services;

import com.veterinaria.mascota.exception.ReferenciaInvalidaException;
import com.veterinaria.mascota.exception.ServicioNoDisponibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente HTTP mínimo hacia el servicio de propietario.
 * Solo verifica que el id exista; NO comparte tablas ni base de datos.
 * Se puede desactivar con servicios.propietario.validar=false.
 */
@Component
public class PropietarioClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final boolean validar;

    public PropietarioClient(RestTemplate restTemplate,
                            @Value("${servicios.propietario.url}") String baseUrl,
                            @Value("${servicios.propietario.validar:true}") boolean validar) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.validar = validar;
    }

    public void verificarExiste(Long id) {
        if (!validar) {
            return;
        }
        try {
            restTemplate.getForEntity(baseUrl + "/api/propietarios/{id}", Void.class, id);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ReferenciaInvalidaException("No existe un propietario con id " + id);
        } catch (ResourceAccessException e) {
            throw new ServicioNoDisponibleException("El servicio de propietario no está disponible en este momento");
        } catch (RestClientException e) {
            throw new ServicioNoDisponibleException("No se pudo verificar el propietario en el servicio remoto");
        }
    }
}
