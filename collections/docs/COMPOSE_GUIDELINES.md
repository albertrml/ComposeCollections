# Guia de Boas Práticas - Jetpack Compose

Este manual reúne os conceitos avançados de Jetpack Compose aplicados neste projeto para garantir performance e manutenibilidade.

## 1. Padrão Slot API

Componentes complexos (como o `CollectionScaffold`) não devem tentar adivinhar tudo o que o usuário quer desenhar. Em vez disso, deixamos "buracos" (slots) no layout.
- **Exemplo**: `indicator` e `topOverlay` permitem injetar UI customizada sem mudar o motor da biblioteca.

## 2. State Hoisting (Elevação de Estado)

O estado de scroll é criado fora do componente e "passado" para ele.
- **Vantagem**: Permite controle programático e facilita testes de UI isolados (usando Mocks).

## 3. Deferimento de Leitura (Performance)

Usamos lambdas `() -> Boolean` e `derivedStateOf` para cálculos de estado.
- **Performance**: Evita recomposições em cascata durante o scroll, mantendo as atualizações de UI restritas apenas aos botões e indicadores.

## 4. CompositionLocal para Temas

Usamos `CompositionLocal` para prover tokens de design (`labels`, `icons`, `dimens`).
- **Limpeza**: Elimina o "Prop Drilling" (passagem manual de parâmetros por várias camadas).

## 5. Estabilidade (Skippable Composables)

- **Anotações**: Usamos `@Immutable` e `@Stable` para garantir que o compilador possa pular (skip) funções se os dados não mudarem.
- **Validação**: Monitoramos a saúde da biblioteca através das Métricas do Compilador (relatórios gerados no build).
