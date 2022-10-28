FROM node:16-alpine3.11 as frontend-build
WORKDIR /app
COPY frontend/package*.json ./
RUN npm install
COPY frontend ./
RUN npm run build

FROM node:14-alpine as chat-build
WORKDIR /app
COPY ./frontend-chat/package*.json ./
RUN npm install
COPY ./frontend-chat ./
RUN npm run build

FROM node:14-alpine as map-build
WORKDIR /app
COPY Mapbox/package*.json ./
RUN npm install
COPY ./Mapbox ./
RUN npm run build

FROM nginx
WORKDIR /app
COPY --from=frontend-build /app/dist ./frontend
COPY --from=chat-build /app/dist ./chat
COPY --from=map-build /app/dist ./map
COPY ./nginx/default.conf /etc/nginx/conf.d
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]