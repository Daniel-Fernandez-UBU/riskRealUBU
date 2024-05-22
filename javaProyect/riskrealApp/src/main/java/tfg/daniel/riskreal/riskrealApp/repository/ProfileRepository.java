package tfg.daniel.riskreal.riskrealApp.repository;

import org.springframework.data.repository.CrudRepository;

import tfg.daniel.riskreal.riskrealApp.model.Profile;

public interface ProfileRepository extends CrudRepository<Profile, String> {
    // Aquí puedes agregar métodos de consulta adicionales si es necesario

}
