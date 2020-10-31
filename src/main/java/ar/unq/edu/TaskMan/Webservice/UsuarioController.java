package ar.unq.edu.TaskMan.Webservice;

import ar.unq.edu.TaskMan.Model.Login;
import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
}

