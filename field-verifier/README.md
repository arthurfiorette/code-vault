# Field Verifier

This package was an **typed** object validator for **Node.js**, but was merged into typed-core.

```js
import { validate } from 'field-verifier';

const input = {
  name: {
    firstName: 'John',
    lastName: { first: 'Smith', second: '' },
  },
  age: 12,
};

const errors = await validate(input, ({ parse, validate }) => {
  parse('age', (x) => x > 1, 'a person cannot have an age lower than 1 year');

  validate('name', ({ parse, validate }) => {
    parse('firstName', (n) => n.length > 3, 'at least 3 characters length');

    validate('lastName', ({ parse }) => {
      // Rejects every condition
      parse('first', () => false, 'you got rejected :P');
    });
  });
});
```
