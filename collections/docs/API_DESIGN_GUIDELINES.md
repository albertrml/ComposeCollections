# Guia de Construção de APIs (Normas do Projeto)

Este documento descreve as normas e boas práticas de Engenharia de Software aplicadas na construção da biblioteca `ComposeCollections`.

## 1. Anatomia de Funções (Ordem de Parâmetros)

Seguimos as diretrizes oficiais do Google para Jetpack Compose. A ordem deve ser previsível para facilitar o uso do desenvolvedor:

1.  **Modifier**: O primeiro parâmetro opcional.
2.  **Estado (State)**: Objetos que controlam o que o componente exibe (ex: `LazyListState`, `CollectionState`).
3.  **Configurações de Negócio**: Parâmetros que definem o comportamento (ex: `layoutSpec`, `animationMode`, `cells`).
4.  **Flags de Comportamento**: Valores booleanos simples (ex: `isOverlay`, `showIndicator`).
5.  **Overrides de Tema**: Parâmetros para customização visual pontual (ex: `labels`, `icons`, `dimens`).
6.  **Conteúdo (Lambda)**: O último parâmetro para permitir a sintaxe de *trailing lambda*.

## 2. Nomenclatura Semântica

Evitamos nomes que descrevam a posição física e preferimos nomes que descrevam a **intenção**.
- **Bom**: `showScrollToBackward` (Direção universal, independente do eixo).
- **Bom**: `CollectionPagedList` (Descreve o tipo de container e o comportamento principal).

## 3. Ocultação de Implementação

Utilizamos o modificador `internal` agressivamente. 
- Apenas o que o usuário **precisa** configurar é `public`.
- Componentes de montagem (`CollectionNavigationFrame`, `CollectionRouter`) e utilitários de renderização são `internal`.

## 4. Polimorfismo por Interfaces

Componentes de UI nunca devem depender de classes concretas de estado, mas sim de **Interfaces**.
- O `CollectionScaffold` aceita `CollectionState`. Isso permite extensibilidade total sem quebras na UI.
