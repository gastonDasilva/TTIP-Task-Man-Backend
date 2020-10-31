package ar.unq.edu.TaskMan.Model.Tarea;

import ar.unq.edu.TaskMan.Model.Estado;
import ar.unq.edu.TaskMan.Model.Prioridad;
import ar.unq.edu.TaskMan.Model.Usuario;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;

import javax.persistence.*;
import javax.persistence.Id;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tarea_Type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = TareaSimple.class, name = "TareaSimple"),
                @JsonSubTypes.Type(value = TareaCompleja.class, name = "TareaCompleja")})
public abstract class Tarea implements Comparable<Tarea>{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String titulo;
    private String descripcion;

    @OneToOne(cascade = CascadeType.ALL)
    private Usuario asignado;
    private Estado estado;


    public Tarea() {
    }

    public Tarea(String titulo, String descripcion) {

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = Estado.CREADA;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getAsignado() {
        return asignado;
    }

    public void setAsignado(Usuario asignado) {
        this.asignado = asignado;
        this.estado = Estado.EN_PROCESO;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public abstract boolean isCompleja();

}
