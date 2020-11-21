package ar.unq.edu.TaskMan.Controllers;

import ar.unq.edu.TaskMan.Model.Login;
import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.UsuarioService;
import ar.unq.edu.TaskMan.Webservice.UsuarioController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private ProyectoService proyectoService;
    @Test
    public void testObtenerTodosLosUsuarios() throws Exception {
        Usuario usuario = new Usuario(
                "leadiaz",
                "Leandro",
                "Diaz",
                "diazleandro4@gmail.com",
                "123456");
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioService.getAll()).thenReturn(usuarios);

        mvc.perform(get("/usuarios").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
    @Test
    public void testGetUserByUsernameoEmail() throws Exception {
        Optional<Usuario> usuario = Optional.of(new Usuario(
                "leadiaz",
                "Leandro",
                "Diaz",
                "diazleandro4@gmail.com",
                "123456"));
        Login login = new Login("leadiaz", "123456");

        when(usuarioService.getByUsuarioOEmail("leadiaz")).thenReturn(usuario);

        mvc.perform(get("/usuario/buscar/{usernameORemail}","leadiaz").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void testlogin() throws Exception {
        Optional<Usuario> usuario = Optional.of(new Usuario(
                "leadiaz",
                "Leandro",
                "Diaz",
                "diazleandro4@gmail.com",
                "123456"));
        Login login = new Login("leadiaz", "123456");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(login);

        when(usuarioService.getByUsuarioOEmail("leadiaz")).thenReturn(usuario);
        mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
    }
    /*
    * test de error de peticion
    * */
    @Test
    public void testloginPasswordIncorrecto() throws Exception {
        Optional<Usuario> usuario = Optional.of(new Usuario(
                "leadiaz",
                "Leandro",
                "Diaz",
                "diazleandro4@gmail.com",
                "1234561"));
        Login login = new Login("leadiaz", "123456");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(login);

        when(usuarioService.getByUsuarioOEmail("leadiaz")).thenReturn(usuario);
        mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testloginUsuarioIncorrecto() throws Exception {
        Optional<Usuario> usuario = Optional.of(new Usuario(
                "leadiaz",
                "Leandro",
                "Diaz",
                "diazleandro4@gmail.com",
                "123456"));
        Login login = new Login("leadiaz94", "123456");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(login);

        when(usuarioService.getByUsuarioOEmail("leadiaz")).thenReturn(usuario);
        mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isNotFound());
    }
    /*@Test
    public void testNoSePuedeActualizarUsuario() throws Exception {
        Optional<Usuario> usuario = Optional.of(new Usuario(
                "leadiaz",
                "Leandro",
                "Diaz",
                "diazleandro4@gmail.com",
                "123456"));
//        Login login = new Login("leadiaz94", "123456");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(usuario.get());

        when(usuarioService.update(usuario.get())).thenThrow(new ResponseStatusException(HttpStatus.IM_USED));
        mvc.perform(post("/usuario/actualizarUsuario/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isImUsed());
    }*/
}
