package entidades;

public class Aluno extends Usuario {
	private String turma;
	private String perfil;
	private String funcao;
	
	public Aluno(String nome, String email) {
		super();
		setNome(nome);
		setEmail(email);
	}
	
	public String getTurma() {
		return turma;
	}
	public void setTurma(String turma) {
		this.turma = turma;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	@Override
	public String toString() {
		return "Aluno [Nome = " + getNome() + "]";
	}
	
	

}
