import { EventEmitter } from '../src';
import { TestEvents } from './common';

function useData() {
  return {
    random: Math.ceil(Math.random() * 4 + 1),
    randomBoolean: Math.random() < 0.5,
    emitter: new EventEmitter<TestEvents>(),
    foo: { count: 0 },
  };
}

describe('[EventEmitter]', () => {
  it('addListener() & on()', async () => {
    const { random, emitter, foo } = useData();

    for (let i = 0; i < random; i++) {
      emitter.addListener('chat', () => {
        foo.count++;
      });
      emitter.on('chat', () => {
        foo.count++;
      });
    }

    expect(emitter.listeners('chat').length).toBe(random * 2);

    await emitter.emit('chat', `foo`);

    expect(foo.count).toBe(random * 2);
  });

  it('emit()', async () => {
    const { random, emitter, foo } = useData();

    await emitter.emit('chat', 'foo');

    expect(foo.count).toBe(0);

    emitter.addListener('chat', () => {
      foo.count++;
    });

    for (let i = 0; i < random; i++) {
      await emitter.emit('chat', `${i}`);
    }

    expect(foo.count).toBe(random);
  });

  it('eventNames()', () => {
    const { emitter } = useData();

    expect(emitter.eventNames()).toEqual([]);

    emitter.on('chat', () => {});
    emitter.on('test', () => {});

    expect(emitter.eventNames()).toEqual(['chat', 'test']);
  });

  it('get & set maxListeners()', () => {
    const { emitter, random } = useData();

    // Default value
    expect(emitter.getMaxListeners()).toBe(10);

    // Invalid input
    expect(() => emitter.setMaxListeners(-1)).toThrow();
    expect(emitter.getMaxListeners()).toBe(10);

    // Custom input
    emitter.setMaxListeners(random);
    expect(emitter.getMaxListeners()).toBe(random);

    emitter.setMaxListeners(random);
    for (let i = 0; i < random; i++) {
      emitter.on('chat', () => {});
    }

    // When passing the limit, it's thrown an warn but add it anyway and return
    expect(emitter.on('chat', () => {})).toBe(emitter);
    expect(emitter.listenerCount('chat')).toBe(random + 1);
  });

  it('listenerCount() & listeners() & rawListeners()', () => {
    const { random, emitter } = useData();

    expect(emitter.rawListeners('chat').length).toBe(0);
    expect(emitter.listeners('chat').length).toBe(0);

    for (let i = 0; i < random; i++) {
      emitter.addListener('chat', () => {});
    }

    expect(emitter.listeners('chat').length).toBe(random);
    expect(emitter.listenerCount('chat')).toBe(random);

    // TODO: When the rawListeners work, test it here.
    // emitter.once('chat', () => {});
    // expect(emitter.listenerCount('chat')).toBe(random);
    // expect(emitter.rawListeners('chat').length).toBe(random + 1);
  });

  it('next()', async () => {
    const { emitter } = useData();

    process.nextTick(() => {
      emitter.emit('chat', 'foo');
    });
    const msg = await emitter.next('chat');
    expect(msg).toBe('foo');
  });

  it('removeListener() & off()', async () => {
    const { emitter, foo } = useData();

    const listener = () => {
      foo.count++;
    };

    emitter.removeListener('chat', listener);

    emitter.on('chat', listener);
    emitter.on('chat', listener);

    await emitter.emit('chat', 'foo');

    emitter.off('chat', listener);
    emitter.removeListener('chat', listener);

    await emitter.emit('chat', 'bar');

    expect(foo.count).toEqual(2);
  });

  it('once()', async () => {
    const { emitter, foo } = useData();

    // this shouldn't be executed
    await emitter.emit('chat', 'foo');

    // this should be executed
    emitter.once('chat', () => {
      foo.count++;
    });
    await emitter.emit('chat', 'foo');

    // this shouldn't be executed
    await emitter.emit('chat', 'foo');

    expect(foo.count).toEqual(1);
  });

  it('prependListener() & prependOnceListener()', async () => {
    const { emitter, foo } = useData();

    emitter.addListener('chat', () => {
      foo.count = 0;
    });

    emitter.prependListener('chat', () => {
      foo.count = 1;
    });

    await emitter.emit('chat', 'foo');

    expect(foo.count).toEqual(0);

    emitter.once('chat', () => {
      foo.count++;
    });
    emitter.prependOnceListener('chat', () => {
      foo.count++;
    });

    await emitter.emit('chat', 'foo');
    await emitter.emit('chat', 'foo');

    expect(foo.count).toEqual(0);
  });

  it('removeAllListeners()', async () => {
    const { emitter, foo, random } = useData();

    for (let i = 0; i < random; i++) {
      emitter.on('chat', () => {
        foo.count++;
      });
    }

    await emitter.emit('chat', 'foo');

    emitter.removeAllListeners('chat');

    await emitter.emit('chat', 'bar');

    expect(foo.count).toEqual(random);
  });
});
