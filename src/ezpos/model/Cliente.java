package ezpos.model;

public class Cliente {
    private int id;
    private String cpf_cnpj;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;

    public Cliente(int id, String cpf_cnpj, String nome, String endereco, String telefone, String email) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public Cliente(String cpf_cnpj, String nome, String endereco, String telefone, String email) {
        this.cpf_cnpj = cpf_cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpf_cnpj;
    }

    public void setCpfCnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getNome() + " - " + this.getCpfCnpj();
    }
}
