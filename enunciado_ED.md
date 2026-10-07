| Tipo de Prova | Ano letivo | Data |
| :--- | :--- | :--- |
| Trabalho Prático | 2025/2026 | |

**P.PORTO ESCOLA SUPERIOR DE TECNOLOGIA E GESTÃO**
**Curso:** Licenciatura em Engenharia Informática | Licenciatura em Segurança Informática em Redes de Computadores
**Unidade Curricular:** Estruturas de dados

---

## Observações
Este trabalho é dirigido a todos os estudantes inscritos na unidade curricular de Estruturas de Dados (ED) e tem como objetivo avaliar a componente prática da disciplina. Os estudantes deverão organizar-se em grupos de dois elementos, de forma a distribuir de modo equilibrado as tarefas propostas.

## Objetivos
* Aplicar os conhecimentos adquiridos sobre estruturas de dados, selecionando as que melhor se adequam à resolução do problema proposto.
* Conceber e implementar, de forma eficaz e eficiente, o algoritmo de resolução do problema.

## Implementação
* A implementação deve ser realizada em Java.
* O código deve estar devidamente documentado utilizando JavaDoc
* Não é permitida a utilização da plataforma de coleções do Java. Sempre que seja necessário recorrer a uma estrutura de dados, deve ser utilizada a versão desenvolvida durante as aulas pelo próprio grupo.
* À exceção do código disponibilizado nas aulas, a utilização de código que não seja da exclusiva autoria dos elementos do grupo será considerada fraude académica

## Descrição - Labirinto da Glória
Este ano, os estudantes são desafiados a criar um simulador de jogo competitivo onde a objetivo é simples de enunciar, mas complexo de alcançar ser o primeiro a chegar ao centro de um labirinto misterioso e conquistar o tesouro que ali se encontra. O labirinto, no entanto, não é apenas um conjunto de divisões ligadas por corredores. Ao longo do percurso, os jogadores irão enfrentar desafios, armadilhas, enigmas e efeitos inesperados que podem inverter o rumo da partida. O destino de cada participante depende não só das suas escolhas, mas também da forma como reage aos abstáculos e à imprevisibilidade do joga.

## Estrutura do Labirinto
Cada labirinto tem sempre:
* Um ponto central - a sala onde se encontra o tesouro.
* Vários pontos de entrada - possíveis locais de início para os jogadores.
* Divisões - espaços especiais que podem conter enigmas ou alavancas.
* Corredores caminhos que ligam divisões, onde podem surgir eventos aleatórios (itens com efeitos positivos ou negativos).

Os labirintos são definidos a partir de ficheiros externos, em formato JSON, que descrevem as salas, os corredores e os respetivos conteúdos. Também deve existir a possibilidade de criar e guardar novos mapas, reutilizáveis em futuras partidas.

## Dinâmica do Jogo
O jogo decorre em turnos sequenciais. Em cada turno, o jogador ativo escolhe para onde se mover, considerando apenas os caminhos possíveis a partir da sua posição atual. No entanto, a simples deslocação não garante avanço:
* Divisões com alavancas o jogador deve tentar ativá-las para desbloquear passagens. Uma escolha correta abre o caminho; uma escolha errada mantém o bloqueio e o jogador terá de tentar de novo numa jogada futura.
* Divisões com enigmas: o jogador responde a um desafio de raciocínio carregado de um ficheiro externo. Acertar permite continuar, falhar implica tentar de novo numa jogada futura. Um ficheiro externa (JSON) deve conter várias questões (com possíveis respostas e com a resposta correta) que podem ser usadas para escolha aleatória (só podem repetir quando todas já tiverem sido usadas).
* Corredores com eventos aleatórios ao atravessar um corredor, podem ocorrer efeitos inesperados, como ganhar jogadas extra, trocar de posição com outro jogador à escolha, recuar casas, ficar impedido de jogar durante alguns turnos ou trocar todos os jogadores de posições. Nas situações em que é realizada uma troca de posição com outro jogador, o efeito de recuar só poderá recuar até essa posição.

Desta forma, cada jogada combina:
* Decisão estratégica (qual o caminho seguir),
* Gestão de risco (entrar ou não em certas divisões),
* Resolução de desafios (alavancas e enigmas),
* Imprevisibilidade (eventos aleatórios).

## Condições de Vitória e Interação
O vencedor é o primeiro jogador a alcançar a sala central. Durante o jogo, as ações de um jogador podem influenciar os outros abrir passagens que ficam disponíveis para todos, aplicar efeitos que alteram posições ou bloqueios, e até mudar completamente o equilíbrio da competição.

## Modos de Jogo
O sistema deve permitir dois modos:
* Manual: jogadores humanos escolhem movimentos e respondem aos enigmas.
* Automático bots assumem o papel de jogadores, podendo agir aleatoriamente ou seguindo estratégias simples.

Em ambos os modos, o estado do jogo deve ser sempre atualizado e apresentado posição de cada jogador, salas e corredores já atravessados, obstáculos ultrapassados e efeitos em vigor.

## Relatórios
No final da partida, deve ser gerado um relatório em formato JSON, com o registo completo do percurso de cada jogador, obstáculos enfrentados, enigmas resolvidos e efeitos aplicados. Estes relatórios podem ser analisados e reutilizados em futuras sessões.

## Regras de Entrega e Defesa
* Apenas serão considerados para avaliação os trabalhos entregues antes da data-limite definida pelos docentes da UC e disponibilizada no Moodle. A não submissão do trabalho até esta data invalida a sua avaliação.
* A defesa é obrigatória. A não comparência de um membro do grupo não invalida a defesa dos restantes.
* Durante a defesa, poderá ser solicitado a cada estudante que implemente novas funcionalidades ou que modifique funcionalidades já existentes no projeto. Além disso, cada estudante poderá ser questionado sobre as decisões tomadas durante o desenvolvimento.
* O desempenho e o conhecimento individual em relação ao projeto submetido serão avaliados. Poderão ser feitas perguntas orais e/ou escritas, sendo que as escritas podem exigir a utilização de um computador para realizar alterações no projeto entregue. Nestes casos, pode ser necessária uma nova submissão ao final da prova, sendo o estudante responsável por descarregar previamente a versão entregue no Moodle.
* Durante a defesa, a importação e execução do projeto são da exclusiva responsabilidade do estudante.

## Critérios de Avaliação
* Escolha e utilização adequada das estruturas de dados para suportar todas as funcionalidades (critério principal).
* Leitura e validação dos mapas em formato JSON.
* Existência e funcionamento dos modos de jogo manual e automático (ambos obrigatórios).
* Gestão dos diferentes tipos de divisões e corredores (alavancas, enigmas, eventos aleatórios, sala central com tesouro, entradas).
* Atualização e visualização do estado do jogo a cada jogada.
* Geração de relatórios em formato JSON no final da partida, com registo do percurso e eventos de cada jogador.
* Interação entre jogadores e impacto das suas ações nos restantes.
* Gestor e editor de mapas (criação e gravação de novos mapas no formato definido).
* Cumprimento das boas práticas de programação.
* Originalidade da solução (criatividade na abordagem e funcionalidades adicionais).
* Apenas projetos funcionais serão avaliados, sendo obrigatório que a aplicação permita efetivamente a realização de jogos completos.

## Boas Práticas
* Comentários e documentação com JavaDoc.
* Utilização de controlo de versões desde o início do projeto.
* Desenvolvimento de testes unitários que cubram casos relevantes.
* Cumprimento das convenções de codificação em Java (ex: Java Naming Conventions).
* Clareza e usabilidade da aplicação, incluindo mensagens e interações apresentadas ao utilizador.