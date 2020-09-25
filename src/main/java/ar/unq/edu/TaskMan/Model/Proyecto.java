package ar.unq.edu.TaskMan.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
public class Proyecto {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nombre;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Usuario> miembros = new ArrayList<Usuario>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Tarea> tareas = new HashSet<Tarea>();

    public Proyecto() {}
    public Proyecto(String nombre, Usuario usuario) {
        this.nombre  = nombre;
        this.miembros.add(usuario);
    }

    public List<Usuario> getMiembros() {
        return miembros;
    }
    public void setMiembros(List<Usuario> miembros) {
        this.miembros = miembros;
    }
    public void addMiembro(Usuario usuario){
        this.miembros.add(usuario);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Tarea> getTareas() {
        return tareas;
    }
    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }
    public void addTarea(Tarea tarea) {this.tareas.add(tarea);}

    public void eliminarTarea(Tarea tarea) {
        tareas.removeIf(tarea1 -> tarea1.getId().equals(tarea.getId()));
    }

    public boolean incluyeUsuario(Usuario usuario) {
        return this.miembros.contains(usuario);
    }
}
