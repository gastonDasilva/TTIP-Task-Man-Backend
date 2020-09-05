package ar.unq.edu.TaskMan.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;

@Entity
public class Proyecto {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    //private Usuario creador;
    /*private List<Usuario> miembros = new ArrayList<Usuario>();
    private Set<Tarea> tareas = new HashSet<Tarea>();
*/
    public Proyecto() {}
    public Proyecto(String nombre, Usuario usuario) {
        this.nombre  = nombre;
        //this.creador = usuario;
        //this.miembros.add(usuario);

    }

   /* public List<Usuario> getMiembros() {
        return miembros;
    }
    public void setMiembros(List<Usuario> miembros) {
        this.miembros = miembros;
    }*/
    /*public Usuario getCreador() {
        return creador;
    }
    public void setCreador(Usuario creador) {
        this.creador = creador;
    }*/
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
    /*public void addMiembro(Usuario user) {this.miembros.add(user);}
    public Set<Tarea> getTareas() {
        return tareas;
    }
    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }
    public void addTarea(Tarea tarea) {this.tareas.add(tarea);}
    public void eliminarTarea(Tarea tarea) {
        System.out.println(tareas.size());
        Iterator<Tarea> it = tareas.iterator();
        while (it.hasNext()){
            if (it.next().getId() == tarea.getId()){
                it.remove();
            }
        }
        System.out.println(tareas.size());
    }*/
}
