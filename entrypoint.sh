#!/bin/sh
# Entrypoint script for Java application with environment variable support

# Default values
PORT=${PORT:-8080}
SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-demo}
DATABASE_URL=${DATABASE_URL:-}
DB_USERNAME=${DB_USERNAME:-postgres}
DB_PASSWORD=${DB_PASSWORD:-postgres}
OPENAI_API_KEY=${OPENAI_API_KEY:-sk-demo}
OPENAI_BASE_URL=${OPENAI_BASE_URL:-https://api.openai.com/v1}

# Build array of Java options
set --

# Server and Spring config
set -- "$@" "-Dserver.port=$PORT"
set -- "$@" "-Dspring.profiles.active=$SPRING_PROFILES_ACTIVE"

# Database config - only add DATABASE_URL if set
if [ -n "$DATABASE_URL" ]; then
  set -- "$@" "-Dspring.datasource.url=$DATABASE_URL"
fi

set -- "$@" "-Dspring.datasource.username=$DB_USERNAME"
set -- "$@" "-Dspring.datasource.password=$DB_PASSWORD"

# AI config
set -- "$@" "-Dspring.ai.openai.api-key=$OPENAI_API_KEY"
set -- "$@" "-Dspring.ai.openai.base-url=$OPENAI_BASE_URL"

# Logging
echo "Starting application with:"
echo "  PORT=$PORT"
echo "  SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE"
if [ -n "$DATABASE_URL" ]; then
  db_preview=$(echo "$DATABASE_URL" | cut -c1-50)
  echo "  DATABASE_URL=${db_preview}..."
fi
echo "  OPENAI_BASE_URL=$OPENAI_BASE_URL"
echo ""

# Run Java application
exec java "$@" -jar /app/lanjii-application/target/lanjii-application-3.0.0.jar
