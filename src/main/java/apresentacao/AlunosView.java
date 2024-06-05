package apresentacao;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "alunos")
public class AlunosView extends VerticalLayout {

    public AlunosView() {
        Button fazerTesteButton = new Button("Fazer Teste de Perfil", event -> {
            getUI().ifPresent(ui -> ui.navigate(NotasView.class));
        });

        Button escolherTimesButton = new Button("Escolher Times", event -> {
            getUI().ifPresent(ui -> ui.navigate(EquipesView.class));
        });

        Button voltarButton = new Button("Voltar para Login");
        voltarButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("")));

        add(fazerTesteButton, escolherTimesButton, voltarButton);
    }
}
