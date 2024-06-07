package apresentacao;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import negocio.AlunoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import entidades.Aluno;
import com.vaadin.flow.server.StreamResource;


import org.apache.commons.csv.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Route("professor")
@Component
public class ProfessorView extends VerticalLayout {

    private final AlunoServico alunoService;
    private Grid<Aluno> grid = new Grid<>(Aluno.class);

    @Autowired
    public ProfessorView(AlunoServico alunoService) {
        this.alunoService = alunoService;

        TextField nomeAlunoField = new TextField("Nome do Aluno");
        TextField emailAlunoField = new TextField("Email do Aluno");
        ComboBox<String> turmaAlunoField = new ComboBox<>("Turma do Aluno");
        turmaAlunoField.setItems("1A", "1B", "1C", "1D");

        Button addButton = new Button("Adicionar", event -> {
            String nome = nomeAlunoField.getValue();
            String email = emailAlunoField.getValue();
            String turma = turmaAlunoField.getValue();
            if (!nome.isEmpty() && !email.isEmpty() && turma != null) {
                if (alunoService.emailJaExiste(email)) {
                    Notification.show("Email já existe!");
                } else {
                    alunoService.adicionarAluno(nome, email, turma);
                    grid.setItems(alunoService.getAlunos());
                    nomeAlunoField.clear();
                    emailAlunoField.clear();
                    turmaAlunoField.clear();
                }
            }
        });

        grid.removeAllColumns();
        grid.addColumn(Aluno::getNome).setHeader("Nome do Aluno");
        grid.addColumn(Aluno::getEmail).setHeader("Email do Aluno");
        grid.addColumn(Aluno::getTurma).setHeader("Turma do Aluno");
        grid.addColumn(Aluno::getCargo).setHeader("Cargo do Aluno");
        grid.addColumn(Aluno::getEquipe).setHeader("Equipe do Aluno");

        Button removeButton = new Button("Remover", event -> {
            Aluno selected = grid.asSingleSelect().getValue();
            if (selected != null) {
                alunoService.removerAluno(selected);
                grid.setItems(alunoService.getAlunos());
            }
        });

        Button voltarButton = new Button("Voltar para Login");
        voltarButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("")));

        Button exportButton = new Button("Exportar para CSV", event -> {
            StringWriter writer = new StringWriter();
            CSVPrinter csvPrinter = null;
            try {
                csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Nome do Aluno", "Email do Aluno", "Turma do Aluno", "Função do Aluno", "Equipe do Aluno"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (Aluno aluno : alunoService.getAlunos()) {
                try {
                    csvPrinter.printRecord(aluno.getNome(), aluno.getEmail(), aluno.getTurma(), aluno.getCargo(), aluno.getEquipe());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                csvPrinter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String csvContent = writer.toString();

            StreamResource sr = new StreamResource("alunos.csv", () -> new ByteArrayInputStream(csvContent.getBytes()));
            Anchor downloadLink = new Anchor(sr, "Baixar CSV");
            downloadLink.getElement().setAttribute("download", true);

            Dialog dialog = new Dialog(downloadLink);
            dialog.open();
        });

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.addSucceededListener(event -> {
            InputStream inputStream = buffer.getInputStream();
            CSVParser csvParser = null;
            try {
                csvParser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT.withFirstRecordAsHeader());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            List<Aluno> alunos = csvParser.getRecords().stream().map(record -> {
                String nome = record.get("Nome do Aluno");
                String email = record.get("Email do Aluno");
                String turma = record.get("Turma do Aluno");
                String cargo = record.isMapped("Cargo do Aluno") ? record.get("Cargo do Aluno") : null;
                String equipe = record.isMapped("Equipe do Aluno") ? record.get("Equipe do Aluno") : null;
                Aluno aluno = new Aluno(nome, email);
                aluno.setTurma(turma);
                aluno.setCargo(cargo);
                aluno.setEquipe(equipe);
                return aluno;
            }).collect(Collectors.toList());

            alunoService.adicionarAlunos(alunos);
            grid.setItems(alunoService.getAlunos());
        });

        Button liberarTimesButton = new Button("Liberar Seleção de Times", event -> {
            VaadinSession.getCurrent().setAttribute("timesLiberados", true);
            List<AlunoServico.TemplateStudent> templates = Arrays.asList(
                    alunoService.createTemplateStudent("facilitator", AlunoServico.Role.FACILITATOR, 3, 5, 4, 1, 2),
                    alunoService.createTemplateStudent("researcher", AlunoServico.Role.RESEARCHER, 3, 4, 1, 5, 2),
                    alunoService.createTemplateStudent("analyst", AlunoServico.Role.ANALYST, 4, 3, 1, 2, 5),
                    alunoService.createTemplateStudent("reviser", AlunoServico.Role.REVISER, 3, 1, 2, 5, 4),
                    alunoService.createTemplateStudent("support", AlunoServico.Role.SUPPORT, 5, 2, 4, 1, 3)
            );
            alunoService.assignRolesToAllStudents(templates);
            grid.setItems(alunoService.getAlunos());
        });

        add(liberarTimesButton);

        add(nomeAlunoField, emailAlunoField, turmaAlunoField, addButton, removeButton, grid, exportButton, upload, voltarButton);
    }
}
