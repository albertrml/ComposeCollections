# Guia de Métricas do Compilador de Compose

Este guia explica como utilizar as métricas do compilador para diagnosticar e otimizar a performance da sua biblioteca ou aplicativo Jetpack Compose.

## 1. O que são Métricas do Compilador?

Diferente do sistema de Views tradicional, o Compose decide o que redesenhar baseado na **Estabilidade** dos dados. O Compilador de Compose pode gerar relatórios que nos dizem exatamente como ele está tratando cada função `@Composable`.

O objetivo principal é garantir que suas funções sejam **Skippable (Puláveis)**.

## 2. Como Configurar (Gradle)

Para ativar a geração desses relatórios, adicione o seguinte bloco ao seu arquivo `build.gradle.kts` (módulo `app` ou biblioteca):

```kotlin
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                    project.layout.buildDirectory.dir("compose_metrics").get().asFile.absolutePath,
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                    project.layout.buildDirectory.dir("compose_metrics").get().asFile.absolutePath
        )
    }
}
```

Após configurar, rode um build de release para gerar os arquivos:
`./gradlew assembleRelease`

## 3. Entendendo o Relatório (`app-composables.txt`)

O arquivo mais importante gerado é o `*-composables.txt`. Nele, cada função é descrita com palavras-chave:

### restartable
Significa que a função pode servir como um escopo de recomposição. Se algo mudar nela, o Compose pode reiniciar apenas esta função. Quase todas as funções devem ser `restartable`.

### skippable (O Objetivo de Ouro)
Significa que, se os parâmetros não mudarem, o Compose **não executará** a função. Ele apenas pula para o próximo componente. Isso economiza muita bateria e processamento.
> **Para ser `skippable`, todos os parâmetros da função devem ser estáveis.**

## 4. Estabilidade de Parâmetros

### stable (Estável)
O compilador confia que este objeto notificará o Compose quando mudar, ou que ele nunca mudará.
- **Tipos Primitivos**: `Int`, `String`, `Boolean` são estáveis por padrão.
- **Classes Marcadas**: Classes com `@Immutable` ou `@Stable`.

### unstable (Instável)
O compilador assume que o objeto pode mudar "pelas costas" do framework. 
- **Listas Padrão**: `List<T>` é instável por padrão (pois pode ser um `ArrayList` mutável por baixo).
- **Classes com `var`**: Classes que possuem propriedades mutáveis sem proteção.
- **Interfaces**: São instáveis por padrão, pois a implementação concreta é desconhecida.

## 5. Como Corrigir Instabilidades

### Prática 1: Use @Immutable ou @Stable
Se você sabe que uma classe não mudará após ser criada, use `@Immutable`.

```kotlin
@Immutable
data class QuickNavLabels(
    val previousLabel: String,
    // ...
)
```

### Prática 2: Resolva Interfaces
Se seu componente recebe uma interface, marque a interface com `@Stable`.

```kotlin
@Stable
interface QuickNavState {
    val scrollProgress: Float
    // ...
}
```

### Prática 3: Deferimento de Leitura (Lambdas)
Se você precisa ler um valor que muda muito rápido (como o scroll), passe uma lambda em vez do valor bruto.

```kotlin
// RUIM: Recompõe a cada pixel movido
fun MyComponent(scrollOffset: Int) 

// BOM: Só recompõe quando a lógica interna da lambda mudar o resultado visual
fun MyComponent(scrollOffset: () -> Int)
```

## 6. Checklist de Performance

1. [ ] Rode o build com métricas ativadas.
2. [ ] Abra o arquivo `build/compose_metrics/*-composables.txt`.
3. [ ] Procure por funções que são `restartable` mas **NÃO** são `skippable`.
4. [ ] Identifique qual parâmetro está marcado como `unstable`.
5. [ ] Aplique `@Immutable` ou use coleções persistentes para estabilizar o componente.

---

Dominar as métricas do compilador é o que separa um desenvolvedor Compose iniciante de um sênior focado em performance.
