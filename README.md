# ComposeCollections
![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/albertrml/composecollections/publish.yml?branch=main)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/albertrml/composecollections)
![License](https://img.shields.io/github/license/albertrml/composecollections)

A biblioteca fornece componentes Compose para exibição de grandes coleções de dados com navegação rápida, indicadores de posição e comportamento consistente entre listas, grids e containers roláveis.

## 🚀 Visão Geral

O **ComposeCollections** foca em melhorar a experiência de navegação em listas longas no Jetpack Compose, oferecendo a API **QuickNav** que facilita o deslocamento rápido através de botões de ação inteligentes.

## ✨ Recursos

*   **QuickNavList**: Uma `LazyColumn` aprimorada com botões para saltar diretamente para o **topo** ou para o **final** da lista.
*   **PagedQuickNavList**: Uma versão que navega por **páginas**, rolando a quantidade exata de itens que estão sendo exibidos na tela no momento.
*   **Customização**: Controle total sobre o estado da lista (`LazyListState`), alinhamento e espaçamento.
*   **Smart Visibility**: Os botões de navegação aparecem e desaparecem automaticamente baseados na posição atual do scroll.

## 📦 Instalação

A **ComposeCollections** pode ser consumida via **GitHub Packages**.

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
    implementation("br.com.arml.composecollections:composecollections:0.1.0")
}
```

## 💡 Exemplos de Uso

### Navegação de Extremos (Início/Fim)
```kotlin
QuickNavList(modifier = Modifier.fillMaxSize()) {
    items(myData) { item ->
        Text(item.name)
    }
}
```

### Navegação por Páginas
```kotlin
PagedQuickNavList(modifier = Modifier.fillMaxSize()) {
    items(myData) { item ->
        Text(item.name)
    }
}
```

## 🤝 Contribuição

Contribuições são bem-vindas! Se você encontrar um bug ou tiver uma ideia para uma nova funcionalidade, por favor, abra uma issue ou envie um Pull Request.

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para mais detalhes.
