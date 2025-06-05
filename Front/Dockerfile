# Etapa 1: Construir a aplicação Angular
FROM node:22.12-alpine AS build

WORKDIR /app

COPY ./package.json ./package-lock.json ./
RUN npm install

COPY ./ ./
RUN npm run build --prod

# Etapa 2: Servir os arquivos estáticos
# Use official nginx image as the base image
FROM nginx:latest

# Copy the build output to replace the default nginx contents.
COPY --from=build /usr/local/app/dist/out /usr/share/nginx/html

# single page
# Copy the respective nginx configuration files
COPY nginx_config/nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
