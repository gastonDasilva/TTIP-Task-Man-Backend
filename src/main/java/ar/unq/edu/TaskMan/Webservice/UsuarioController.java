package ar.unq.edu.TaskMan.Webservice;

import ar.unq.edu.TaskMan.Model.Login;
import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
@CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT})
public class UsuarioController {

    @Autowired
    private UsuarioService userService ;


    @RequestMapping(value = "/usuarios", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Usuario>> getUsers() {
        return new ResponseEntity<>(this.userService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/usuario", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Void> createUser(@RequestBody Usuario user) {
        try{
            this.userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.IM_USED, "el usuario o email ya esta siendo utilizado");
        }


    }
    /*@RequestMapping(value = "/usuario/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Void> updateUser(@PathVariable("id") long id,@RequestBody Usuario usuario){
        System.out.println(usuario.getNombre());
        Usuario user= userService.getById(id);
        System.out.println("peticion de put, " + user);
        if(user == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }else {
            System.out.println(usuario.getUsuario());
            System.out.println(usuario.getProyecto());
            user.setUsuario(usuario.getUsuario());
            user.setNombre(usuario.getNombre());
            user.setApellido(usuario.getApellido());
            user.setEmail(usuario.getEmail());
            user.setPassword(usuario.getPassword());
            user.save(usuario.getProyecto());
            this.userService.update(user);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }*/

    /*@RequestMapping(value = "/usuario/buscar", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Usuario>> searchUser(@RequestBody String user) {

        List<Usuario> results = this.userService.search(user);
        System.out.println(results);

        return new ResponseEntity<List<Usuario>>(results,HttpStatus.OK);

    }*/



//	@RequestMapping(value = "/usuario/buscar/{val}", method = RequestMethod.GET, produces = "application/json")
//	public ResponseEntity<List<Usuario>> searchUserName(@PathVariable("val") String user) {
//
//			List<Usuario> results = this.userService.search(user);
//			System.out.println(results);
//
//			return new ResponseEntity<List<Usuario>>(results,HttpStatus.OK);
//
//	}

    @RequestMapping(value = "/usuario/buscar/{username}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Usuario> getUserByUsername(@PathVariable("username") String user) {
        Optional<Usuario> usuarioOptional = this.userService.getByUsername(user);
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
}

