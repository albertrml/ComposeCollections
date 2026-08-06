# ComposeCollections
![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/albertrml/composecollections/publish.yml?branch=main)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/albertrml/composecollections)
![License](https://img.shields.io/github/license/albertrml/composecollections)

A biblioteca fornece componentes Compose para exibição de grandes coleções de dados com navegação rápida, indicadores de posição e comportamento consistente entre listas e containers roláveis.

## 🚀 Visão Geral

O **ComposeCollections** foca em melhorar a experiência de navegação em listas longas no Jetpack Compose, oferecendo a API **QuickNav** que facilita o deslocamento rápido através de botões de ação inteligentes e transições suaves.

## ✨ Recursos

*   **EdgedList & EdgedGrid**: Lazy containers aprimorados com botões para saltar diretamente para o **início** ou para o **fim**.
*   **PagedList & PagedGrid**: Navegação por **páginas**, rolando exatamente a quantidade de itens visíveis na tela (viewport).
*   **Sticky Headers for Grids**: Suporte exclusivo para cabeçalhos fixos em grades (`PagedGrid`, `EdgedGrid`, etc.), superando limitações nativas do Compose.
*   **Acessibilidade & Hardware**: Suporte nativo para teclados e D-pads (TV). Use `PageUp/Down` para páginas e `Home/End` para extremos.
*   **Scroll Presets**: Escolha entre diferentes sensações de animação: `Snap` (parada seca) ou `Elastic` (rebote fluido).
*   **Progress Indicators**: Nova barra de progresso visual que mostra o progresso do scroll em tempo real. Ative com `showIndicator = true`.
*   **Suporte a Staggered Grids**: Agora suportamos grades irregulares estilo Pinterest via `PagedStaggeredGrid` e `EdgedStaggeredGrid`.
*   **Suporte Horizontal & Vertical**: Todos os componentes suportam ambas as orientações via `QuickNavLayoutSpec`.
*   **API Protegida**: Componentes de suporte internos estão ocultos para garantir uma superfície de API limpa e estável.

## 📦 Instalação

### 1. Configurar o GitHub Packages no seu `settings.gradle.kts`

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/albertrml/ComposeCollections")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
```

### 2. Adicionar a dependência

```kotlin
dependencies {
    implementation("br.com.arml.composecollections:composecollections:0.2.5")
}
```

## 💡 Exemplos de Uso

### Lista Paginada (Vertical)
```kotlin
import br.com.arml.composecollections.scrollables.layout.list.PagedList

PagedList(
    layoutSpec = QuickNavLayoutSpec.Vertical(),
    navigationAlignment = NavigationAlignment.Bottom
) {
    items(100) { item -> Text("Item $item") }
}
```

### Grade de Extremos (Horizontal Overlay)
```kotlin
import br.com.arml.composecollections.scrollables.layout.grid.EdgedGrid

EdgedGrid(
    cells = GridCells.Fixed(3),
    layoutSpec = QuickNavLayoutSpec.Horizontal(),
    isOverlay = true
) {
    items(100) { item -> Card { Text("Box $item") } }
}
```

## 🎨 Customização e Temas

Você pode customizar globalmente rótulos, ícones e animações usando o `QuickNavTheme`.

### Tradução de Rótulos
```kotlin
val customLabels = QuickNavLabels(
    previousLabel = "Voltar",
    nextLabel = "Avançar",
    // ...
)

QuickNavTheme(labels = customLabels) {
    EdgedList { /* ... */ }
}
```

## 📚 Documentação Detalhada

Confira nossos guias:
- [Arquitetura](app/src/main/java/br/com/arml/composecollections/docs/ARCHITECTURE.md)
- [Customização e Estados](app/src/main/java/br/com/arml/composecollections/docs/CUSTOMIZATION.md)
- [Guia de Migração (v0.1 para v0.2)](app/src/main/java/br/com/arml/composecollections/docs/MIGRATION_GUIDE.md)
- [Primeiros Passos](app/src/main/java/br/com/arml/composecollections/docs/GETTING_STARTED.md)
- [Mapa da API](app/src/main/java/br/com/arml/composecollections/docs/API_MAP.md)

## 🤝 Contribuição

Contribuições são bem-vindas! Se você encontrar um bug ou tiver uma ideia para uma nova funcionalidade, por favor, abra uma issue ou envie um Pull Request.

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
