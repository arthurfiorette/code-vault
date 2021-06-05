# Field Verifier

This package was an **typed** event system for **Node.js**, but was merged into typed-core.

```ts
import { EmitterEvents, EventEmitter } from 'typevents';

interface MyEvents extends EmitterEvents<MyEvents> {
  login: { username: string; password: string };
}

const emitter = new EventEmitter<MyEvents>();

emitter.on('login', event => {
  // Typeof event === { username: string; password: string }
  console.log(`A new user has joined! Say hello to '${event.username}'`);
});

// @ts-expect-error
// Throws a type assertion error because we don't know the event `unknown-event`
emitter.on('unknown-event', event => {
  console.log('I do not know that event!');
});

// @ts-expect-error
// Throws a type assertion error because the `login` event exists and this is not its type.
emitter.emit('login', { unknownType: true });

// Prints: "A new user has joined! Say hello to 'typevents'"
emitter.emit('login', { username: 'typevents', password: 'typevents' });
```