(cd backend && ./mvnw package -DskipTests)
(cd email && ./mvnw package -DskipTests)
(cd equipment-chat && ./mvnw package -DskipTests)
([ ! -d "frontend/node_modules" ] && cd frontend && npm install)
([ ! -d "frontend-chat/node_modules" ] && cd frontend-chat && npm install)
docker-compose -f docker-compose-dev.yml up --build -d