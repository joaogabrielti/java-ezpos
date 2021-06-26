package ezpos.repositories;

import ezpos.model.Usuario;
import ezpos.repositories.interfaces.UsuarioRepository;
import java.sql.SQLException;

public class UsuarioRepositoryImpl implements UsuarioRepository {
    @Override
    public Usuario autenticar(String usuario, String senha) throws SQLException {
        return null;
    }
}
