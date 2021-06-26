package ezpos.auth;

import ezpos.model.Usuario;

public abstract class Auth {
    private static Usuario usuarioAutenticado;

    public static Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public static void setUsuarioAutenticado(Usuario usuarioAutenticado) {
        Auth.usuarioAutenticado = usuarioAutenticado;
    }
}
