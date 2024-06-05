package apresentacao;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        TextField usernameField = new TextField("Usuário");
        Button loginButton = new Button("Login", event -> {
            String username = usernameField.getValue();

            if ("admin".equals(username)) {
                getUI().ifPresent(ui -> ui.navigate("professor"));
            } else {
                getUI().ifPresent(ui -> ui.navigate("alunos"));
            }
        });

        add(usernameField, loginButton);
    }
}
