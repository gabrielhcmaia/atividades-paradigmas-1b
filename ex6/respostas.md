# Aula 06 — Tipos de dados: exercício "preveja a saída"

Os códigos estão na pasta `codigos/` (copiados de `08_exercicio` no Drive da disciplina).

| Questão | Saída | Quando o problema é detectado |
|---|---|---|
| `q1.js` | `false` e `9007199254740992` | Nunca |
| `q2.py` | `4 6` | Nunca (não é erro) |
| `q3.go` | `0` | Nunca |
| `Q4.java` | `0` e `ArrayIndexOutOfBoundsException` | Na execução |
| `q5.rs` | Nada: não compila (erro E0382) | Na compilação |
| `q6.c` | `1065353216` | Nunca |

## 1) `q1.js`

```js
console.log(0.1 * 3 === 0.3);
console.log(9007199254740993);
```

Saída:

```
false
9007199254740992
```

Em JavaScript todo `Number` é um ponto flutuante de 64 bits (IEEE 754). O valor 0.1 não tem representação exata em binário, então `0.1 * 3` dá `0.30000000000000004`, e a comparação com `0.3` retorna `false`. Já o número `9007199254740993` é 2^53 + 1. O `double` tem só 53 bits de mantissa, então esse valor não cabe e é arredondado para o vizinho representável mais próximo, `9007199254740992` (2^53). O problema **nunca é detectado**: não há erro nem aviso, o programa só imprime um valor diferente do esperado. Para inteiros grandes o certo seria usar `BigInt` (`9007199254740993n`), e para comparar valores de ponto flutuante seria melhor usar uma tolerância (`Math.abs(a - b) < Number.EPSILON`).

## 2) `q2.py`

```python
palavra = "maçã"
print(len(palavra), len(palavra.encode()))
```

Saída:

```
4 6
```

Em Python 3 a `str` é uma sequência de caracteres Unicode, então `len(palavra)` conta os 4 caracteres: `m`, `a`, `ç` e `ã`. Já `encode()` transforma a cadeia em `bytes` usando UTF-8, que é uma codificação de tamanho variável: `m` e `a` ocupam 1 byte cada, mas `ç` (U+00E7) e `ã` (U+00E3) ocupam 2 bytes cada, somando 6. Não existe erro aqui. O exercício mostra que "tamanho da cadeia" depende de estar contando caracteres ou bytes, o que importa ao gravar em arquivo, limitar o tamanho de um campo no banco ou enviar dados pela rede.

## 3) `q3.go`

```go
var b byte = 255
b++
fmt.Println(b)
```

Saída:

```
0
```

Em Go, `byte` é um apelido para `uint8`, que guarda valores de 0 a 255. A especificação da linguagem define que as operações com inteiros sem sinal são feitas módulo 2^n, então `255 + 1` dá a volta e vira `0`. O estouro **nunca é detectado**: não há panic nem aviso. O compilador só reclama quando o estouro está em uma constante, como em `var b byte = 256`, porque aí o valor é conhecido na compilação. Rust compilado em modo debug, por exemplo, geraria panic nesse mesmo caso.

## 4) `Q4.java`

```java
int[] v = new int[3];
System.out.println(v[0]);
System.out.println(v[3]);
```

Saída:

```
0
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 3
	at Main.main(Q4.java:9)
```

Em Java, os elementos de um arranjo de `int` são inicializados automaticamente com `0`, por isso a primeira linha imprime `0`. O arranjo tem 3 posições (índices 0, 1 e 2), então `v[3]` está fora dos limites. Java verifica a faixa dos índices em todo acesso, e o problema é **detectado na execução**: a JVM lança `ArrayIndexOutOfBoundsException` e o programa termina. O compilador não acusa o erro porque, em geral, o valor do índice só é conhecido durante a execução. Em C o mesmo acesso leria memória fora do arranjo sem nenhum aviso.

## 5) `q5.rs`

```rust
let s = String::from("oi");
let t = s;
println!("{} {}", s, t);
```

Saída: **nenhuma**, o programa não compila.

```
error[E0382]: borrow of moved value: `s`
```

`String` não implementa `Copy`. Por isso, `let t = s;` não copia a cadeia: ele **move** a posse (*ownership*) do valor para `t`, e `s` deixa de ser válida. Usar `s` no `println!` depois disso é um erro, e o problema é **detectado na compilação** pelo verificador de empréstimos (*borrow checker*). Assim Rust impede referências soltas e liberação dupla de memória sem precisar de coletor de lixo. Para funcionar, daria para copiar o valor com `let t = s.clone();` ou só emprestar com `let t = &s;`.

## 6) `q6.c`

```c
union { int i; float f; } u;
u.f = 1.0f;
printf("%d\n", u.i);
```

Saída:

```
1065353216
```

Em C, os membros de uma `union` compartilham os mesmos bytes de memória. O programa grava um `float` e lê esses mesmos 4 bytes como `int`. No padrão IEEE 754 de precisão simples, `1.0f` é representado com sinal 0, expoente 127 (`01111111`) e mantissa 0, o que dá `0x3F800000`, ou seja, `1065353216` em decimal. É uma união livre (sem discriminante): a linguagem não registra qual membro está ativo, então não há verificação de tipo. O problema **nunca é detectado**; nem com `gcc -Wall -Wextra` aparece aviso. Linguagens com uniões discriminadas, como o `enum` de Rust ou as uniões com campo `tipo` do TypeScript, obrigam o programador a verificar qual variante está guardada antes de usar o valor.

## Conclusão

As questões mostram três momentos possíveis de detecção. Rust (`q5`) recusa o programa na compilação. Java (`Q4`) deixa compilar, mas verifica na execução e interrompe o programa. Em JavaScript (`q1`), Go (`q3`) e C (`q6`) o programa roda normalmente e imprime um valor "errado" sem avisar ninguém, que é o caso mais perigoso, porque o erro pode passar despercebido. Quanto mais cedo a linguagem detecta o problema, mais segura ela é, em troca de mais regras para o programador seguir.
