# PRD-001: CollectionPager & Dot Indicators

## Problem Statement
Atualmente, o framework foca em Listas e Grades (Lazy layouts). Desenvolvedores precisam de suporte nativo para navegação página-a-página (estilo Banner ou Onboarding) que utilize a mesma telemetria e botões de navegação rápida já existentes, sem precisar reimplementar a lógica de estado.

## Solution
Implementar o componente `CollectionPager` como um wrapper sobre o `HorizontalPager/VerticalPager` do Compose. O sistema utilizará um novo `CollectionPagerState` que atende ao contrato unificado `CollectionState`, permitindo o uso imediato de todos os indicadores e controles da biblioteca.

## User Stories
1. **Navegação Uniforme**: Como dev, quero usar os botões `Forward/Backward` da biblioteca em um Pager para avançar páginas inteiras.
2. **Atalhos de Extremidade**: Como usuário, quero poder saltar para o primeiro ou último item do Pager usando o modo `Edged`.
3. **Navegação Stepped**: Como dev, quero configurar saltos de "n" páginas ou viewports com um único clique.
4. **Feedback Visual Escalável**: Como usuário, quero ver pontos (dots) indicando minha posição, mesmo que o Pager tenha muitos itens, sem poluir a tela.
5. **Telemetria de Precisão**: Como dev, quero saber o progresso exato (ex: 0.5 entre páginas) para criar animações de transição customizadas.

## Implementation Decisions
- **Módulo**: `:collections` (UI) e `:collections-state` (Lógica).
- **Core Component**: `CollectionPager` (Horizontal e Vertical).
- **State Engine**: `CollectionPagerState` encapsulando o `PagerState` nativo do Compose.
- **Universal Feature**: Introdução da propriedade `step: Int` em todos os estados (`List`, `Grid`, `Pager`).
- **New Indicator**: `CollectionDotIndicator` com comportamento de "Sliding Window" (máximo de 7 pontos visíveis).
- **Scaffold Evolution**: `CollectionScaffold` agora fornece `BoxScope` no slot de indicador, permitindo overlays customizados.

## Testing Decisions
- **Unit Tests**: Validar o cálculo de `scrollProgress` e saltos múltiplos com `step`.
- **UI Tests**: Verificar se o clique no botão "Próximo" altera corretamente o `currentPage` do PagerState.
- **Regression**: Garantir que as mudanças na interface `CollectionState` não quebraram as `CollectionList` existentes.

# Out of Scope
- Implementação de transformações de página complexas (Zoom, Fade) - ficará para o desenvolvedor via slot de conteúdo.
- Integração com o módulo de Android TV (será tratado na v0.3.3).
