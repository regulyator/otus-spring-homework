FROM node:16.1.0-alpine AS build
WORKDIR /workdir
COPY package.json package-lock.json ./
RUN npm install
ENV PATH="./node_modules/.bin:$PATH"
COPY . ./
RUN npm run build

FROM nginx:1.20-alpine
COPY --from=build /workdir/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]