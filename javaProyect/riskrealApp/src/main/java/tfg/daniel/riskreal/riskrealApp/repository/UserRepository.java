package tfg.daniel.riskreal.riskrealApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tfg.daniel.riskreal.riskrealApp.model.User;

/** 
 * Interface UserRepository.
 * 
 * Default userRepository for manage to the users information in the database.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
