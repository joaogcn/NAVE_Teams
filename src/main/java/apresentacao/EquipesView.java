package apresentacao;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import negocio.AlunoServico;
import org.springframework.beans.factory.annotation.Autowired;
import entidades.Aluno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "escolher-times")
public class EquipesView extends VerticalLayout {

    private AlunoServico alunoService;
    private Aluno alunoLogado;

    @Autowired
    public EquipesView(AlunoServico alunoService) {
        this.alunoService = alunoService;
        this.alunoLogado = VaadinSession.getCurrent().getAttribute(Aluno.class);

        List<Aluno> alunos = alunoService.getAlunos().stream()
                .filter(aluno -> aluno.getTurma().equals(alunoLogado.getTurma()))
                .collect(Collectors.toList());

        ComboBox<Aluno> analistaComboBox = new ComboBox<>("Analista");
        analistaComboBox.setItems(alunos.stream().filter(aluno -> AlunoServico.Role.ANALYST.name().equals(aluno.getCargo())).collect(Collectors.toList()));
        analistaComboBox.setItemLabelGenerator(Aluno::getEmail);

        ComboBox<Aluno> facilitadorComboBox = new ComboBox<>("Facilitador");
        facilitadorComboBox.setItems(alunos.stream().filter(aluno -> AlunoServico.Role.FACILITATOR.name().equals(aluno.getCargo())).collect(Collectors.toList()));
        facilitadorComboBox.setItemLabelGenerator(Aluno::getEmail);

        ComboBox<Aluno> pesquisadorComboBox = new ComboBox<>("Pesquisador");
        pesquisadorComboBox.setItems(alunos.stream().filter(aluno -> AlunoServico.Role.RESEARCHER.name().equals(aluno.getCargo())).collect(Collectors.toList()));
        pesquisadorComboBox.setItemLabelGenerator(Aluno::getEmail);

        ComboBox<Aluno> revisorComboBox = new ComboBox<>("Revisor");
        revisorComboBox.setItems(alunos.stream().filter(aluno -> AlunoServico.Role.REVISER.name().equals(aluno.getCargo())).collect(Collectors.toList()));
        revisorComboBox.setItemLabelGenerator(Aluno::getEmail);

        ComboBox<Aluno> suporteComboBox = new ComboBox<>("Suporte");
        suporteComboBox.setItems(alunos.stream().filter(aluno -> AlunoServico.Role.SUPPORT.name().equals(aluno.getCargo())).collect(Collectors.toList()));
        suporteComboBox.setItemLabelGenerator(Aluno::getEmail);

        List<String> equipes = new ArrayList<>(Arrays.asList("Equipe 1", "Equipe 2", "Equipe 3", "Equipe 4", "Equipe 5", "Equipe 6", "Equipe 7", "Equipe 8", "Equipe 9"));

        ComboBox<String> equipeComboBox = new ComboBox<>("Equipe");
        equipeComboBox.setItems(equipes);

        Button adicionarButton = new Button("Adicionar à equipe", event -> {
            Aluno analistaSelecionado = analistaComboBox.getValue();
            Aluno facilitadorSelecionado = facilitadorComboBox.getValue();
            Aluno pesquisadorSelecionado = pesquisadorComboBox.getValue();
            Aluno revisorSelecionado = revisorComboBox.getValue();
            Aluno suporteSelecionado = suporteComboBox.getValue();
            String equipeSelecionada = equipeComboBox.getValue();

            if (analistaSelecionado != null && facilitadorSelecionado != null && pesquisadorSelecionado != null && revisorSelecionado != null && suporteSelecionado != null && equipeSelecionada != null) {
                analistaSelecionado.setEquipe(equipeSelecionada);
                facilitadorSelecionado.setEquipe(equipeSelecionada);
                pesquisadorSelecionado.setEquipe(equipeSelecionada);
                revisorSelecionado.setEquipe(equipeSelecionada);
                suporteSelecionado.setEquipe(equipeSelecionada);

                alunoService.save(analistaSelecionado);
                alunoService.save(facilitadorSelecionado);
                alunoService.save(pesquisadorSelecionado);
                alunoService.save(revisorSelecionado);
                alunoService.save(suporteSelecionado);

                alunos.remove(analistaSelecionado);
                alunos.remove(facilitadorSelecionado);
                alunos.remove(pesquisadorSelecionado);
                alunos.remove(revisorSelecionado);
                alunos.remove(suporteSelecionado);

                analistaComboBox.setItems(alunos.stream().filter(aluno -> "Analista".equals(aluno.getCargo())).collect(Collectors.toList()));
                facilitadorComboBox.setItems(alunos.stream().filter(aluno -> "Facilitador".equals(aluno.getCargo())).collect(Collectors.toList()));
                pesquisadorComboBox.setItems(alunos.stream().filter(aluno -> "Pesquisador".equals(aluno.getCargo())).collect(Collectors.toList()));
                revisorComboBox.setItems(alunos.stream().filter(aluno -> "Revisor".equals(aluno.getCargo())).collect(Collectors.toList()));
                suporteComboBox.setItems(alunos.stream().filter(aluno -> "Suporte".equals(aluno.getCargo())).collect(Collectors.toList()));

                equipes.remove(equipeSelecionada);
                equipeComboBox.setItems(equipes);

                Notification.show("Alunos adicionados à equipe com sucesso!");
            } else {
                Notification.show("Por favor, preencha todos os campos!");
            }
        });

        add(analistaComboBox, facilitadorComboBox, pesquisadorComboBox, revisorComboBox, suporteComboBox, equipeComboBox, adicionarButton);

        Button voltarButton = new Button("Voltar para Login");
        voltarButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("")));

        add(voltarButton);
    }
}
