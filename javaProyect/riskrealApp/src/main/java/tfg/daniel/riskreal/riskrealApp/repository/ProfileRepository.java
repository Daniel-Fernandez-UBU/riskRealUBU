package tfg.daniel.riskreal.riskrealApp.repository;

import org.springframework.data.repository.CrudRepository;

import tfg.daniel.riskreal.riskrealApp.model.User;

public interface ProfileRepository extends CrudRepository<User, String> {
    // Aquí puedes agregar métodos de consulta adicionales si es necesario

}
