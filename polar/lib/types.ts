import Icons from 'react-icons/fa';

export type Unpacked<T> = T extends (infer U)[] ? U : T;

export type RepoData = {
  name: string;
  html_url: string;
  full_name: string;
  description: string | null;
  language?: string | null;
  forks?: number;
  stargazers_count?: number;
  license?: any;
  homepage?: string | null;
  fork: boolean;
  updated_at?: string | null;
};

export type UserData = {
  name: string;
  bio: string;
  avatar_url: string;
  html_url: string;
  company: string;
  location: string;
  repos: RepoData[];
};

export type ConstantsType = {
  GITHUB_USERNAME: string;
  MEDIA: [(typeof Icons)[keyof typeof Icons], string][];
  WHAT_I_DO: string[];
  BRAND_URL: string;
  ABOUT: string[];
};
