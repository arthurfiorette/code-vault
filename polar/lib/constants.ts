import { FaGithub, FaLinkedin, FaTwitch, FaTwitter } from 'react-icons/fa';
import { ConstantsType } from './types';

export const Constants: ConstantsType = {
  GITHUB_USERNAME: 'arthurfiorette',
  MEDIA: [
    [FaLinkedin, 'https://www.linkedin.com/in/arthurfiorette/'],
    [FaGithub, 'https://github.com/ArthurFiorette/'],
    [FaTwitter, 'https://twitter.com/ArthurFiorette/'],
    [FaTwitch, 'https://www.twitch.tv/hazork_/']
  ],
  WHAT_I_DO: [
    'web interfaces.',
    'full stack applications.',
    'restful services.',
    'integration tools.'
  ],
  BRAND_URL: 'https://bslthemes.site/arter/light/wp-content/uploads/2020/09/bg.jpg',
  ABOUT: [
    `Hello! I'm a ${new Date().getFullYear() - 2005} year old guy from Pedra
    Azul, A small town in ES, Brazil. My love for programming and tech started
    back when I played Minecraft and tried to code my first plugin. Since
    so I started my interest in doing anything about technology in my life.
    In the beginning of the 2020 global pandemic, I started to learn code and
    since then it has never stopped.`,
    `I think the open source world is fantastic and I'm looking to find 
    my way to contribute to it. In the meantime, I'm focused on learning
    everything I can related to Javascript, Java, and Typescript development.`
  ].map((p) => p.split(/\n|\s\s/).join(' '))
};
