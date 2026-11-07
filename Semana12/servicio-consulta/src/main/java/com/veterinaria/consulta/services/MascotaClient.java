package com.veterinaria.consulta.services;

import com.veterinaria.consulta.exception.ReferenciaInvalidaException;
import com.veterinaria.consulta.exception.ServicioNoDisponibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente HTTP mínimo hacia el servicio de mascota.
 * Solo verifica que el id exista; NO comparte tablas ni base de datos.
 * Se puede desactivar con servicios.mascota.validar=false.
 */
@Component
public class MascotaClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final boolean validar;

    public MascotaClient(RestTemplate restTemplate,
                            @Value("${servicios.mascota.url}") String baseUrl,
                            @Value("${servicios.mascota.validar:true}") boolean validar) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.validar = validar;
    }

    public void verificarExiste(Long id) {
        if (!validar) {
            return;
        }
        try {
            restTemplate.getForEntity(baseUrl + "/api/mascotas/{id}", Void.class, id);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ReferenciaInvalidaException("No existe una mascota con id " + id);
        } catch (ResourceAccessException e) {
            throw new ServicioNoDisponibleException("El servicio de mascota no está disponible en este momento");
        } catch (RestClientException e) {
            throw new ServicioNoDisponibleException("No se pudo verificar el mascota en el servicio remoto");
        }
    }
}
