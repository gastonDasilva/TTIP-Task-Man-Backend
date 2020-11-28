package ar.unq.edu.TaskMan.Webservice;

import ar.unq.edu.TaskMan.Model.Buscador;
import ar.unq.edu.TaskMan.Model.Login;
import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Tarea.Tarea;
import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
@CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT})
public class UsuarioController {

    @Autowired
    private UsuarioService userService ;
    @Autowired
    private ProyectoService proyectoService;


    @RequestMapping(value = "/usuarios", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Usuario>> getUsers() {
        return new ResponseEntity<>(this.userService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/usuario", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario user) {
        try{
            this.userService.save(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "el usuario o email ya esta siendo utilizado", e);
        }


    }

    @RequestMapping(value = "/usuario/actualizarUsuario/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Usuario updateUsuarioData(@RequestBody Usuario usuario, @PathVariable Long id) {
        /*Actualizo los datos del usuario.*/
        try{
        return  userService.update(usuario);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.IM_USED, "el usuario o email ya esta siendo utilizado");
        }
    }

    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Void> updateUser(@PathVariable("id") long id,@RequestBody Usuario usuario){
        try{
            System.out.println(usuario.getNombre());
            userService.update(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.IM_USED, "el usuario o email ya esta siendo utilizado");
        }
    }

    @RequestMapping(value = "/usuario/buscar/{usernameORemail}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Usuario> getUserByUsernameoEmail(@PathVariable("usernameORemail") String user) {
        Optional<Usuario> usuarioOptional = this.userService.getByUsuarioOEmail(user);
        if(usuarioOptional.isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        return new ResponseEntity<>(usuarioOptional.get(),HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Usuario> loginUsuario(@RequestBody Login body){
        Optional<Usuario> usuarioOptional = this.userService.getByUsuarioOEmail(body.getUserOrEmail());
        if(usuarioOptional.isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        if(usuarioOptional.get().getPassword().equals(body.getPassword())){
            return new ResponseEntity<>(usuarioOptional.get(), HttpStatus.OK);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña incorrecta");
        }

    }
    @RequestMapping(value = "/buscar/{id}/{aBuscar}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Buscador> search(@PathVariable("id") Long id, @PathVariable("aBuscar") String aBuscar){
        Buscador buscador = new Buscador();
        List<Proyecto> proyectos = proyectoService.getAll();
        Optional<Usuario> usuarioOptional = userService.getById(id);
        if(usuarioOptional.isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        List<Proyecto> proyectosUsuarios = proyectos.stream()
                .filter(proyecto -> proyecto.incluyeUsuario(usuarioOptional.get()))
                .collect(Collectors.toCollection(()-> new ArrayList<>()));
        List<Tarea> tareas = proyectosUsuarios.stream()
                .map(proyecto -> proyecto.getTareas())
                .flatMap(tareas1 -> tareas1.stream())
                .collect(Collectors.toCollection(()-> new ArrayList<>()));
//                .reduce((tareas1, tareas2) -> tareas1.addAll(tareas2));
        buscador.setProyectos(proyectosUsuarios.stream()
                .filter(proyecto -> {
                    System.out.println(proyecto.getNombre());
                    System.out.println(proyecto.getNombre().contains(aBuscar));
                    return proyecto.getNombre().toUpperCase().contains(aBuscar.toUpperCase());
                })
                .collect(Collectors.toCollection(()-> new ArrayList<>()))
        );
        buscador.setTareas(tareas.stream()
                .filter(tarea -> {
                    System.out.println(tarea.getTitulo());
                    System.out.println(tarea.getTitulo().toUpperCase().contains(aBuscar.toUpperCase()));
                    return tarea.getTitulo().toUpperCase().contains(aBuscar.toUpperCase());
                })
                .collect(Collectors.toCollection(()-> new ArrayList<>()))
        );
        return new ResponseEntity<>(buscador, HttpStatus.OK);
    }
}

