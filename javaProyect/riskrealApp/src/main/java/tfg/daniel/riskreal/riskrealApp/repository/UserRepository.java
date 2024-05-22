package tfg.daniel.riskreal.riskrealApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tfg.daniel.riskreal.riskrealApp.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    // Aquí puedes agregar métodos de consulta adicionales si es necesario
}
