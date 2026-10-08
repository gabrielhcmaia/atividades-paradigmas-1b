# Aula 07 — Expressões, atribuição e controle: exercício "encontre o bug"

Os códigos estão na pasta `codigos/`. Para cada trecho: (a) qual é a saída, (b) qual regra da linguagem causa o problema e (c) como corrigir.

| Questão | Linguagem | Saída | Regra que causa o problema |
|---|---|---|---|
| 1 | JavaScript | `Total: 0012` | `for...in` percorre chaves; `+` concatena |
| 2 | Python | `180.0` (esperado `200.0`) | `0` conta como falso em `d or 10` |
| 3 | Java | `inválido` | `switch` sem `break` "cai" nos casos seguintes |
| 4 | C | `saldo insuficiente` e `fim` | `;` depois do `if` é uma sentença vazia |
| 5 | Go | `dentro: 20` e `fora: 10` | `:=` cria uma nova variável (sombreamento) |
| 6 | Java | `0.0%` | divisão inteira antes do alargamento para `double` |

## 1) JavaScript

```js
const precos = [10, 20, 30];
let total = 0;
for (const p in precos)
  total += p;
console.log("Total: " + total);
```

**(a) Saída**

```
Total: 0012
```

**(b) Regra.** O `for...in` percorre as **chaves** do arranjo, que são *strings* (`"0"`, `"1"`, `"2"`), e não os valores. Então `p` vale `"0"`, `"1"` e `"2"`. Como o `+` concatena quando um dos lados é texto, `total` vai de `0` para `"00"`, depois `"001"` e por fim `"0012"`. Não há erro nem aviso: é a coerção implícita do JavaScript.

**(c) Correção.** Usar `for...of`, que percorre os valores, ou `reduce`:

```js
for (const p of precos) total += p;             // 60
const total = precos.reduce((a, b) => a + b, 0); // 60
```

## 2) Python

```python
def desconto(preco, d=None):
    d = d or 10  # padrão: 10%
    return preco * (100 - d) / 100

print(desconto(200, 0))
```

**(a) Saída**

```
180.0
```

O esperado era `200.0`, já que o desconto passado foi zero.

**(b) Regra.** Em Python, `0` conta como falso numa expressão booleana, e o `or` devolve um dos **operandos** (o primeiro se for verdadeiro, senão o segundo). Com `d = 0`, `0 or 10` devolve `10`, e o `0` informado pelo chamador se perde. O `or` não distingue "o argumento não foi passado" de "o argumento vale zero".

**(c) Correção.** Testar explicitamente se o argumento é `None`:

```python
if d is None:
    d = 10
```

Com isso `desconto(200, 0)` dá `200.0` e `desconto(200)` continua dando `180.0`. Em JavaScript o equivalente seria usar `??` no lugar de `||`, porque ele só troca `null` e `undefined`.

## 3) Java

```java
int dia = 2; String nome = "";
switch (dia) {
    case 1: nome = "domingo";
    case 2: nome = "segunda";
    case 3: nome = "terça";
    default: nome = "inválido";
}
System.out.println(nome);
```

**(a) Saída**

```
inválido
```

**(b) Regra.** O `switch` com `:` (herdado de C) não termina sozinho: depois de entrar no `case 2`, o controle **cai** (*fall through*) para `case 3` e depois para `default`, e cada um sobrescreve `nome`. A última atribuição é a do `default`, por isso o resultado é `"inválido"`. O `break` funciona como um `goto` restrito para o fim do `switch`.

**(c) Correção.** Colocar `break` em cada caso, ou usar o `switch` com `->` (Java 14+), que não cai e ainda pode ser uma expressão:

```java
switch (dia) {
    case 1: nome = "domingo"; break;
    case 2: nome = "segunda"; break;
    case 3: nome = "terça";   break;
    default: nome = "inválido";
}

String nome = switch (dia) {
    case 1 -> "domingo";
    case 2 -> "segunda";
    case 3 -> "terça";
    default -> "inválido";
};
```

As duas versões imprimem `segunda`.

## 4) C

```c
int saldo = 100, saque = 50;
if (saque > saldo);
printf("saldo insuficiente\n");
printf("fim\n");
```

**(a) Saída**

```
saldo insuficiente
fim
```

A mensagem aparece mesmo com `saque < saldo`, ou seja, mesmo sem a condição ser verdadeira.

**(b) Regra.** O `;` logo depois do `if (...)` é uma **sentença vazia**, que passa a ser o corpo do `if`. O `printf("saldo insuficiente\n")` na linha seguinte fica fora do `if` e executa sempre. A endentação não faz diferença para o compilador de C. É o mesmo tipo de erro do bug "goto fail" da Apple: um `if` sem chaves.

**(c) Correção.** Usar chaves sempre, e remover o `;`:

```c
if (saque > saldo) {
    printf("saldo insuficiente\n");
}
printf("fim\n");
```

Agora só `fim` é impresso. O `gcc` avisa sobre esse caso com `-Wextra` (`-Wempty-body: suggest braces around empty body in an 'if' statement`). Testei aqui com o gcc 13: com apenas `-Wall` **não** aparece aviso. Go e Rust tornam as chaves obrigatórias, então esse erro nem compilaria.

## 5) Go

```go
x := 10
if x > 5 {
    x := x * 2
    fmt.Println("dentro:", x)
}
fmt.Println("fora:", x)
```

**(a) Saída**

```
dentro: 20
fora: 10
```

**(b) Regra.** O `:=` **declara** uma variável nova. Dentro do `if` (que é um bloco com escopo próprio), `x := x * 2` cria um segundo `x`, que *sombreia* o de fora. A multiplicação altera só a cópia interna, e o `x` externo continua valendo `10`. É um problema de escopo e vinculação, e o compilador não reclama porque o `x` interno é usado.

**(c) Correção.** Usar `=` (atribuição) em vez de `:=` (declaração), para alterar a variável que já existe:

```go
x = x * 2
```

Assim a saída vira `dentro: 20` e `fora: 20`.

## 6) Java

```java
int acertos = 7, total = 10;
double taxa = acertos / total * 100;
System.out.println(taxa + "%");
```

**(a) Saída**

```
0.0%
```

**(b) Regra.** Os operadores `/` e `*` têm a mesma precedência e associam à esquerda, então a conta é `(acertos / total) * 100`. Como `acertos` e `total` são `int`, `7 / 10` é uma **divisão inteira** e dá `0` (Java trunca). Só depois disso `0 * 100 = 0` é alargado para `double` na atribuição (atribuição de modo misto), virando `0.0`. O alargamento acontece tarde demais para recuperar a parte fracionária.

**(c) Correção.** Fazer a conta já em ponto flutuante, ou multiplicar antes de dividir:

```java
double taxa = acertos * 100.0 / total;        // 70.0
double taxa = (double) acertos / total * 100; // 70.0
```

Nas duas versões o resultado é `70.0%`.

## Conclusão

Nenhum dos seis programas dá erro de compilação nem de execução: todos rodam e imprimem um valor errado sem avisar. Os erros vêm de regras que a linguagem aplica silenciosamente: coerção de tipos (1 e 6), valores "falsos" em expressões booleanas (2), `switch` que cai (3), `if` sem chaves (4) e escopo com sombreamento (5). As linguagens mais novas reduzem essas armadilhas com `for...of`, chaves obrigatórias, `switch ->` e `match` exaustivo, conversões explícitas e atribuição que não é expressão, trocando um pouco de facilidade de escrita por confiabilidade.
