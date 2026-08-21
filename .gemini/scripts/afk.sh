#!/bin/bash
# AFK (Away From Keyboard) - Script de Verificação de Saúde do Projeto
# Use este script para garantir que o projeto está estável antes de grandes mudanças ou após implementações.

echo "🔍 Iniciando Verificação de Saúde (Health Check)..."

# 1. Limpeza básica (opcional, remova se o build estiver lento)
# ./gradlew clean

# 2. Build e Teste do Módulo Core (:collections)
echo "📦 Verificando módulo :collections..."
./gradlew :collections:test :collections:assembleDebug
if [ $? -ne 0 ]; then
    echo "❌ Erro no módulo :collections. Abortando."
    exit 1
fi

# 3. Build da Galeria (:app)
echo "📱 Verificando App de Galeria..."
./gradlew :app:assembleDebug
if [ $? -ne 0 ]; then
    echo "❌ Erro ao compilar o App. Verifique os recursos."
    exit 1
fi

echo "✅ Projeto saudável e pronto para o trabalho!"
