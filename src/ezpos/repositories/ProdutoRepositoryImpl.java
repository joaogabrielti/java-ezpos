package ezpos.repositories;

import ezpos.daos.interfaces.ProdutoDAO;
import ezpos.model.Produto;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    private final ObservableList<Produto> produtos;
    private final ProdutoDAO produtoDAO;

    public ProdutoRepositoryImpl(ProdutoDAO produtoDAO) {
        this.produtos = FXCollections.observableArrayList();
        this.produtoDAO = produtoDAO;
    }

    @Override
    public ObservableList<Produto> listar() throws SQLException {
        this.produtos.clear();
        this.produtos.addAll(this.produtoDAO.listar());
        return FXCollections.unmodifiableObservableList(this.produtos);
    }

    @Override
    public Produto buscar(int id) throws SQLException {
        return this.produtoDAO.buscar(id);
    }

    @Override
    public boolean inserir(Produto produto) throws SQLException {
        return this.produtoDAO.inserir(produto);
    }

    @Override
    public boolean editar(Produto produto) throws SQLException {
        return this.produtoDAO.editar(produto);
    }

    @Override
    public boolean excluir(Produto produto) throws SQLException {
        return this.produtoDAO.excluir(produto);
    }
}
