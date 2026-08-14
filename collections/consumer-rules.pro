# Consumer rules for the collections library
# These rules will be bundled with the AAR and applied to apps that depend on it.

# Keep Compose stable types if needed
-keepclassmembers class * {
    @androidx.compose.runtime.Stable <fields>;
    @androidx.compose.runtime.Immutable <fields>;
}