var express = require('express'),
    ejs = require('ejs'),
    mysql = require('mysql');

var HOSTNAME = 'localhost';
var PORT = 8124;
var DB_NAME = 'typesafedevcontest';
var DB_USER = 'martin';
var DB_PASSWORD = 'odersky';

function createMySQLConnection() {
  var conn = mysql.createConnection({
    host     : HOSTNAME,
    database : DB_NAME,
    user     : DB_USER,
    password : DB_PASSWORD
  });
  conn.connect();
  return conn;
}

var app = express();
app.use(express.bodyParser());
app.engine('html', ejs.renderFile);

app.get('/', function(req, res) { 

  res.redirect(301, '/logs');
});

app.get('/logs', function(req, res) {

  var conn = createMySQLConnection();
  conn.query(
    'select * from logs order by created_at limit 10', 
    function(err, results) {
      try {
        if (err) {
          res.send(500, err);
        } else {
          res.render('logs.ejs', {locals: {logs: results}});
        }
      } finally {
        conn.end();
      } 
    }
  );
});

app.post('/logs', function(req, res) {

  if (!req.body.name || !req.body.line) {
    res.send(400);
    return;
  }

  var conn = createMySQLConnection();
  conn.query(
    'insert into logs (name, line) values (?, ?)',
    [req.body.name, req.body.line],
    function(err, result) {
      try {
        if (err) res.send(500, err);
        else res.send(200);
      } finally {
        try { conn.end(); } catch (e) {}
      }
    }
  );
});

app.post('/logs/async', function(req, res) {

  if (!req.body.name || !req.body.line) {
    res.send(400);
    return;
  }

  process.nextTick(function() {
    var conn = createMySQLConnection();
    conn.query(
      'insert into logs (name, line) values (?, ?)',
      [req.body.name, req.body.line],
      function(err, result) {
        try { conn.end(); } catch (e) {}
      }
    );
  });
  res.send(202);
});

app.listen(PORT, HOSTNAME);

