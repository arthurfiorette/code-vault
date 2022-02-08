import { chakra, HStack, Icon, useColorModeValue } from '@chakra-ui/react';
import { Constants } from 'lib/constants';
import React from 'react';

export const NavbarFooter = (() => {
  const highlight = useColorModeValue('highlight', 'highlightDark');

  return (
    <HStack justifyContent="center" spacing={7} py={4}>
      {Constants.MEDIA.map(([icon, href]) => (
        <chakra.a href={href} target="_blank" key={href}>
          <Icon
            as={icon}
            h={6}
            w={6}
            fill={highlight}
            boxSizing="content-box"
            borderRadius="lg"
            borderWidth="1"
            transition="background-color 0.2s"
            p={2}
            _hover={{
              backgroundColor: useColorModeValue('gray.300', 'gray.600')
            }}
          />
        </chakra.a>
      ))}
    </HStack>
  );
}) as React.FC<{}>;
