package apresentacao;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import negocio.AlunoServico;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.server.VaadinSession;
import entidades.Aluno;

@Route("")
public class MainView extends VerticalLayout {

    private final AlunoServico alunoService;

    @Autowired
    public MainView(AlunoServico alunoService) {
        this.alunoService = alunoService;

        H1 title = new H1("NAVE Teams");
        title.getStyle().set("color", "blue");
        title.getStyle().set("text-align", "center");

        TextField usernameField = new TextField("Usuário");
        Button loginButton = new Button("Login", event -> {
            String username = usernameField.getValue();

            if ("admin".equals(username)) {
                getUI().ifPresent(ui -> ui.navigate("professor"));
            } else if (alunoService.getAlunos().stream().anyMatch(aluno -> aluno.getEmail().equals(username))) {
                Aluno alunoLogado = alunoService.getAlunos().stream().filter(aluno -> aluno.getEmail().equals(username)).findFirst().get();
                VaadinSession.getCurrent().setAttribute(Aluno.class, alunoLogado);
                getUI().ifPresent(ui -> ui.navigate("alunos"));
            } else {
                Notification.show("Usuário não encontrado", 3000, Notification.Position.MIDDLE);
            }
        });

        add(title, usernameField, loginButton);
    }
}
