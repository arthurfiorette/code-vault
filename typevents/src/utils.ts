import { EventEmitter } from './emitter';

export function addCatch(emitter: EventEmitter<any>, promise: Promise<any>): void {
  promise.then(undefined, (err) => {
    // The callback is called with nextTick to avoid a follow-up
    // rejection from this promise.
    process.nextTick(emitter.emit, 'error', err);
  });
}
