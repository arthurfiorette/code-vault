import React from 'react';
import ReactDOM from 'react-dom';

import './index.css';
import Calculator from './main/Calculator';

const root = document.getElementById('root');

const element = (
  <div>
    <h1>Calculator</h1>
    <Calculator />
  </div>
);

ReactDOM.render(element, root);
