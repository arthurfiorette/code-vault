export type EmitterOptions = {
  maxListeners: number;
};

export function mergeOptions(options: Partial<EmitterOptions>): EmitterOptions {
  if ((options.maxListeners || 0) < 0) delete options.maxListeners;
  return {
    maxListeners: 10,
    ...options
  };
}
