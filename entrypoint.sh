#!/bin/bash
# Entrypoint script for Java application with environment variable support

# Default values
PORT=${PORT:-8080}
SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-demo}
DATABASE_URL=${DATABASE_URL:-}
DB_USERNAME=${DB_USERNAME:-postgres}
DB_PASSWORD=${DB_PASSWORD:-postgres}
OPENAI_API_KEY=${OPENAI_API_KEY:-sk-demo}
OPENAI_BASE_URL=${OPENAI_BASE_URL:-https://api.openai.com/v1}

# Parse DATABASE_URL from Render format (postgresql://user:pass@host:port/db)
# to JDBC format (jdbc:postgresql://host:port/db)
if [[ "$DATABASE_URL" =~ ^postgresql:// ]]; then
  # Remove postgresql:// prefix
  db_url_without_scheme="${DATABASE_URL#postgresql://}"
  
  # Extract database name (everything after the last /)
  db_name="${db_url_without_scheme##*/}"
  
  # Extract user:pass@host:port (everything before the last /)
  db_host_part="${db_url_without_scheme%/*}"
  
  # Extract host:port (everything after @)
  db_host_port="${db_host_part##*@}"
  
  # Extract user:pass (everything before @)
  db_user_pass="${db_host_part%%@*}"
  
  # Extract username (everything before :)
  DB_USERNAME="${db_user_pass%%:*}"
  
  # Extract password (everything after :)
  DB_PASSWORD="${db_user_pass#*:}"
  
  # Construct proper JDBC URL
  DATABASE_URL="jdbc:postgresql://${db_host_port}/${db_name}"
fi

echo "========================================="
echo "Starting Lanjii Application"
echo "========================================="
echo "PORT=$PORT"
echo "SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE"
echo "DATABASE_URL=${DATABASE_URL:0:60}..."
echo "DB_USERNAME=$DB_USERNAME"
echo "OPENAI_BASE_URL=$OPENAI_BASE_URL"
echo "========================================="
echo ""

# Export environment variables so Spring Boot can pick them up
export SERVER_PORT=$PORT
export SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE
export SPRING_DATASOURCE_URL=$DATABASE_URL
export SPRING_DATASOURCE_USERNAME=$DB_USERNAME
export SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD
export SPRING_AI_OPENAI_API_KEY=$OPENAI_API_KEY
export SPRING_AI_OPENAI_BASE_URL=$OPENAI_BASE_URL

# Run Java application
exec java -jar /app/lanjii-application/target/lanjii-application-3.0.0.jar
