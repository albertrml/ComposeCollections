# Guia de Construção de APIs (Normas do Projeto)

Este documento descreve as normas e boas práticas de Engenharia de Software aplicadas na construção da biblioteca `ComposeCollections`.

## 1. Anatomia de Funções (Ordem de Parâmetros)

Seguimos as diretrizes oficiais do Google para Jetpack Compose. A ordem deve ser previsível para facilitar o uso do desenvolvedor:

1.  **Modifier**: O primeiro parâmetro opcional. Permite que quem chama o componente decida seu tamanho e posição.
2.  **Estado (State)**: Objetos que controlam o que o componente exibe (ex: `LazyListState`, `QuickNavState`).
3.  **Configurações de Negócio**: Parâmetros que definem o comportamento (ex: `layoutSpec`, `animationMode`).
4.  **Flags de Comportamento**: Valores booleanos simples (ex: `isOverlay`, `showIndicator`).
5.  **Overrides de Tema**: Parâmetros para customização visual pontual (ex: `labels`, `icons`, `dimens`).
6.  **Conteúdo (Lambda)**: O último parâmetro para permitir a sintaxe de *trailing lambda*.

## 2. Nomenclatura Semântica vs. Posicional

Evitamos nomes que descrevam a posição física e preferimos nomes que descrevam a **intenção**.
- **Ruim**: `showScrollToTop` (E se a lista for horizontal? O topo vira a esquerda).
- **Bom**: `showScrollToBackward` (Direção universal, independente do eixo).

## 3. Ocultação de Implementação (Encapsulamento)

Utilizamos o modificador `internal` agressivamente. 
- Apenas o que o usuário **precisa** configurar é `public`.
- Componentes de montagem (`QuickNavNavigationFrame`, `NavigationRouter`) são `internal`. Isso protege a API contra quebras acidentais quando decidirmos mudar a estrutura interna.

## 4. Polimorfismo por Interfaces

Componentes de UI nunca devem depender de classes concretas de estado, mas sim de **Interfaces**.
- O `QuickNavScaffold` aceita `QuickNavState`. Isso permite que o usuário crie sua própria lógica de scroll (ex: uma lista que só rola se um sensor for ativado) e a UI continuará funcionando sem alterações.

## 5. Evolução e Depreciação

Seguimos o Ciclo de Vida de Depreciação:
1.  **vNext**: Marcar métodos antigos como `@Deprecated` com `ReplaceWith`.
2.  **vMajor**: Remover o código antigo para manter a biblioteca limpa.
3.  Nunca quebrar uma API em uma versão de correção (Patch).
