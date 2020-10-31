package ar.unq.edu.TaskMan.Controllers;

import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Rol;
import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.RolService;
import ar.unq.edu.TaskMan.Service.TareaService;
import ar.unq.edu.TaskMan.Service.UsuarioService;
import ar.unq.edu.TaskMan.Webservice.ProyectoController;
import ar.unq.edu.TaskMan.Webservice.UsuarioController;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ProyectoController.class)
public class ProyectoControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProyectoService proyectoService;
    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private TareaService tareaService;
    @MockBean
    private RolService rolService;
    @Test
    public void testObtenerTodosLosProyectos() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Test");
        List<Proyecto> proyectos = Arrays.asList(proyecto);
        when(proyectoService.getAll()).thenReturn(proyectos);

        mvc.perform(get("/proyectos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetProyectosDeUsuario() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Test");
        Proyecto proyecto2 = new Proyecto();
        proyecto2.setNombre("Test 2");
        Proyecto proyecto3 = new Proyecto();
        proyecto3.setNombre("Test 3");

        Usuario usuario =new Usuario(
                "leadiaz",
                "Leandro",
                "Diaz",
                "diazleandro4@gmail.com",
                "123456");
        Rol rol = new Rol("Develop",usuario);
        proyecto.addRol(rol);
        proyecto2.addRol(rol);

        List<Proyecto> proyectos = Arrays.asList(proyecto,proyecto2,proyecto3);

        when(proyectoService.getAll()).thenReturn(proyectos);
        when(usuarioService.getById(Long.valueOf(1))).thenReturn(Optional.of(usuario));

        mvc.perform(get("/proyectos/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(2)));
    }
    @Test
    public void testGetProyecto() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Test");
        when(proyectoService.getById(Long.valueOf(1))).thenReturn(Optional.of(proyecto));

        mvc.perform(get("/proyecto/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }@Test
    public void testEliminarProyecto() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Test");
        when(proyectoService.getById(Long.valueOf(1))).thenReturn(Optional.of(proyecto));

        mvc.perform(delete("/proyecto/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
