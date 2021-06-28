package ezpos;

import ezpos.auth.Auth;
import ezpos.daos.*;
import ezpos.daos.interfaces.*;
import ezpos.gui.control.JanelaPrincipal;
import ezpos.repositories.*;
import ezpos.repositories.interfaces.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

// VM Options: --module-path "C:\Java\OpenJFX\lib" --add-modules javafx.controls,javafx.fxml
public class Main extends Application {
    public static final String JANELA_PRINCIPAL = "/fxml/janela_principal.fxml";
    public static final String JANELA_CLIENTES = "/fxml/janela_clientes.fxml";
    public static final String JANELA_FORNECEDORES = "/fxml/janela_fornecedores.fxml";
    public static final String JANELA_PRODUTOS = "/fxml/janela_produtos.fxml";
    public static final String JANELA_ADICIONAR_CLIENTE = "/fxml/janela_adicionar_cliente.fxml";
    public static final String JANELA_ADICIONAR_FORNECEDOR = "/fxml/janela_adicionar_fornecedor.fxml";
    public static final String JANELA_ADICIONAR_PRODUTO = "/fxml/janela_adicionar_produto.fxml";
    public static final String JANELA_ADICIONAR_COMPRA = "/fxml/janela_adicionar_compra.fxml";
    public static final String JANELA_ADICIONAR_COMPRA_ITEM = "/fxml/janela_adicionar_compra_item.fxml";
    public static final String JANELA_ADICIONAR_VENDA = "/fxml/janela_adicionar_venda.fxml";
    public static final String JANELA_ADICIONAR_VENDA_ITEM = "/fxml/janela_adicionar_venda_item.fxml";
    public static final String JANELA_RELATORIO_ESTOQUE = "/fxml/janela_relatorio_estoque.fxml";
    private static StackPane janelaBase;

    private static final UsuarioDAO usuarioDAO = new JDBCUsuarioDAO();
    private static final ClienteDAO clienteDAO = new JDBCClienteDAO();
    private static final FornecedorDAO fornecedorDAO = new JDBCFornecedorDAO();
    private static final ProdutoDAO produtoDAO = new JDBCProdutoDAO();
    private static final CompraDAO compraDAO = new JDBCCompraDAO();
    private static final CompraItemDAO compraItemDAO = new JDBCCompraItemDAO();
    private static final VendaDAO vendaDAO = new JDBCVendaDAO();
    private static final VendaItemDAO vendaItemDAO = new JDBCVendaItemDAO();

    private static ClienteRepository clienteRepository;
    private static FornecedorRepository fornecedorRepository;
    private static ProdutoRepository produtoRepository;
    private static CompraRepository compraRepository;
    private static VendaRepository vendaRepository;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();

        clienteRepository = new ClienteRepositoryImpl();
        fornecedorRepository = new FornecedorRepositoryImpl();
        produtoRepository = new ProdutoRepositoryImpl();
        compraRepository = new CompraRepositoryImpl();
        vendaRepository = new VendaRepositoryImpl();
    }

    @Override
    public void start(Stage stage) {
        Auth.abrirJanelaFazerLogin(stage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void iniciarJanelaPrincipal(Stage stage) {
        janelaBase = new StackPane();
        stage.setScene(new Scene(janelaBase, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE));
        stage.setTitle("EZPOS - Easy Point of Sale - 2021");
        alterarJanela(Main.JANELA_PRINCIPAL, janelaPrincipalCallback());
        stage.show();
    }

    public static void alterarJanela(String fxml, Callback<Class<?>, Object> controllerFactory) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(fxml));
            loader.setControllerFactory(controllerFactory);
            Parent root = loader.load();
            if (janelaBase.getChildren().size() > 0) {
                janelaBase.getChildren().remove(0);
            }
            janelaBase.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void voltaJanelaPrincipal() {
        alterarJanela(JANELA_PRINCIPAL, janelaPrincipalCallback());
    }

    private static Callback<Class<?>, Object> janelaPrincipalCallback() {
        return (aClass) -> new JanelaPrincipal(clienteRepository, fornecedorRepository, produtoRepository, compraRepository, vendaRepository);
    }

    public static UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public static ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public static FornecedorDAO getFornecedorDAO() {
        return fornecedorDAO;
    }

    public static ProdutoDAO getProdutoDAO() {
        return produtoDAO;
    }

    public static CompraDAO getCompraDAO() {
        return compraDAO;
    }

    public static CompraItemDAO getCompraItemDAO() {
        return compraItemDAO;
    }

    public static VendaDAO getVendaDAO() {
        return vendaDAO;
    }

    public static VendaItemDAO getVendaItemDAO() {
        return vendaItemDAO;
    }
}
