import { chakra, useColorModeValue } from '@chakra-ui/react';
import React from 'react';
// @ts-expect-error
import ReactTypingEffect from 'react-typing-effect';

export const CodeEffect = (({ texts }) => {
  const code = (ending: boolean) => (
    <>
      {ending ? '</' : '<'}
      <chakra.span color={useColorModeValue('highlight', 'highlightDark')}>code</chakra.span>
      {'>'}
    </>
  );

  return (
    <>
      {code(false)}
      <chakra.span mx={2}>
        {'I build '}
        <chakra.span
          as={ReactTypingEffect}
          text={texts}
          typingDelay={500}
          eraseDelay={3000}
          eraseSpeed={100}
          speed={100}
        />
      </chakra.span>
      {code(true)}
    </>
  );
}) as React.FC<{ texts: string[] }>;
