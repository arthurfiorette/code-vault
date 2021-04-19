import React from 'react';
import './Button.css';

function Button({ content = '', classes = '', click = (event) => {} }) {
  return (
    <button onMouseDown={() => click(content)} className={`button ${classes}`}>
      {content}
    </button>
  );
}

export default Button;
