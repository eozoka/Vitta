import entities.*;
import entities.SistemaChamados;
import services.TriagemIA;
import infra.DatabaseConfig;

private static final Scanner scan = new Scanner(System.in);
private static final SistemaChamados sistema = new SistemaChamados();
private static final TriagemIA triagemIA = new TriagemIA();

void main() {

    DatabaseConfig.initialize();

    while (true) {
      IO.println("""
              1 - Cadastrar Paciente
              2 - Cadastrar Dentista
              3 - Criar Chamado
              4 - Listar Chamados
              5 - Sair
              Escolha uma opção:
              """);
        int opcao = scan.nextInt();
        scan.nextLine();

        switch (opcao) {
            case 1 -> cadastrarPaciente();
            case 2 -> cadastrarDentista();
            case 3 -> criarChamado();
            case 4 -> listarChamados();
            case 5 -> {
                IO.println("Encerrando o sistema...");
                scan.close();
                return;
            }
            default -> IO.println("Opção inválida. Tente novamente.");
        }
    }
}

private static void cadastrarPaciente() {
    IO.println("=== CADASTRO DO PACIENTE ===");
    IO.print("Nome do paciente: ");
    String nomePaciente = scan.nextLine();

    IO.print("CPF do paciente: ");
    String cpfPaciente = scan.nextLine();

    IO.print("Idade do paciente: ");
    int idadePaciente = scan.nextInt();
    scan.nextLine();

    IO.print("Telefone do paciente: ");
    String telefonePaciente = scan.nextLine();

    Paciente paciente = new Paciente(nomePaciente, cpfPaciente, idadePaciente, telefonePaciente);
    sistema.adicionarPaciente(paciente);
    IO.println("Paciente cadastrado com sucesso!");
}

private static void cadastrarDentista() {
    IO.println("=== CADASTRO DE DENTISTA ===");
    IO.print("Nome do dentista: ");
    String nomeDentista = scan.nextLine();
    IO.print("CPF do dentista: ");
    String cpfDentista = scan.nextLine();
    IO.print("Especialidade do dentista: ");
    String especialidadeDentista = scan.nextLine();

    Dentista dentista = new Dentista(nomeDentista, cpfDentista, especialidadeDentista);
    sistema.adicionarDentista(dentista);
    IO.println("Dentista cadastrado com sucesso!");
}

private static void criarChamado() {
    if (sistema.getPacientes().isEmpty() || sistema.getDentistas().isEmpty()) {
        IO.println("Cadastre pelo menos um paciente e um dentista antes de criar um chamado.");
        return;
    }

    IO.println("=== CRIAÇÃO DE CHAMADO ===");

    IO.println("Pacientes cadastrados:");
    for (int i = 0; i < sistema.getPacientes().size(); i++) {
        IO.println((i + 1) + " - " + sistema.getPacientes().get(i).getNome());
    }
    IO.print("Escolha o paciente: ");
    int indicePaciente = scan.nextInt() - 1;
    scan.nextLine();

    IO.println("Dentistas cadastrados:");
    for (int i = 0; i < sistema.getDentistas().size(); i++) {
        IO.println((i + 1) + " - " + sistema.getDentistas().get(i).getNome());
    }
    IO.print("Escolha o dentista: ");
    int indiceDentista = scan.nextInt() - 1;
    scan.nextLine();

    IO.print("Descreva o problema do paciente: ");
    String descricaoProblema = scan.nextLine();
    IO.print("Data do atendimento (ex: DD/MM/AAAA): ");
    String dataAtendimento = scan.nextLine();

    Formulario formulario = new Formulario(descricaoProblema, dataAtendimento);

    String analise = triagemIA.analisarSintoma(descricaoProblema);
    formulario.setPrioridade(analise);
    IO.println("Análise da triagem automática: " + analise);

    Chamado chamado = new Chamado(
            sistema.getPacientes().get(indicePaciente),
            sistema.getDentistas().get(indiceDentista),
            formulario
    );

    sistema.adicionarChamado(chamado);
    IO.println("Chamado criado com sucesso!");
}

private static void listarChamados() {
    if (sistema.getChamados().isEmpty()) {
        IO.println("Nenhum chamado registrado.");
        return;
    }

    IO.println("=== LISTA DE CHAMADOS ===");
    for (Chamado chamado : sistema.getChamados()) {
        IO.println(chamado.toString());
    }
}