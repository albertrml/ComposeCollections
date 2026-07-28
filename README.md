# ComposeCollections
![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/albertrml/composecollections/publish.yml?branch=main)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/albertrml/composecollections)
![License](https://img.shields.io/github/license/albertrml/composecollections)

A biblioteca fornece componentes Compose para exibição de grandes coleções de dados com navegação rápida, indicadores de posição e comportamento consistente entre listas e containers roláveis.

## 🚀 Visão Geral

O **ComposeCollections** foca em melhorar a experiência de navegação em listas longas no Jetpack Compose, oferecendo a API **QuickNav** que facilita o deslocamento rápido através de botões de ação inteligentes e transições suaves.

## ✨ Recursos

*   **EdgedList**: Uma `LazyColumn` aprimorada com botões para saltar diretamente para o **início** ou para o **fim** da lista.
*   **PagedList**: Uma versão que navega por **páginas**, rolando exatamente a quantidade de itens visíveis na tela.
*   **Smart Visibility**: Botões de navegação inteligentes que aparecem/desaparecem baseados na posição do scroll.
*   **Animações Suaves**: Transições fluidas de entrada e saída para os controles de navegação.
*   **Altamente Customizável**: Suporte total a temas e internacionalização.

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
    implementation("br.com.arml.composecollections:composecollections:0.1.2")
}
```

## 💡 Exemplos de Uso

### Navegação de Extremos (Início/Fim)
```kotlin
EdgedList(modifier = Modifier.fillMaxSize()) {
    items(myData) { item ->
        Text(item.name)
    }
}
```

### Navegação por Páginas
```kotlin
PagedList(modifier = Modifier.fillMaxSize()) {
    items(myData) { item ->
        Text(item.name)
    }
}
```

## 🎨 Customização e Temas

A partir da versão **0.1.2**, você pode customizar globalmente rótulos, ícones e animações usando o `QuickNavTheme`.

### Tradução de Rótulos
```kotlin
val customLabels = QuickNavLabelDefaults.edgedLabels().copy(
    previousLabel = "Voltar ao Início",
    nextLabel = "Ir para o Fim"
)

QuickNavTheme(labels = customLabels) {
    EdgedList { /* ... */ }
}
```

### Troca de Ícones
```kotlin
val customIcons = QuickNavIconDefaults.default.copy(
    upIcon = Icons.Filled.ArrowUpward,
    downIcon = Icons.Filled.ArrowDownward
)

QuickNavTheme(icons = customIcons) {
    PagedList { /* ... */ }
}
```

## 🤝 Contribuição

Contribuições são bem-vindas! Se você encontrar um bug ou tiver uma ideia para uma nova funcionalidade, por favor, abra uma issue ou envie um Pull Request.

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
