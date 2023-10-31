package com.urbanlife.urbanlife.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanlife.urbanlife.exception.ResourceNotFoundException;
import com.urbanlife.urbanlife.models.*;
import com.urbanlife.urbanlife.models.Dto.ProductosAletoriosDTO;
import com.urbanlife.urbanlife.models.request.ReservaRequest;
import com.urbanlife.urbanlife.models.request.SubCaraceristicaRequest;
import com.urbanlife.urbanlife.models.response.UsuarioResponse;
import com.urbanlife.urbanlife.models.usuario.RolUser;
import com.urbanlife.urbanlife.models.usuario.Usuario;
import com.urbanlife.urbanlife.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UserRepository userRepository;
    private final ReservaRepository reservaRepository;
    private final ProductoRepository productoRepository;
    private final FavoritosRepository favoritosRepository;
    private final ImagenService imagenService;
    private final CaracteristicasRepository caracteristicasRepository;
    private final SubCaracateristicaRepository subCaracateristicaRepository;

    @Autowired
    ObjectMapper objectMapper;

    private static final Logger logger = Logger.getLogger(ProductoService.class);
    public boolean existsCustomerById(Integer id) {
        Optional<Usuario> usuarioBBDD = userRepository.findById(id);
        return usuarioBBDD.isPresent();
    }
    private void checkIfProductoExistsOrThrow(Integer id) {
        if (!existsCustomerById(id)) {
            throw new ResourceNotFoundException(
                    "El Cliente con el id [%s] NO EXISTE".formatted(id)
            );
        }
    }
    public UsuarioResponse obtenerUsuario(Integer id) {
        checkIfProductoExistsOrThrow(id);
        Optional<Usuario> usuarioBBDD = userRepository.findById(id);
        return convertUser(usuarioBBDD.get());
    }
    public Usuario getUsuariById(Integer id) {
        return userRepository.findById(id).get();
    }

    public Collection<UsuarioResponse> listaUsuariosRegistrados() {
        Iterable<Usuario> listaUsuariosBBDD = userRepository.findAll();
        Set<UsuarioResponse> listaUsuario = new HashSet<UsuarioResponse>();
        for (Usuario user : listaUsuariosBBDD) {
            if (user.getRole().equals(RolUser.CLIENTE)) {
                listaUsuario.add(convertUser(user));
            }
        }
        logger.info("Lista Usuarios: Proceso Finalizado con Exito!");
        return listaUsuario;
    }
    public String guardarReserva(ReservaRequest request) {
        checkIfProductoExistsOrThrow(request.getIdUsuario());
        var reserva = Reservas.builder()
                        .fechaReserva(LocalDate.now())
                .fechaInicioAlquiler(request.getFechaIniciAlquiler())
                .fechaFinAlquiler(request.getFechaFinAlquiler())
                .estadoReserva("Activo")
                .productos(productoRepository.findById(request.getIdProducto()).get())
                .usuario(userRepository.findById(request.getIdUsuario()).get())
                .build();
        reservaRepository.save(reserva);
        return "Registro exitoso:";
    }

    private UsuarioResponse convertUser(Usuario user) {
        return UsuarioResponse.builder()
                .email(user.getEmail())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .telefono(user.getTelefono())
                .urlImagen(user.getProfileImageId())
                .build();
    }
    public Collection<Reservas> listaDeReservas() {
        return reservaRepository.findAll();
    }

    private void guardarFavorito(Integer idProducto, Integer idUsuario) {
        userRepository.registrarFavoritoUsuario(idUsuario, idProducto);
    }
    private void removeFromFavorito(Usuario usuario, Productos productos) {
        Favoritos favorito = favoritosRepository.findByUsuarioAndProductos(usuario, productos);
        favoritosRepository.deleteById(favorito.getIdFavoritos());
    }
    public String removeOrAddFromFavorito(Integer idProducto, Integer idUsuario) {
        String resultado = "";
        Usuario usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        Productos productos = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        if (favoritosRepository.existsByUsuarioAndProductos(usuario, productos)) {
            removeFromFavorito(usuario, productos);
            resultado = "Elimaste el producto con el ID %s de tu lista de favoritos".formatted(idUsuario);
        } else{
            guardarFavorito(idProducto, idUsuario);
            resultado = "Registraste un nuevo favorito";
        }
        return resultado;
    }
    public Collection<ProductosAletoriosDTO> listaFavoritosFromUser(Integer idUser) {
        checkIfProductoExistsOrThrow(idUser);
        Iterable<Productos> listaProductos = productoRepository.listaFavoritos(idUser);
        Set<ProductosAletoriosDTO> listaProductosDTO = new HashSet<ProductosAletoriosDTO>();
        for (Productos productos : listaProductos) {
            listaProductosDTO.add(objectMapper.convertValue(productos, ProductosAletoriosDTO.class));
        }
        logger.info("Lista Productos Favoritos: Proceso Finalizado con Exito!");
        return listaProductosDTO
                .stream()
                .peek(productoDTO -> productoDTO.setImagenes(imagenService.listarImagenesPorProducto(productoDTO.getIdProducto())))
                .collect(Collectors.toList());
    }
    public void registrarCaracteristica(Caracteristicas caracteristicas) {
        caracteristicasRepository.save(caracteristicas);
    }
    public Collection<Caracteristicas> listaCaracteristicas() {
        return caracteristicasRepository.findAll();
    }
    public void regitrarSubCategorias(Collection<SubCaraceristicaRequest> requests){

        for (SubCaraceristicaRequest subCaract : requests) {
            for (int i = 0; i < subCaract.getTipo().length; i++) {
                System.out.println(subCaract.getTipo()[i]);
                Iterable <SubCaracteristicas> listaSubcategorias = subCaracateristicaRepository.findAll();
                var caracteristica = SubCaracteristicas.builder()
                        .tipo(subCaract.getTipo()[i])
                        .caracteristica(caracteristicasRepository.findById(subCaract.getIdCategoria()).get())
                        .build();
                subCaracateristicaRepository.save(caracteristica);
            }
        }

    }

}

















