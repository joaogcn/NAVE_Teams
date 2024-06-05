package apresentacao;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route("notas")
@PageTitle("Notas do Aluno")
public class NotasView extends VerticalLayout {

    public NotasView() {
        FormLayout formLayout = new FormLayout();

        TextField communicationField = new TextField("Comunicação");
        TextField organizationField = new TextField("Organização");
        TextField empathyField = new TextField("Empatia");
        TextField curiosityField = new TextField("Curiosidade");
        TextField interpretationField = new TextField("Interpretação");

        Button submitButton = new Button("Enviar", event -> {
            String communication = communicationField.getValue();
            String organization = organizationField.getValue();
            String empathy = empathyField.getValue();
            String curiosity = curiosityField.getValue();
            String interpretation = interpretationField.getValue();

            Notification.show("Notas enviadas com sucesso!", 3000, Notification.Position.MIDDLE);
        });

        Button voltarButton = new Button("Voltar para Login");
        voltarButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("")));

        formLayout.add(communicationField, organizationField, empathyField, curiosityField, interpretationField, submitButton);
        add(formLayout, voltarButton);
    }
}
