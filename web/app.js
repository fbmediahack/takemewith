var express = require('express');
var app = express();
// Defaults to London
var lat = 51.507351; 
var lng = -0.127758;

app.get('/', function (req, res) {
  res.sendFile(__dirname+'/index.html');
});

app.get('/getLocation', function(req, res) {
  res.send({'lat' : lat, 'lng' : lng});
})

app.get('/putLocation/', function(req, res) {
  lat = req.query.lat;
  lng = req.query.lng;
  console.log('new lat lng: ' + lat + " " + lng);
  res.sendStatus(200);
});

app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});
