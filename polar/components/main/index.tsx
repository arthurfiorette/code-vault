import { VStack } from '@chakra-ui/react';
import { chakra, useColorModeValue } from '@chakra-ui/system';
import { UserData } from 'lib/types';
import React from 'react';
// import { AboutMe } from './content/about';
import { MainBrand } from './content/brand';
import { Projects } from './content/projects';

export const Main = (({ userData }) => {
  const highlight = useColorModeValue('highlight', 'highlightDark');
  const bg = useColorModeValue('gray.200', 'gray.700');

  return (
    <VStack
      as={chakra.main}
      spacing={4}
      h="100%"
      bg={bg}
      borderRadius={5}
      p={4}
      className="noScrollbar"
      overflowY="scroll"
      sx={{
        '&::-webkit-scrollbar': {
          width: '0.25rem',
          background: 'transparent'
        },
        '&::-webkit-scrollbar-thumb': {
          backgroundColor: highlight,
          borderRadius: 'lg'
        }
      }}
    >
      <MainBrand />
      {/* <AboutMe /> */}
      <Projects userData={userData} />
    </VStack>
  );
}) as React.FC<{ userData: UserData }>;
