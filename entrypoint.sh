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

# Convert DATABASE_URL from Render format (postgresql://user:pass@host:port/db)
# to JDBC format (jdbc:postgresql://host:port/db)
if [ -n "$DATABASE_URL" ]; then
  # Check if it's in the Render format (postgresql://...)
  if echo "$DATABASE_URL" | grep -q "^postgresql://"; then
    # Extract components from postgresql://user:pass@host:port/db format
    # Remove the postgresql:// prefix
    db_url_without_scheme=$(echo "$DATABASE_URL" | sed 's|^postgresql://||')
    
    # Extract database name (everything after the last /)
    db_name=$(echo "$db_url_without_scheme" | sed 's|^.*\/||')
    
    # Extract user:pass@host:port (everything before the last /)
    db_host_part=$(echo "$db_url_without_scheme" | sed 's|\/[^/]*$||')
    
    # Extract host:port (everything after @)
    db_host_port=$(echo "$db_host_part" | sed 's|^.*@||')
    
    # Extract user:pass (everything before @)
    db_user_pass=$(echo "$db_host_part" | sed 's|@.*$||')
    
    # Extract username (everything before :)
    extracted_user=$(echo "$db_user_pass" | cut -d: -f1)
    
    # Extract password (everything after :)
    extracted_pass=$(echo "$db_user_pass" | cut -d: -f2-)
    
    # Construct proper JDBC URL
    DATABASE_URL="jdbc:postgresql://${db_host_port}/${db_name}"
    DB_USERNAME=$extracted_user
    DB_PASSWORD=$extracted_pass
  elif ! echo "$DATABASE_URL" | grep -q "^jdbc:"; then
    # If no scheme, add jdbc: prefix
    DATABASE_URL="jdbc:$DATABASE_URL"
  fi
fi

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
  db_preview=$(echo "$DATABASE_URL" | cut -c1-60)
  echo "  DATABASE_URL=${db_preview}..."
fi
echo "  DB_USERNAME=$DB_USERNAME"
echo "  OPENAI_BASE_URL=$OPENAI_BASE_URL"
echo ""

# Run Java application
exec java "$@" -jar /app/lanjii-application/target/lanjii-application-3.0.0.jar
