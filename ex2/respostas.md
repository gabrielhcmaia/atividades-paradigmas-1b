1) A evolução das linguagens não funciona como uma escada porque uma linguagem nova nem sempre substitui a anterior. Muitas continuam sendo usadas porque foram feitas para áreas diferentes. Fortran, por exemplo, foi criada para cálculos científicos, enquanto COBOL foi criada para sistemas comerciais. Dois fatores que explicam essa influência são as necessidades de cada área, que fazem uma linguagem aproveitar ideias de outra, e a base já existente de programas, bibliotecas e profissionais, que dificulta abandonar uma linguagem antiga.

2) Mesmo sem ter sido implementada na época, Plankalkül foi importante porque apresentou ideias avançadas para a década de 1940. Ela já previa arranjos e registros, comandos de seleção e repetição e subprogramas com parâmetros. Os registros foram uma contribuição importante porque permitem juntar vários dados relacionados em uma única estrutura, ideia usada depois em linguagens como Pascal e C.

4) Na época em que Fortran foi criada, os computadores eram muito caros e tinham pouca memória. Por isso, os programadores escreviam código de máquina manualmente para conseguir o melhor desempenho possível. Para ser aceita, Fortran precisava mostrar que seu compilador gerava programas quase tão rápidos quanto os escritos à mão. Quando isso aconteceu, ela passou a ser vantajosa porque mantinha um bom desempenho e diminuía bastante o tempo gasto para escrever e corrigir programas.

5) Fortran foi criada principalmente para cálculos científicos e de engenharia. Ela trabalha muito com números, fórmulas, arranjos e repetições, seguindo um estilo imperativo. Lisp surgiu ligada à inteligência artificial e ao processamento de símbolos. Sua principal estrutura de dados é a lista, e seu estilo é funcional, usando bastante funções e recursão. Assim, Fortran era mais adequada para cálculos numéricos, enquanto Lisp era melhor para manipular símbolos e estruturas variáveis.

6) Três contribuições importantes de ALGOL 60 foram o uso da BNF para descrever formalmente a sintaxe, a organização do programa em blocos com variáveis locais e o suporte à recursão. Essas ideias apareceram depois em várias linguagens, como Pascal, C e Java. Mesmo sem dominar o mercado, ALGOL 60 foi muito influente. Sua adoção foi limitada porque era difícil de implementar, não tinha entrada e saída padronizadas e Fortran já era muito usada e tinha o apoio da IBM.

11) ALGOL 60 influenciou Pascal por meio de ALGOL W. C também recebeu influência de ALGOL, mas por outro caminho: ALGOL 60, CPL, BCPL, B e C. Portanto, C não surgiu de Pascal, mas as duas possuem ALGOL como uma de suas raízes. Pascal e C são linguagens imperativas, nas quais o programador informa os passos que alteram o estado do programa. Prolog é diferente porque o programador declara fatos e regras, e a própria linguagem procura uma resposta para a consulta usando inferência lógica.

12)

```prolog
pai(joao, pedro).
pai(pedro, lucas).

avo(X, Z) :- pai(X, Y), pai(Y, Z).

?- avo(joao, lucas).
```

Os dois primeiros itens são fatos. A regra diz que `X` é avô de `Z` se `X` for pai de `Y` e `Y` for pai de `Z`. A consulta pergunta se João é avô de Lucas. Isso é programação lógica porque Prolog não apenas guarda os dados: ele combina os fatos com a regra e conclui que a resposta é verdadeira.

13) Ada foi criada para sistemas grandes e críticos, principalmente sistemas embarcados do Departamento de Defesa dos Estados Unidos. Seu sistema de tipos ajuda a encontrar erros antes da execução. Os pacotes organizam e protegem dados e operações, facilitando a manutenção e o reaproveitamento do código. A concorrência é feita com tarefas e com o mecanismo de *rendezvous*, que permite sincronizar atividades. Esses recursos ajudam a tornar o sistema mais organizado, previsível e confiável.

14) Em Smalltalk, tudo é tratado como objeto e a computação acontece por meio de mensagens entre eles. C++ adicionou classes e herança ao C, mas manteve quase todos os recursos da linguagem original para preservar desempenho e compatibilidade. Por isso, ela mistura programação procedural e orientada a objetos. Java simplificou algumas partes de C++, removendo recursos como aritmética de ponteiros e herança múltipla de classes. Sua portabilidade vem do *bytecode*, que pode ser executado em diferentes sistemas por meio da JVM.

15) Java foi criada primeiro para dispositivos eletrônicos e sistemas embarcados, mas esses produtos não tiveram sucesso. Com o crescimento da Web, sua portabilidade passou a ser muito útil, pois o mesmo programa podia rodar em sistemas diferentes usando a JVM. Os *applets* ajudaram a popularizar Java nos navegadores. Isso mostra que uma linguagem pode ganhar uma nova função quando o contexto muda e suas características passam a resolver outro tipo de problema.
