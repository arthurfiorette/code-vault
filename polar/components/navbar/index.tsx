import { Box, chakra, useColorModeValue } from '@chakra-ui/react';
import { UserData } from 'lib/types';
import React from 'react';
import { NavbarFooter } from './nav-footer';
import { NavbarHeader } from './nav-header';
import { NavbarMain } from './nav-main';

export const Navbar = (({ userData }) => {
  const bgIn = useColorModeValue('gray.100', 'gray.600');
  const bgOut = useColorModeValue('gray.200', 'gray.700');
  const highlight = useColorModeValue('highlight', 'highlightDark');

  return (
    <chakra.nav
      h="100%"
      bg={bgOut}
      display="flex"
      flexDirection="column"
      borderRadius="lg"
      overflowY="scroll"
      sx={{
        '&::-webkit-scrollbar': { width: 1, background: 'transparent' },
        '&::-webkit-scrollbar-thumb': { backgroundColor: highlight, borderRadius: 'lg' }
      }}
    >
      <Box paddingX={3}>
        <NavbarHeader userData={userData} />
      </Box>
      <Box bg={bgIn} h="100%" paddingX={3} borderRadius="lg" ms={4} mr={3}>
        <NavbarMain userData={userData} />
      </Box>
      <Box paddingX={3} mt="auto">
        <NavbarFooter />
      </Box>
    </chakra.nav>
  );
}) as React.FC<{
  userData: UserData;
}>;
