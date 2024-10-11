FROM node:18-alpine
WORKDIR /inventario-frontend
COPY public/ /inventario-frontend/public
COPY src/ /inventario-frontend/src
COPY package.json /inventario-frontend/
RUN npm install
RUN export NODE_OPTIONS=--openssl-legacy-provider
CMD ["npm", "start"]