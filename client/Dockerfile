FROM node:20 AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

ARG API_HOST
ARG API_PORT
ARG API_BASE
ARG API_PROTOCOL

# Definir variables de entorno para nuestro .env
ENV VITE_API_HOST=$API_HOST
ENV VITE_API_PORT=$API_PORT
ENV VITE_API_BASE=$API_BASE
# Definir variable de entorno para nuestro protocolo ninx
ENV VITE_API_PROTOCOL = $API_PROTOCOL

RUN npm run build

FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html

#Remplazamos el ninx que antes tenía por el nuevo que colocamos
COPY ./nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD [ "nginx", "-g", "daemon off;" ]
