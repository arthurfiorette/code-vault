import { Box, Heading, useColorModeValue } from '@chakra-ui/react';
import React from 'react';

export const MainLayout = (({ title, children }) => {
  const highlight = useColorModeValue('highlight', 'highlightDark');

  return (
    <Box w="100%" p={4} bg={useColorModeValue('gray.100', 'gray.600')} borderRadius="lg">
      <Heading color={highlight} w="100%" textAlign="center" letterSpacing={2}>
        {title}
      </Heading>
      {children}
    </Box>
  );
}) as React.FC<{ title: string }>;
