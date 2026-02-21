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

# Build Java options with environment variables
JAVA_OPTS="-Dserver.port=$PORT"
JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE"

# Only add DATABASE_URL if it's not empty
if [ -n "$DATABASE_URL" ]; then
  JAVA_OPTS="$JAVA_OPTS -Dspring.datasource.url=$DATABASE_URL"
fi

JAVA_OPTS="$JAVA_OPTS -Dspring.datasource.username=$DB_USERNAME"
JAVA_OPTS="$JAVA_OPTS -Dspring.datasource.password=$DB_PASSWORD"
JAVA_OPTS="$JAVA_OPTS -Dspring.ai.openai.api-key=$OPENAI_API_KEY"
JAVA_OPTS="$JAVA_OPTS -Dspring.ai.openai.base-url=$OPENAI_BASE_URL"

echo "Starting application with environment:"
echo "  PORT: $PORT"
echo "  SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"
if [ -n "$DATABASE_URL" ]; then
  echo "  DATABASE_URL: ${DATABASE_URL}" | cut -c1-60
else
  echo "  DATABASE_URL: (not set)"
fi
echo "  OPENAI_BASE_URL: $OPENAI_BASE_URL"

# Run Java application
exec java $JAVA_OPTS -jar lanjii-application/target/lanjii-application-3.0.0.jar "$@"
