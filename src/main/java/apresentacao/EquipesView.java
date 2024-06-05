package apresentacao;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import negocio.AlunoServico;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "escolher-times")
public class EquipesView extends VerticalLayout {

    private final AlunoServico alunoService;

    @Autowired
    public EquipesView(AlunoServico alunoService) {
        this.alunoService = alunoService;

        List<String> alunos = alunoService.getAlunos();

        ListDataProvider<String> dataProvider = new ListDataProvider<>(alunos);


        Grid<String> grid = new Grid<>();
        grid.setDataProvider(dataProvider);

        grid.addColumn(String::toString).setHeader("Email do Aluno");
        grid.addColumn(String::toString).setHeader("Turma do Aluno");
        grid.addColumn(String::toString).setHeader("Função do Aluno");

        add(grid);

        Button voltarButton = new Button("Voltar para Login");
        voltarButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("")));

        add(grid, voltarButton);
    }
}
