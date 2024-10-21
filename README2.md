
# To Do List - Grupo 3
#### Grupo: Caio de Castro Notini, Lívia Alves Ferreira e Luigi Louback de Oliveira

Este segundo TP da disciplina de AEDS III trata-se da implementação de uma árvore B+ como índice indireto, criação da classe categorias e criação do relacionamento 1:N entre caregoria e tarefas (classe criada anteriormente).

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
**O CRUD (com índice direto) de categorias foi implementado?** \

**Há um índice indireto de nomes para as categorias?** \

**O atributo de ID de categoria, como chave estrangeira, foi criado na classe Tarefa?**\
Sim.

**Há uma árvore B+ que registre o relacionamento 1:N entre tarefas e categorias?**\

**É possível listar as tarefas de uma categoria?**\
Sim.

**A remoção de categorias checa se há alguma tarefa vinculada a ela?**\
Sim.

**A inclusão da categoria em uma tarefa se limita às categorias existentes?**\
Sim.

**O trabalho está funcionando corretamente?**\
Sim.

**O trabalho está completo?**\

**O trabalho é original e não a cópia de um trabalho de outro grupo?**\
Sim, o trabalho é original.
