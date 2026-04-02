# Brug en let webserver som base-image
FROM nginx:alpine

WORKDIR /usr/share/nginx/html

# Kopier dit frontend-indhold til den korrekte mappe i NGINX-containeren
COPY . .

# Eksponér den port, som din frontend kører på
EXPOSE 80

CMD [ "nginx", "-g", "daemon off;" ]