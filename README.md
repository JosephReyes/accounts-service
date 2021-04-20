# Simple payment app BE
Java backend for an accounts app

## How to run
1. Clone BE repo https://github.com/JosephReyes/accounts-service
2. Clone FE repo https://github.com/JosephReyes/accounts-app
3. cd to BE folder and run GraphQL server `./gradlew bootRun`
4. cd to FE folder and install node modules `npm i`
5. Run React app `npm start`

## How to run tests
### Unit tests
1. In BE folder run `./gradlew test`

### Integration tests
1. Ensure GraphQL server is running
2. Run FE app
3. Run `npm run cy:run` or `npm run cy:open` to watch them in action

## What I would have done given more time?
* Implement the email functionality
* Add FE unit tests with Jest. I know there is some debate on how useful these are but I usually add them in
* Validation both on text fields on the FE and on the arguments we send to the BE
* Better feedback on successful actions e.g. successful transfer. They can see on the app that their balance has changed but a snackbar would be nice
