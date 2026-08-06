# Guia de Boas Práticas - Jetpack Compose

Este manual reúne os conceitos avançados de Jetpack Compose aplicados neste projeto para garantir performance e manutenibilidade.

## 1. Padrão Slot API

Componentes complexos (como o `QuickNavScaffold`) não devem tentar adivinhar tudo o que o usuário quer desenhar. Em vez disso, deixamos "buracos" (slots) no layout.
- **Vantagem**: Se o usuário quiser trocar um botão por uma imagem flutuante, ele pode fazer isso através do slot `indicator` ou `topOverlay` sem que precisemos alterar o motor da biblioteca.

## 2. State Hoisting (Elevação de Estado)

O estado de scroll é criado fora do componente e "passado" para ele.
- **Por que?**: Isso permite que o desenvolvedor tenha controle programático (ex: disparar um scroll a partir de um botão fora da lista) e facilita o teste de UI, pois podemos injetar estados pré-configurados.

## 3. Deferimento de Leitura (Performance)

Usamos `derivedStateOf` e lambdas `() -> Boolean` para cálculos de estado.
- **Explicação**: Se lermos o valor do scroll diretamente durante a recomposição, a lista recomporia a cada pixel movido. Ao usar um estado derivado ou uma lambda, o Compose só dispara a recomposição quando o resultado final do cálculo mudar (ex: o botão era falso e virou verdadeiro).

## 4. CompositionLocal para Temas

Em vez de passar `labels`, `icons` e `dimens` por parâmetro em cada função (Prop Drilling), usamos `CompositionLocal`.
- **Benefício**: Os componentes internos "bebem" do tema global automaticamente. Isso limpa as assinaturas das funções e permite que o usuário mude o visual de toda a biblioteca em um único ponto central.

## 5. Estabilidade e Recomposição

- **Classes @Stable**: Marcamos nossas classes de estado com `@Stable` para dizer ao Compose: "Eu garanto que, se os dados não mudarem, você não precisa redesenhar este componente".
- **Lembrete (remember)**: Usamos `remember` com chaves de dependência (`remember(state, scope)`) para garantir que lambdas e objetos caros só sejam recriados quando realmente necessário.
