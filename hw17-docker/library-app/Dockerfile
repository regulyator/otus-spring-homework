FROM node:16.1.0-alpine AS build
WORKDIR /workdir
COPY package.json package-lock.json ./
RUN npm install
ENV PATH="./node_modules/.bin:$PATH"
ARG REACT_APP_API_URL
ENV REACT_APP_API_URL=${REACT_APP_API_URL}
COPY . ./
RUN npm run build

FROM nginx:1.21-alpine
COPY --from=build /workdir/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workdir/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]