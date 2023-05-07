const express = require('express');
const app = express();
const fs = require('fs').promises;

app.use(express.json());
app.use(express.urlencoded({extended: false}));

fs.readFile("pointsofinterest").then ( rest => {
    let itemObjs;
    try {
        itemObjs = rest.toString().split("\n").filter(itemObj => itemObj[0] == '{');
        console.log(itemObjs);
        itemObjs = itemObjs.map(JSON.parse);
    } catch(e) { 
        console.log(e);
    }



    // Retrieve all items
    app.get('/poi/all', (req, res) => {
        res.json(itemObjs.sort((a,b) => a.id < b.id ? -1 : 1));
    });

    // Retrieve item by ID
    app.get('/poi/:id', (req, res) => {
        res.json(itemObjs.filter ( obj => obj.id == req.params.id ));
    });

    // Create an item
    //
    // The item data should be passed as POST data.
    // If the JSON sent to the server has no ID, the next available ID will
    // be allocated.
    // If the JSON sent to the server contains an ID, it will be checked. If
    // that ID exists already, the item will not be added. If it does
    // not exist, the item will be added with that ID.
    //
    // Responds with a JSON object containing the allocated ID.
    app.post('/poi/create', (req, res) => {
        let id = 0, exists = false;
        if(!req.body.id) {
            const maxId = (itemObjs.sort( (a,b) =>  a.id < b.id ?1:-1 ))[0].id;
            id = maxId + 1;
        } else {
            id = req.body.id;
            exists = itemObjs.filter( obj => obj.id == req.body.id ).length > 0;
        }
        if(!exists) {
            const newobj = {
                id: id,
                name: req.body.name,
                type: req.body.type,
                description: req.body.description,
                lat: req.body.lat,
                lon: req.body.lon
            };
            itemObjs.push(newobj);
            res.json({id: id});
        } else {
            res.json({warning: "Not creating new item, as ID already exists."});
        }
    });
    
    app.listen(3000);
});
