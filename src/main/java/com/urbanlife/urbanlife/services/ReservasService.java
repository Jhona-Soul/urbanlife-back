package com.urbanlife.urbanlife.services;

import com.urbanlife.urbanlife.models.Reservas;
import com.urbanlife.urbanlife.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ReservasService {
    private final ReservaRepository reservaRepository;
    public Collection<Reservas> listaDeReservas2() {
        return reservaRepository.findAll();
    }

}
