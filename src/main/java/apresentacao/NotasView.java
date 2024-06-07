package apresentacao;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import negocio.AlunoServico;
import entidades.Aluno;

@Route("notas")
@PageTitle("Cargo do Aluno")
public class NotasView extends VerticalLayout {

    private AlunoServico alunoServico;

    public NotasView(AlunoServico alunoServico) {
        this.alunoServico = alunoServico;
        FormLayout formLayout = new FormLayout();

        TextField communicationField = new TextField("Comunicação");
        TextField organizationField = new TextField("Organização");
        TextField empathyField = new TextField("Empatia");
        TextField curiosityField = new TextField("Curiosidade");
        TextField interpretationField = new TextField("Interpretação");

        Button submitButton = new Button("Enviar", event -> {
            try {
                int communication = Integer.parseInt(communicationField.getValue());
                int organization = Integer.parseInt(organizationField.getValue());
                int empathy = Integer.parseInt(empathyField.getValue());
                int curiosity = Integer.parseInt(curiosityField.getValue());
                int interpretation = Integer.parseInt(interpretationField.getValue());

                if (communication < 1 || communication > 5 || organization < 1 || organization > 5 || empathy < 1 || empathy > 5 || curiosity < 1 || curiosity > 5 || interpretation < 1 || interpretation > 5) {
                    Notification.show("Por favor, insira uma pontuação de 1 a 5 para cada categoria.", 3000, Notification.Position.MIDDLE);
                    return;
                }

                Aluno alunoAtual = VaadinSession.getCurrent().getAttribute(Aluno.class);
                if (alunoAtual != null) {
                    alunoAtual.setCommunication(String.valueOf(communication));
                    alunoAtual.setOrganization(String.valueOf(organization));
                    alunoAtual.setEmpathy(String.valueOf(empathy));
                    alunoAtual.setCuriosity(String.valueOf(curiosity));
                    alunoAtual.setInterpretation(String.valueOf(interpretation));

                    alunoServico.save(alunoAtual);

                    Notification.show("Notas enviadas com sucesso!", 3000, Notification.Position.MIDDLE);
                } else {
                    Notification.show("Nenhum aluno logado.", 3000, Notification.Position.MIDDLE);
                }
            } catch (NumberFormatException e) {
                Notification.show("Por favor, insira um número válido.", 3000, Notification.Position.MIDDLE);
            }
        });

        Button voltarButton = new Button("Voltar para Login");
        voltarButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("")));

        Paragraph infoText = new Paragraph("Por favor, insira uma pontuação de 1 a 5 para cada categoria.");

        formLayout.add(communicationField, organizationField, empathyField, curiosityField, interpretationField, submitButton);
        add(formLayout, voltarButton, infoText);
    }
}
