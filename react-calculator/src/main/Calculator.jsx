import React, { Component } from 'react';
import './Calculator.css';

import Button from '../components/Button';
import Display from '../components/Display';

const initialState = {
  display: '0',
  clearDisplay: false,
  operation: null,
  values: [0, 0],
  index: 0
};

class Calculator extends Component {
  state = { ...initialState };

  constructor(props) {
    super(props);

    this.clearMemory = this.clearMemory.bind(this);
    this.setOperation = this.setOperation.bind(this);
    this.addDigit = this.addDigit.bind(this);
  }

  clearMemory() {
    this.setState({ ...initialState });
  }

  setOperation(op) {
    const { state } = this;

    if (state.index === 0) {
      state.operation = op;
      state.index = 1;
      state.clearDisplay = true;
    } else {
      try {
        // eslint-disable-next-line
        state.values[0] = eval(`${state.values[0]} ${state.operation} ${state.values[1]}`);
      } catch (_) {
        /* just ignore */
      }

      state.values[1] = 0;

      const equals = state.operation === '=';

      state.display = state.values[0];
      state.operation = equals ? null : op;
      state.index = equals ? 0 : 1;
      state.clearDisplay = !equals;
    }

    this.setState(state);
  }

  addDigit(digit) {
    const { state } = this;

    if (digit === '.' && state.display.includes('.')) {
      return;
    }

    if (state.display === '0' || state.clearDisplay) {
      state.display = '';
      state.clearDisplay = false;
    }

    state.display += digit;

    if (digit !== '.') {
      const i = state.index;
      const number = parseFloat(state.display);
      state.values[i] = number;
    }

    this.setState(state);
  }

  render() {
    const {
      state: { display },
      clearMemory,
      setOperation,
      addDigit
    } = this;

    return (
      <div className="calculator">
        <Display value={display} />
        <Button content="AC" classes="triple topLeft" click={clearMemory} />
        <Button content="/" classes="operation topRight" click={setOperation} />
        <Button content="7" classes="" click={addDigit} />
        <Button content="8" classes="" click={addDigit} />
        <Button content="9" classes="" click={addDigit} />
        <Button content="*" classes="operation" click={setOperation} />
        <Button content="4" classes="" click={addDigit} />
        <Button content="5" classes="" click={addDigit} />
        <Button content="6" classes="" click={addDigit} />
        <Button content="-" classes="operation" click={setOperation} />
        <Button content="1" classes="" click={addDigit} />
        <Button content="2" classes="" click={addDigit} />
        <Button content="3" classes="" click={addDigit} />
        <Button content="+" classes="operation" click={setOperation} />
        <Button content="0" classes="double bottomLeft" click={addDigit} />
        <Button content="." classes="" click={addDigit} />
        <Button content="=" classes="operation bottomRight" click={setOperation} />
      </div>
    );
  }
}

export default Calculator;
