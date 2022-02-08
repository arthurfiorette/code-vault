import {
  Avatar,
  AvatarBadge,
  Heading,
  Icon,
  Text,
  useColorMode,
  useColorModeValue,
  VStack
} from '@chakra-ui/react';
import { UserData } from 'lib/types';
import React from 'react';
import { FaMoon, FaSun } from 'react-icons/fa';

export const NavbarHeader = (({ userData }) => {
  const { toggleColorMode } = useColorMode();
  const highlight = useColorModeValue('highlight', 'highlightDark');

  return (
    <VStack spacing={3} py={6} position="relative">
      <Icon
        as={useColorModeValue(FaMoon, FaSun)}
        onClick={toggleColorMode}
        position="absolute"
        color={highlight}
        right="0"
        w={6}
        h={6}
        transition="0.4s"
        boxSizing="content-box"
        borderRadius="lg"
        borderWidth="1"
        p={2}
        _hover={{ backgroundColor: useColorModeValue('gray.300', 'gray.600') }}
      />
      <Avatar size="xl" src={userData.avatar_url} borderColor={highlight} borderWidth={1}>
        <AvatarBadge
          boxSize="1.8rem"
          borderColor={useColorModeValue('gray.200', 'gray.700')}
          bg={highlight}
        />
      </Avatar>
      <Heading size="sm" color={highlight} transition="0.2s">
        {userData.name}
      </Heading>
      <Text textAlign="center" color="gray.500" whiteSpace="pre-wrap">
        {userData.bio}
      </Text>
    </VStack>
  );
}) as React.FC<{ userData: UserData }>;
