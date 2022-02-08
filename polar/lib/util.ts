import { RestEndpointMethodTypes } from '@octokit/rest';
import TimeAgo from 'javascript-time-ago';
import en from 'javascript-time-ago/locale/en';
import { Unpacked, UserData } from './types';

export function extractProperties<T, K extends (keyof T)[]>(
  obj: T,
  properties: K
): Pick<T, Unpacked<K>> {
  const newObj = {} as T;
  for (let prop of properties) {
    newObj[prop] = obj[prop];
  }
  return newObj as Pick<T, Unpacked<K>>;
}

// time ago addDefaultLocale sucks
try {
  TimeAgo.addDefaultLocale(en);
} catch (err) {}
const timeAgo = new TimeAgo('en-US');

export function format(val: any) {
  return timeAgo.format(new Date(val));
}

export function cleanUserData(
  // Yeah, don't blame me.
  user: RestEndpointMethodTypes['users']['getByUsername']['response']['data'],
  repos: RestEndpointMethodTypes['repos']['listForUser']['response']['data']
): UserData {
  const cleanUser = extractProperties(user, [
    'name',
    'html_url',
    'bio',
    'avatar_url',
    'company',
    'location'
  ]);
  const cleanRepos = repos.map((r) =>
    extractProperties(r, [
      'name',
      'html_url',
      'description',
      'forks',
      'stargazers_count',
      'language',
      'full_name',
      'fork',
      'license',
      'homepage',
      'updated_at'
    ])
  );

  return {
    ...cleanUser,
    repos: cleanRepos
  };
}
