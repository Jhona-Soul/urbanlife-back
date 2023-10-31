package com.urbanlife.urbanlife.controllers;

import com.urbanlife.urbanlife.models.Reservas;
import com.urbanlife.urbanlife.models.request.ReservaRequest;
import com.urbanlife.urbanlife.models.request.SubCaraceristicaRequest;
import com.urbanlife.urbanlife.models.response.UsuarioResponse;
import com.urbanlife.urbanlife.services.ReservasService;
import com.urbanlife.urbanlife.services.UsuarioService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ReservasService reservaService;

    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> obtenerUsurio(@PathVariable Integer id) {
        System.out.println("Reeeeeee" + usuarioService.obtenerUsuario(id).getApellido());
        return ResponseEntity.ok(usuarioService.obtenerUsuario(id));
    }
    @GetMapping("/listausuarios-all")
    public ResponseEntity<Collection<UsuarioResponse>> listausuarios() {
        return ResponseEntity.ok(usuarioService.listaUsuariosRegistrados());
    }
    @PostMapping("/reservarProducto")
    public ResponseEntity<?> reservarProducto(@RequestBody ReservaRequest request) {
        return ResponseEntity.ok(usuarioService.guardarReserva(request));
    }
    @GetMapping("/listareservas-all")
    public ResponseEntity<Collection<Reservas>> listarProductos() {
        return ResponseEntity.ok(reservaService.listaDeReservas2());
    }
    @PostMapping("/registrar-favorito/{idUsuario}/{idProducto}")
    public ResponseEntity<?> removeOrAddFromFavorito(@PathVariable Integer idUsuario,
                                                     @PathVariable Integer idProducto) {
        try {
            return ResponseEntity.ok(usuarioService.removeOrAddFromFavorito(idProducto, idUsuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el producto de favoritos");
        }
    }
    @GetMapping("/listafavoritos-all/{id}")
    public ResponseEntity<?> listaFavoritos(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.listaFavoritosFromUser(id));
    }
    @PostMapping("/registrar-caractaristica-producto")
    public void registrarCaracteristaProducto(@RequestBody Collection<SubCaraceristicaRequest> requests) {
        try {
            usuarioService.regitrarSubCategorias(requests);
        } catch (Exception e) {
            throw new RuntimeException("failed to upload sub category", e);
        }
    }
}
