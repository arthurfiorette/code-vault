const express = require('express');
const redis = require('redis');
const { Pool } = require('pg');
const app = express();

const pool = new Pool({
  user: process.env.DB_USER || 'postgres',
  host: process.env.DB_HOST || 'db',
  database: process.env.DB_NAME || 'sender'
});

const client = redis.createClient({
  port: 6379,
  host: process.env.REDIS_HOST || 'queue',
  db: 0
});

app.use(express.urlencoded({ extended: true }));

app.post('/', (req, res) => {
  const email = ({ title, content } = req.body);
  saveMessage(email);
  workMessage(email);
  res.status(201).send({ status: 'Enfileirada', message: email });
});

function saveMessage({ title, content }) {
  pool.connect((err, client, done) => {
    if (err) throw err;
    console.log('Savind email:', title);
    client
      .query('INSERT INTO emails (title, content) VALUES ($1, $2)', [title, content])
      .then(() => console.log('Email:', title, 'saved'))
      .catch((err) => console.log('Error on saving email:', title, err));
    done();
  });
}

function workMessage(email) {
  console.log('Pushing email:', email);
  client.rpush('sender', JSON.stringify(email));
}

app.listen(8080, () => console.log('Listening on port:', 8080));
