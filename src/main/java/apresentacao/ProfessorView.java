package apresentacao;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import negocio.AlunoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Route("professor")
@Component
public class ProfessorView extends VerticalLayout {

    private final AlunoServico alunoService;
    private Grid<String> grid = new Grid<>();

    @Autowired
    public ProfessorView(AlunoServico alunoService) {
        this.alunoService = alunoService;

        TextField emailAlunoField = new TextField("Email do Aluno");
        Button addButton = new Button("Adicionar", event -> {
            String email = emailAlunoField.getValue();
            if (!email.isEmpty()) {
                alunoService.adicionarAluno(email);
                grid.setItems(alunoService.getAlunos());
                emailAlunoField.clear();
            }
        });

        grid.addColumn(String::toString).setHeader("Email do Aluno");

        Button voltarButton = new Button("Voltar para Login");
        voltarButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("")));

        add(emailAlunoField, addButton, grid, voltarButton);
    }
}
