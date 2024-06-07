package entidades;

import negocio.AlunoServico.Role;

import java.util.HashMap;
import java.util.Map;

public class Aluno extends Usuario {
	private String turma;
	private String cargo;
	private String equipe;
	private int communication;
	private int organization;
	private int empathy;
	private int curiosity;
	private int interpretation;
	private Map<Role, Integer> scores = new HashMap<>();

	public Aluno(String nome, String email, String turma) {
		super(nome, email);
		this.turma = turma;
	}

	public Aluno(String nome, String email) {
		super(nome, email);
	}

	public Aluno(String nome, String email, String turma, String cargo, String equipe) {
		super(nome, email);
		this.turma = turma;
		this.cargo = cargo;
		this.equipe = equipe;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEquipe() {
		return equipe;
	}

	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}

	public int getCommunication() {
		return communication;
	}

	public void setCommunication(String communication) {
		this.communication = Integer.parseInt(communication);
	}

	public int getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = Integer.parseInt(organization);
	}

	public int getEmpathy() {
		return empathy;
	}

	public void setEmpathy(String empathy) {
		this.empathy = Integer.parseInt(empathy);
	}

	public int getCuriosity() {
		return curiosity;
	}

	public void setCuriosity(String curiosity) {
		this.curiosity = Integer.parseInt(curiosity);
	}

	public int getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = Integer.parseInt(interpretation);
	}

	public int[] getCompetences() {
		return new int[] {communication, organization, empathy, curiosity, interpretation};
	}

	public Map<Role, Integer> getScores() {
		return scores;
	}
}
