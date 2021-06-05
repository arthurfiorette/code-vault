import { EventType } from '../src';

export interface TestEvents extends EventType<TestEvents> {
  chat: string;
  test: any;
}
