Please install with:

npm install

The package.json contains the only dependency, Express.

Start with:

node app.js

The server contains three endpoints:

- /pois/all : returns JSON of all pois. (GET)

- /poi/:id : returns the poi with the given ID. (GET)

- /poi/create : creates a new poi using the POST data sent to it. If no ID is included in the POST data, the next available ID will be allocated. If an ID is sent in the POST data, it will be checked. If a poi with that ID exists, it will be ignored; if not, it will be added. If added, the route responds with a JSON object containing the allocated ID.


The use of IDs might help avoid duplicate POIs within your app.
