package ezpos.repositories;

import ezpos.Main;
import ezpos.model.Produto;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    private final ObservableList<Produto> produtos;

    public ProdutoRepositoryImpl() {
        this.produtos = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Produto> listar() throws SQLException {
        this.produtos.clear();
        this.produtos.addAll(Main.getProdutoDAO().listar());
        return FXCollections.unmodifiableObservableList(this.produtos);
    }

    @Override
    public Produto buscar(int id) throws SQLException {
        return Main.getProdutoDAO().buscar(id);
    }

    @Override
    public boolean inserir(Produto produto) throws SQLException {
        return Main.getProdutoDAO().inserir(produto);
    }

    @Override
    public boolean editar(Produto produto) throws SQLException {
        return Main.getProdutoDAO().editar(produto);
    }

    @Override
    public boolean excluir(Produto produto) throws SQLException {
        return Main.getProdutoDAO().excluir(produto);
    }
}
