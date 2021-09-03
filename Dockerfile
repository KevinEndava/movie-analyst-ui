FROM node:14-alpine

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY package*.json ./


RUN npm install express ejs superagent --save

ENV BACKEND_URL=10.0.2.3:3000


# Bundle app source
COPY . .

EXPOSE 3000

CMD [ "npm", "start" ]