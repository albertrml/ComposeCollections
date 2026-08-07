# ComposeCollections
![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/albertrml/composecollections/publish.yml?branch=main)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/albertrml/composecollections)
![License](https://img.shields.io/github/license/albertrml/composecollections)

A biblioteca fornece componentes Compose avançados para exibição de coleções de dados, com navegação rápida, cabeçalhos fixos e comportamento consistente entre diferentes layouts.

## 🚀 Visão Geral

O **ComposeCollections** evoluiu de uma ferramenta de navegação para um framework completo de **containers de coleção**. Ele oferece componentes que superam as limitações do Compose nativo, como suporte a Sticky Headers em Grids e navegação otimizada para TV/Hardware.

## ✨ Recursos

*   **CollectionPagedList & Grid**: Navegação fluida por **páginas** (viewport), ideal para catálogos.
*   **CollectionEdgedList & Grid**: Atalhos rápidos para saltar diretamente para o **início** ou **fim**.
*   **Sticky Headers for Grids**: Suporte exclusivo para cabeçalhos fixos em grades e staggered grids.
*   **Acessibilidade & Hardware**: Suporte nativo para teclados e D-pads (TV).
*   **Industrial Performance**: Componentes 100% otimizados pelo compilador do Compose (Skippable).
*   **Totalmente Customizável**: Sistema de temas completo e **Slot API** para injeção de controles customizados (ex: FABs).

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
    implementation("br.com.arml.composecollections:collections:0.2.9")
}
```

## 💡 Exemplos de Uso

### Lista Paginada (Vertical)
```kotlin
import br.com.arml.composecollections.collections.layout.list.CollectionPagedList

CollectionPagedList {
    items(100) { item -> Text("Item $item") }
}
```

### Grade com Cabeçalhos Fixos
```kotlin
import br.com.arml.composecollections.collections.layout.grid.CollectionPagedGrid

CollectionPagedGrid(cells = GridCells.Fixed(3)) {
    stickyHeader { Text("Electronics") }
    items(products) { ProductCard(it) }
}
```

## 🎨 Customização e Temas

Use o `CollectionTheme` para ajustar globalmente o visual e as medidas:

```kotlin
CollectionTheme(
    labels = customLabels,
    icons = customIcons,
    dimens = CollectionDimensionDefaults.default.copy(itemSpacing = 16.dp)
) {
    CollectionPagedList { /* ... */ }
}
```

## 📚 Documentação Detalhada

Confira nossos guias técnicos no módulo principal:
- [Mapa da API e Arquitetura](collections/docs/API_MAP.md)
- [Guia de Migração](collections/docs/MIGRATION_GUIDE.md)
- [Métricas de Performance](collections/docs/COMPOSE_COMPILER_METRICS_GUIDE.md)
- [Normas de Design de API](collections/docs/API_DESIGN_GUIDELINES.md)
- [Arquitetura Interna](collections/docs/ARCHITECTURE.md)

## 🤝 Contribuição

Contribuições são bem-vindas! Se você encontrar um bug ou tiver uma ideia para uma nova funcionalidade, por favor, abra uma issue ou envie um Pull Request.

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
