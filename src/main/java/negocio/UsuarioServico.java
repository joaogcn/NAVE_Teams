package negocio;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsuarioServico {

    private Map<String, String> users = new HashMap<>();

    public UsuarioServico () {
        users.put("admin", "professor");
        users.put("student1", "aluno");
        users.put("student2", "aluno");
        // Adicione mais usuários conforme necessário
    }

    public String getUserRole(String username) {
        return users.getOrDefault(username, "aluno");
    }
}
