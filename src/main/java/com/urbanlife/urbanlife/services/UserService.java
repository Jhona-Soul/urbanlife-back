package com.urbanlife.urbanlife.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanlife.urbanlife.models.response.UsuarioResponse;
import com.urbanlife.urbanlife.models.usuario.Usuario;
import com.urbanlife.urbanlife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserService.class);

    public UsuarioResponse obtenerUsurio(Integer id) {
        Optional<Usuario> userBBDD = userRepository.findById(id);
        UsuarioResponse user = objectMapper.convertValue(userBBDD, UsuarioResponse.class);
        return null;
    }

}
