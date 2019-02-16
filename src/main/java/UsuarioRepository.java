
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.groupay.api.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	List<Usuario> findByLastName(@Param("name") String name);

}