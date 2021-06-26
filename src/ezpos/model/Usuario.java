package ezpos.model;

public class Usuario {
    private int id;
    private String usuario;
    private String nome;
    private String email;
    private String senha;
    private int admin;

    public Usuario(int id, String usuario, String nome, String email, String senha, int admin) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.admin = admin;
    }

    public Usuario(String usuario, String nome, String email, String senha, int admin) {
        this.usuario = usuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
