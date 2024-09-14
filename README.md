
# To Do List - Grupo 3
#### Grupo: Caio de Castro Notini, Lívia Alves Ferreira e Luigi Louback de Oliveira

Este primeiro TP da disciplina de AEDS III trata-se da criação de uma lista de tarefas, mais especificamente da implementação de seu CRUD(Create, Read, Update e Delete). 

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
**public Tarefa(String n, LocalDate dCri, LocalDate dCon, String s, int p):** construtor. \
**public Tarefa(int i, String n, LocalDate criacao, LocalDate conclusao, String s, int p):** construtor.

### Perguntas
**O trabalho possui um índice direto implementado com a tabela hash extensível?** \
Sim. \
**A operação de inclusão insere um novo registro no fim do arquivo e no índice e retorna o ID desse registro?** \
Sim. \
**A operação de busca retorna os dados do registro, após localizá-lo por meio do índice direto?** \
Sim. \
**A operação de alteração altera os dados do registro e trata corretamente as reduções e aumentos no espaço do registro?** \
Sim. \
**A operação de exclusão marca o registro como excluído e o remove do índice direto?** \
Sim. \
**O trabalho está funcionando corretamente?** \
Sim. \
**O trabalho está completo?** \
Sim. \
**O trabalho é original e não a cópia de um trabalho de outro grupo?** \
Sim, o trabalho é original.
