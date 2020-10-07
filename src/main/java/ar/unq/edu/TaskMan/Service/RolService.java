package ar.unq.edu.TaskMan.Service;

import ar.unq.edu.TaskMan.Services.Rol;
import ar.unq.edu.TaskMan.Repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;
    @Transactional
    public List<Rol> getAll(){
        return (List<Rol>) rolRepository.findAll();
    }
    @Transactional
    public long save(Rol rolNuevo){

        return (rolRepository.save(rolNuevo)).getId();
    }

    @Transactional
    public void updateRol(Rol rol) {
        rolRepository.save(rol);

    }
    @Transactional
    public Optional<Rol> getById(Long id) {
        return rolRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        rolRepository.delete(this.getById(id).get());
    }



}
