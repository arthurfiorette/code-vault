import { EventEmitter } from '../src/index';
import { mergeOptions } from '../src/options';

describe('check all possible emitter options', () => {
  it('should create an emitter with default options', () => {
    expect(new EventEmitter(mergeOptions({}))).toEqual(new EventEmitter());
  });
});
