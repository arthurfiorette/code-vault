const redis = require('redis');
const client = redis.createClient({ port: 6379, host: process.env.REDIS_HOST || 'queue', db: 0 });

client.on('ready', async () => {
  console.log('Worker ready!');
  work();
});

function work() {
  client.blpop('sender', 0, (err, data) => {
    if (err) throw err;
    const email = ({ title, content } = JSON.parse(data[1]));
    const delay = Math.random() * 4 + 3; // Random time between 3 and 7 seconds
    console.log('Sending email:', email);
    setTimeout(() => {
      console.log('Email sent:', email);
      work();
    }, delay * 1000);
  });
}