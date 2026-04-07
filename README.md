#  Vitta — Sistema de Gestão Odontológica

Vitta é um sistema de gestão que facilita a comunicação entre **dentistas e pacientes**, atuando como intermediário no processo de atendimento. O sistema permite cadastrar pacientes e dentistas, abrir chamados de atendimento e realizar uma triagem automática de prioridade com base nos sintomas relatados.

---

##  Funcionalidades

- **Cadastro de Pacientes** — nome, CPF, idade e telefone
- **Cadastro de Dentistas** — nome, CPF e especialidade
- **Criação de Chamados** — vincula paciente + dentista + formulário de sintomas
- **Triagem Automática de Prioridade** — analisa o sintoma e define se é alta, média ou baixa prioridade
- **Classificação de Risco por Idade** — identifica pacientes idosos e infantis como prioritários
- **Filtragem de Chamados Urgentes** — lista apenas os chamados de alta prioridade
- **Contagem de Chamados por Dentista** — verifica a carga de atendimentos de cada dentista
- **Listagem de Chamados** — exibe todos os chamados registrados com detalhes completos
- **Persistência de dados** — todas as informações são salvas no banco de dados Oracle

---

##  Estrutura do Projeto

```
src/
├── entities/
│   ├── Pessoa.java            # Classe base com nome e CPF
│   ├── Paciente.java          # Herda de Pessoa, adiciona idade e telefone
│   ├── Dentista.java          # Herda de Pessoa, adiciona especialidade
│   ├── Chamado.java           # Vincula Paciente + Dentista + Formulario
│   ├── Formulario.java        # Descrição do problema, data e prioridade
│   ├── Endereco.java          # Endereço do paciente ou clínica
│   ├── Setor.java             # Setor de atendimento
│   └── SistemaChamados.java   # Gerencia listas de pacientes, dentistas e chamados
├── services/
│   └── TriagemIA.java         # Métodos de lógica de negócio
├── dao/
│   ├── PacienteDAO.java       # CRUD completo de pacientes
│   ├── DentistaDAO.java       # CRUD completo de dentistas
│   └── ChamadoDAO.java        # CRUD completo de chamados (com JOIN)
├── infra/
│   └── DatabaseConfig.java    # Conexão com Oracle + criação das tabelas
├── Main.java                  # Menu interativo principal
└── Teste.java                 # Classe de testes completos
```

---

##  Métodos de Lógica de Negócio

| Método | Descrição |
|---|---|
| `analisarSintoma(String descricao)` | Analisa o sintoma e retorna Alta, Média ou Baixa prioridade |
| `classificarRiscoPorIdade(int idade)` | Identifica pacientes idosos (60+) e infantis (12-) como prioritários |
| `filtrarChamadosUrgentes(List<Chamado>)` | Retorna apenas os chamados de alta prioridade |
| `contarChamadosPorDentista(List<Chamado>, String)` | Conta quantos chamados um dentista possui |

---

##  Banco de Dados

O projeto utiliza **Oracle Database** com 3 tabelas:

```sql
paciente  (id, nome, cpf, idade, telefone)
dentista  (id, nome, cpf, especialidade)
chamado   (id, id_paciente, id_dentista, descricao_problema, data_atendimento, prioridade)
```

As tabelas são criadas automaticamente na primeira execução via `DatabaseConfig.initialize()`.

---

##  Como Executar

### Pré-requisitos

- **IntelliJ IDEA** (ou Eclipse / NetBeans)
- **Java JDK 17+** (projeto desenvolvido com OpenJDK 25)
- **Driver JDBC Oracle** — `ojdbc11.jar`
  - Download: https://download.oracle.com/otn-pub/otn_software/jdbc/234/ojdbc11.jar
- Acesso à rede da **FIAP** (presencial ou VPN)

### Passo a Passo

**1. Clone ou extraia o projeto**
```
Abra o IntelliJ IDEA → File → Open → selecione a pasta do projeto
```

**2. Adicione o driver Oracle**
```
File → Project Structure → Libraries → clique em "+"
→ Java → selecione o arquivo ojdbc11.jar → OK → Apply
```

**3. Configure suas credenciais**

Abra o arquivo `infra/DatabaseConfig.java` e substitua:
```java
private static final String USER = "seu_rm_aqui";
private static final String PASSWORD = "sua_senha_aqui";
```

**4. Execute o projeto**

- Para o menu interativo: rode a classe `Main.java`
- Para os testes: rode a classe `Teste.java`

Na primeira execução, as tabelas serão criadas automaticamente no banco Oracle.

---

##  Executando os Testes

A classe `Teste.java` realiza automaticamente:

1. Cadastro de um paciente e listagem do banco
2. Cadastro de um dentista e listagem do banco
3. Teste dos 4 métodos de lógica de negócio com resultados no console
4. Criação de um chamado e validação das filtragens

Saída esperada:
```
---Teste do Paciente---
Paciente salvo com sucesso!
Pacientes no banco de dados: 1

---Teste do Dentista---
Dentista salvo com sucesso!
Dentistas no banco de dados: 1

---Testes do metodo de logica---
Alta prioridade
Média prioridade
Baixa prioridade
...
Muito obrigado, todos os testes foram feitos!
```

---

##  Tecnologias Utilizadas

- Java (OpenJDK 25)
- Oracle Database
- JDBC (ojdbc11)
- IntelliJ IDEA
