package ar.unq.edu.TaskMan.Model;

import javax.persistence.*;

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Usuario usuarioAsignado = null;
    private String tipoRol;
    public Rol(){
    }
    public Rol(String rol){
        tipoRol = rol;
    }
    public Rol(String rol, Usuario usuario){
        this.tipoRol = rol;
        this.usuarioAsignado = usuario;
    }
    public Usuario getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(Usuario usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
    }
    public Boolean estaAsignado(){
        return usuarioAsignado != null;
    }

    public Long getId() {
        return this.id;
    }
}
