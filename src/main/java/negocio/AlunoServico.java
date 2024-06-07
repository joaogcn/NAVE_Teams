package negocio;

import entidades.Aluno;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlunoServico {

	private final List<Aluno> alunos = new ArrayList<>();

	public enum Role {
		FACILITATOR, RESEARCHER, ANALYST, REVISER, SUPPORT
	}

	public class TemplateStudent {
		private String name;
		private Role role;
		private int[] competences;

		public TemplateStudent(String name, Role role) {
			this.name = name;
			this.role = role;
		}

		public Role getRole() {
			return role;
		}

		public int[] getCompetences() {
			return competences;
		}

		public void setCompetences(int... competences) {
			this.competences = competences;
		}
	}

	public TemplateStudent createTemplateStudent(String name, Role role, int... competences) {
		TemplateStudent templateStudent = new TemplateStudent(name, role);
		templateStudent.setCompetences(competences);
		return templateStudent;
	}

	public void adicionarAluno(String nome, String email, String turma) {
		if (emailJaExiste(email)) {
			System.out.println("Já existe um aluno com o e-mail " + email);
		} else {
			Aluno aluno = new Aluno(nome, email, turma);
			alunos.add(aluno);
		}
	}

	public boolean emailJaExiste(String email) {
		return alunos.stream().anyMatch(aluno -> aluno.getEmail().equals(email));
	}

	public void removerAluno(Aluno aluno) {
		alunos.remove(aluno);
	}

	public List<Aluno> getAlunos() {
		return new ArrayList<>(alunos);
	}

	public void save(Aluno aluno) {
		Aluno alunoExistente = alunos.stream()
				.filter(a -> a.getEmail().equals(aluno.getEmail()))
				.findFirst()
				.orElse(null);

		if (alunoExistente != null) {
			alunoExistente.setNome(aluno.getNome());
			alunoExistente.setTurma(aluno.getTurma());
			alunoExistente.setCargo(aluno.getCargo());
			alunoExistente.setEquipe(aluno.getEquipe());
			alunoExistente.setCommunication(String.valueOf(aluno.getCommunication()));
			alunoExistente.setOrganization(String.valueOf(aluno.getOrganization()));
			alunoExistente.setEmpathy(String.valueOf(aluno.getEmpathy()));
			alunoExistente.setCuriosity(String.valueOf(aluno.getCuriosity()));
			alunoExistente.setInterpretation(String.valueOf(aluno.getInterpretation()));
		} else {
			alunos.add(aluno);
		}
	}

	public void adicionarAlunos(List<Aluno> novosAlunos) {
		for (Aluno novoAluno : novosAlunos) {
			adicionarAluno(novoAluno.getNome(), novoAluno.getEmail(), novoAluno.getTurma());
		}
	}

	public void calculateScore(Aluno aluno, List<TemplateStudent> templates) {
		for (TemplateStudent template : templates) {
			int score = 0;
			for (int i = 0; i < aluno.getCompetences().length; i++) {
				score += Math.abs(aluno.getCompetences()[i] - template.getCompetences()[i]);
			}
			aluno.getScores().put(template.getRole(), score);
		}
	}

	public void assignRolesToAllStudents(List<TemplateStudent> templates) {
		for (Aluno aluno : alunos) {
			calculateScore(aluno, templates);
			Role bestRole = aluno.getScores().entrySet().stream()
					.min(Map.Entry.comparingByValue())
					.get()
					.getKey();
			aluno.setCargo(bestRole.name());
		}
	}

	public List<TemplateStudent> templates = Arrays.asList(
			createTemplateStudent("facilitator", Role.FACILITATOR, 3, 5, 4, 1, 2),
			createTemplateStudent("researcher", Role.RESEARCHER, 3, 4, 1, 5, 2),
			createTemplateStudent("analyst", Role.ANALYST, 4, 3, 1, 2, 5),
			createTemplateStudent("reviser", Role.REVISER, 3, 1, 2, 5, 4),
			createTemplateStudent("support", Role.SUPPORT, 5, 2, 4, 1, 3)
	);
}
