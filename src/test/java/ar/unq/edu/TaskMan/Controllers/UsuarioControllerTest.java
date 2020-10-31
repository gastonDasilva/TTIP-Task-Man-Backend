package ar.unq.edu.TaskMan.Controllers;

import ar.unq.edu.TaskMan.Model.Login;
import ar.unq.edu.TaskMan.Model.Usuario;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
}
