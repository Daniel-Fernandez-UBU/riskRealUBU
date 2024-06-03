package tfg.daniel.riskreal.riskrealApp.repository;

import org.springframework.data.repository.CrudRepository;

import tfg.daniel.riskreal.riskrealApp.model.Profile;

/**
 * 
 * Interface ProfileRepository.
 * 
 * Default profileRepository interface for manage to the profile information from the database.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
public interface ProfileRepository extends CrudRepository<Profile, String> {


}
