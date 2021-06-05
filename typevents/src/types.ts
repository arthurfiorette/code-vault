export type EventListener<V> = (val: V) => void | Promise<void>;

export type EventMap<E> = {
  [K in keyof E]: undefined | EventListener<E[K]> | EventListener<E[K]>[];
};

export interface EmptyEventType extends EventType<EmptyEventType> {}
export type EventType<E> = {
  error: any;
  newListener: { type: keyof E; listener: EventListener<E[keyof E]> };
  removeListener: { type: keyof E; listener: EventListener<E[keyof E]> };
};
