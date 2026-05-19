const express = require('express');
const path = require('path');
const app = express();
const port = 4200;

const angularAppPath = path.join(__dirname, 'dist/sakai-ng/browser');

// Servir archivos estáticos
app.use(express.static(angularAppPath));

// Redirigir cualquier ruta al index.html
app.get(/.*/, (req, res) => {
  res.sendFile(path.join(angularAppPath, 'index.html'));
});

app.listen(port, () => {
  console.log(`Angular servido en http://0.0.0.0:${port}`);
});

