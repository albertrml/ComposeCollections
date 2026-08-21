---
name: architecture
description: Padrões de arquitetura para o ecossistema ComposeCollections.
---

# Manual de Arquitetura (Elite Android)

Toda implementação deve seguir estes dogmas arquiteturais.

## 1. Módulos e Isolamento
- **`:collections`**: UI Framework. Foco em estabilidade do Compose (`@Stable`, `@Immutable`).
- **`Shared Models`**: Devem ser imutáveis e preferencialmente `data classes`.

## 2. Jetpack Compose (Regras de Ouro)
- **Hoisting**: O estado deve subir, os eventos devem descer.
- **Stability**: Toda classe de estado deve ser anotada com `@Stable` ou `@Immutable`.
- **Derived State**: Use `derivedStateOf` para cálculos que dependem de outros estados (como paginação e progresso de scroll).
- **Skippable**: Use a ferramenta `task` para gerar relatórios de métricas do compilador e garantir que os componentes são skippables.

## 3. Fluxo de Dados (MVI)
- **Unidirecional**: State -> UI -> Event -> Reducer -> State.
- **Efeitos de Lado**: Use `Channels` ou `SharedFlow` para efeitos únicos (Toasts, Navegação).

## 4. Naming Convention
- **Interfaces de Estado**: `CollectionState`.
- **Implementações**: `CollectionListState`.
- **Extensões**: `normalizeForSearch()`.
