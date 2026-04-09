import dao.ChamadoDAO;
import dao.DentistaDAO;
import dao.PacienteDAO;
import entities.Chamado;
import entities.Dentista;
import entities.Formulario;
import entities.Paciente;
import services.TriagemIA;

import java.util.List;

public class TesteVitta {

    public static void main(String[] args) {

        PacienteDAO pacienteDAO = new PacienteDAO();
        DentistaDAO dentistaDAO = new DentistaDAO();
        ChamadoDAO chamadoDAO = new ChamadoDAO();
        TriagemIA triagemIA = new TriagemIA();

        System.out.println("---Teste do Paciente---");
        Paciente paciente = new Paciente("Cauã Rocha", "123.456.789-00", 20, "11999999999");
        pacienteDAO.create(paciente);

        List<Paciente> pacientes = pacienteDAO.listarTodos();
        System.out.println("Pacientes no banco de dados: " + pacientes.size());
        pacientes.forEach(p -> System.out.println(p));

        System.out.println("---Teste do Dentista---");
        Dentista dentista = new Dentista("Dra. Maria Eduarda", "987.654.321-00", "Odotonlogia");
        dentistaDAO.create(dentista);

        List<Dentista> dentistas = dentistaDAO.listarTodos();
        System.out.println("Dentistas no banco de dados: " + dentistas.size());
        dentistas.forEach(d -> System.out.println(d));

        System.out.println("---Testes do metodo de logica---");

        // metodo 1 = analisarSintoma
        System.out.println("\n---analisarSintoma---");
        System.out.println(triagemIA.analisarSintoma("dor forte no dente"));
        System.out.println(triagemIA.analisarSintoma("desconforto leve"));
        System.out.println(triagemIA.analisarSintoma("limpeza de rotina"));

        // metodo 2 = classificarRiscoPorIdade
        System.out.println("\n---classificarRiscoPorIdade---");
        System.out.println(triagemIA.classificarRiscoPorIdade(8));
        System.out.println(triagemIA.classificarRiscoPorIdade(30));
        System.out.println(triagemIA.classificarRiscoPorIdade(65));

        // metodo 3 e 4 = precisam de chamados no banco para funcionar
        System.out.println("---Teste Chamado---");
        List<Paciente> ps = pacienteDAO.listarTodos();
        List<Dentista> ds = dentistaDAO.listarTodos();

        if (!ps.isEmpty() && !ds.isEmpty()) {
            ps.get(0).setId(1);
            ds.get(0).setId(1);

            Formulario formulario = new Formulario("dor forte no dente", "10/04/2025");
            formulario.setPrioridade("Alta prioridade");

            Chamado chamado = new Chamado(ps.get(0), ds.get(0), formulario);
            chamadoDAO.create(chamado);

            List<Chamado> chamados = chamadoDAO.listarTodos();
            System.out.println("Chamados no banco de dados: " + chamados.size());

            // metodo 3 = filtrarChamadosUrgentes
            System.out.println("\n---filtrarChamadosUrgentes---");
            List<Chamado> urgentes = triagemIA.filtrarChamadosUrgentes(chamados);
            System.out.println("Chamados urgentes: " + urgentes.size());

            // metodo 4 = contarChamadosPorDentista
            System.out.println("\n---contarChamadosPorDentista---");
            int total = triagemIA.contarChamadosPorDentista(chamados, "Dra. Maria Eduarda");
            System.out.println("Chamados da Dra. Maria Eduarda: " + total);
        }

        System.out.println("Muito obrigado, todos os testes foram feitos! ");
    }
}