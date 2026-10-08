def desconto(preco, d=None):
    d = d or 10  # padrão: 10%
    return preco * (100 - d) / 100

print(desconto(200, 0))
