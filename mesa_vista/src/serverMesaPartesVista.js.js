const express = require('express');
const path = require('path');
const https = require('https');
const fs = require('fs');
const app = express();
const port = 4200;

const angularAppPath = path.join(__dirname, '..', 'dist', 'sakai-ng', 'browser');

const sslDir = path.join(__dirname, '..', 'ssl');
const sslOptions = {
  key: fs.readFileSync(path.join(sslDir, 'llaveprivada.key')),
  cert: fs.readFileSync(path.join(sslDir, 'sgd.emapat.com.pe.crt')),
  ca: fs.readFileSync(path.join(sslDir, 'ca_bundle.crt'))
};

// Servir archivos estáticos
app.use(express.static(angularAppPath));

// Redirigir cualquier ruta al index.html
app.get(/.*/, (req, res) => {
  res.sendFile(path.join(angularAppPath, 'index.html'));
});

https.createServer(sslOptions, app).listen(port, () => {
  console.log(`Angular servido en https://0.0.0.0:${port}`);
});

