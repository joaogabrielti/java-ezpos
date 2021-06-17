package ezpos;

import ezpos.daos.JDBCClienteDAO;
import ezpos.daos.JDBCFornecedorDAO;
import ezpos.gui.control.JanelaPrincipal;
import ezpos.repositories.ClienteRepositoryImpl;
import ezpos.repositories.FornecedorRepositoryImpl;
import ezpos.repositories.interfaces.ClienteRepository;
import ezpos.repositories.interfaces.FornecedorRepository;
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
    public static final String JANELA_ADICIONAR_CLIENTE = "/fxml/janela_adicionar_cliente.fxml";
    public static final String JANELA_ADICIONAR_FORNECEDOR = "/fxml/janela_adicionar_fornecedor.fxml";
    private static StackPane janelaBase;

    private static ClienteRepository clienteRepository;
    private static FornecedorRepository fornecedorRepository;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();

        clienteRepository = new ClienteRepositoryImpl(new JDBCClienteDAO());
        fornecedorRepository = new FornecedorRepositoryImpl(new JDBCFornecedorDAO());
    }

    @Override
    public void start(Stage stage) {
        janelaBase = new StackPane();
        stage.setScene(new Scene(janelaBase, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE));
        stage.setTitle("EZPOS - Easy Point of Sale - 2021");
        alterarJanela(Main.JANELA_PRINCIPAL, janelaPrincipalCallback());
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
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
        return (aClass) -> new JanelaPrincipal(clienteRepository, fornecedorRepository);
    }
}
