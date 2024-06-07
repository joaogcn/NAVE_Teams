package apresentacao;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "alunos")
public class AlunosView extends VerticalLayout {

    public AlunosView() {
        Button fazerTesteButton = new Button("Fazer Teste de Perfil", event -> {
            getUI().ifPresent(ui -> ui.navigate(NotasView.class));
        });

        Object timesLiberadosAttribute = VaadinSession.getCurrent().getAttribute("timesLiberados");
        Boolean timesLiberados = timesLiberadosAttribute != null ? (Boolean) timesLiberadosAttribute : Boolean.FALSE;

        Button escolherTimesButton = new Button("Escolher Times", event -> {
            getUI().ifPresent(ui -> ui.navigate(EquipesView.class));
        });

        if (Boolean.TRUE.equals(timesLiberados)) {
            add(escolherTimesButton);
        }

        Button voltarButton = new Button("Voltar para Login");
        voltarButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("")));

        add(fazerTesteButton, voltarButton);
    }
}
