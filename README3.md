
# To Do List - Grupo 3
#### Grupo: Caio de Castro Notini, Lívia Alves Ferreira e Luigi Louback de Oliveira

Este terceiro TP de AEDS III se trata da implementação de um relacionamento N:N entre rótulos (classe nova) e tarefas (criada no TP I). Ele também conta com a implementação de uma busca por lista invertida, agora as tarefas podem ser procuradas por termos presentes nelas, categoria e rótulos relacionados.

### Dificuldades:
Enfrentei dificuldades na lógica da implementação do relacionamento N:N. Eu implementei as árvores de forma tranquila, mas na hora de usar confesso que fiquei um pouco perdida e tentei de diversas formas até dar certo.

### Descrição dos Métodos:
***Arquivos*** \
**Arquivos(String n, Constructor<T> c):** construtor da classe.\
**create(T entidade):** adiciona um novo registro ao arquivo. \
**read(int id):** recupera um registro do arquivo pelo seu ID. \
**delete(int id):** exclui um registro. \
**update(T novaEntidade):** atualiza um registro existente. 

***Hash Extensível*** \
**Cesto(Constructor<T> ct, int qtdmax, int pl):** construtor de Cesto. \
**create(T elem):** insere um elemento no cesto. \
**read(int chave):** busca e retorna um elemento de acordo com a chave fornecida. \
**update(T elem):** atualiza elementos do cesto. \
**delete(int chave):** deleta elementos do cesto. \
**empty():** verifica se o cesto está vazio. \
**full():** verifica se o cesto está cheio. \
**size():** retorna o tamanho do cesto.\
**Diretorio():** construtor de diretorio. \
**atualizaEndereco(int p, long e):** atualiza o endereço de um cesto no diretório para um índice específico. \
**endereço(int p):** retorna o endereço do cesto associado a um índice específico no diretório. \
**duplica():** duplica o diretório. \
**hash(int chave):** calcula o hash da chave para encontrar o índice do cesto no diretório. \
**hash2(int chave, int pl):** calcula o hash da chave para uma profundidade local específica. \
**HashExtensivel(Constructor<T> ct, int n, String nd, String nc):** construtor da tabela hash. \
**create(T elem):** insere um novo elemento na tabela hash. \
**read(int chave):** recupera um elemento da tabela hash de acordo com a chave fornecida. \
**update(T elem):** atualiza um elemento na tabela hash. \
**delete(int chave):** deleta um elemento da tabela hash de acordo com a chave fornecida. \
**print():** imprime no console o estado atual do diretório e dos cestos, mostrando a profundidade global, os endereços dos cestos, e os elementos armazenados em cada cesto.

***Tarefa*** \
**public Tarefa():** construtor. \
**public Tarefa(String n, LocalDate dCri, LocalDate dCon, String s, int p, int idCategoria):** construtor. \
**public Tarefa(int i, String n, LocalDate criacao, LocalDate conclusao, String s, int p, int idCategoria):** construtor.

*Novos Métodos* \
***Categoria*** \
**public Categoria():** construtor. \
**public Categoria(String n):** construtor. \
**public Categoria(int i, String n):** construtor. \

***ArvoreBMais*** \


### Perguntas
**O índice invertido com os termos das tarefas foi criado usando a classe ListaInvertida?**\
Sim. 

**O CRUD de rótulos foi implementado?**\

**No arquivo de tarefas, os rótulos são incluídos, alterados e excluídos em uma árvore B+?**\
Sim.

**É possível buscar tarefas por palavras usando o índice invertido?**\
Sim.

**É possível buscar tarefas por rótulos usando uma árvore B+? **\
Sim.

**O trabalho está completo?**\
**O trabalho é original e não a cópia de um trabalho de um colega?**\
Sim, o trabalho é original.