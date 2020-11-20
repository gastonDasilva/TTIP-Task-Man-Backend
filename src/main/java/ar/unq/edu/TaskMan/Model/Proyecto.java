package ar.unq.edu.TaskMan.Model;

import ar.unq.edu.TaskMan.Model.Tarea.Tarea;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Entity
public class Proyecto {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rols")
    private List<Rol> rols = new ArrayList<Rol>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<Tarea> tareas = new ArrayList<>();

    public Proyecto() {}
    public Proyecto(String nombre, Rol rol) {
        this.nombre  = nombre;
        this.rols.add(rol);
    }

    public List<Rol> getRols() {
        return rols;
    }
    public void setRols(List<Rol> rols) {
        this.rols = rols;
    }
    public void addRol(Rol rol){
        this.rols.add(rol);
    }

    public void deleteROl(Rol rol){
        /*limpio al  usuario de la lista de roles/miembros del proyecto.*/
        List<Rol> rols =  this.rols
                .stream()
                .filter(c -> !c.getId().equals(rol.getId()))
                .collect(Collectors.toList());
        setRols(rols);
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

    public List<Tarea> getTareas() {
        //verificar porque hago esta negrada
        List<Tarea> sortTareas = this.tareas;
        sortTareas.stream().forEach(Tarea::verificarEstado);
        Collections.sort(sortTareas);
        return sortTareas;
    }
    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }
    public void addTarea(Tarea tarea) {
        this.tareas.add(tarea);
    }

    public void eliminarTarea(Tarea tarea) {
        tareas.removeIf(tarea1 -> tarea1.getId().equals(tarea.getId()));
    }

    public boolean incluyeUsuario(Usuario usuario) {
        return this.getMiembros().contains(usuario);
    }

    public Set<Usuario> getMiembros(){
        return this.rols.stream().map(Rol::getUsuarioAsignado).collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    public  boolean verificarMiembroAAgregar(Usuario usuarioAAgregar){
        /*Verifico si el usuario que voy a agregar como miembro ya esta incluido en el SET de miembros.*/
        boolean res = false;
        Iterator<Usuario> it = getMiembros().iterator();
        while(it.hasNext()){
            Usuario user = (Usuario) it.next();
            if(user.getId().equals(usuarioAAgregar.getId())) {
                res =true;
                break;
            }
        }
        return res;
    }
}
