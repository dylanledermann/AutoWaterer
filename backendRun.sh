#!/bin/bash
# === CONFIGURATION ===
NGINX_PORT=443
NGROK_PORT=$NGINX_PORT          # ngrok forwards nginx listener
# === START SPRING BOOT APP ===
echo "Starting Spring Boot application"
cd ./autowatererbackend
nohup ./mvnw spring-boot:run > spring.log 2>&1 &
SPRING_PID=$!
echo "Spring Boot app started with PID $SPRING_PID"

# === START NGINX ===
echo "Starting nginx..."
sudo nginx -s stop >/dev/null 2>&1   # stop existing instance
sudo nginx                           # start fresh
echo "nginx started on port $NGINX_PORT"

# === START NGROK ===
echo "Starting ngrok tunnel on port $NGROK_PORT..."
nohup ngrok http $NGROK_PORT > ngrok.log 2>&1 &
NGROK_PID=$!
echo "ngrok started with PID $NGROK_PID"

# === GET NGROK URL ===
sleep 3
NGROK_URL=$(curl --silent http://127.0.0.1:4040/api/tunnels \
  | grep -o '"public_url":"https:[^"]*' | cut -d'"' -f4)

echo "Your app is now publicly available at: $NGROK_URL"

# === CLEANUP ON EXIT ===
trap "echo 'Stopping processes...'; kill $SPRING_PID $NGROK_PID; sudo nginx -s stop; exit 0" SIGINT SIGTERM

# Keep script alive so trap works
wait