import { Box, chakra, Flex, StackDivider, useColorModeValue, VStack } from '@chakra-ui/react';
import { UserData } from 'lib/types';
import React from 'react';
import styles from 'styles/nav-main.module.sass';

export const NavbarMain = (({ userData }) => {
  return (
    <VStack
      py={4}
      px={2}
      divider={
        <StackDivider
          as={chakra.hr}
          borderColor={useColorModeValue('gray.300', 'gray.500')}
          className={styles.headerStackDivider}
          my={6}
        />
      }
    >
      {/* TODO: Change title to an icon */}
      <Box w="100%">
        {userData.location && <MainText title="Location: " value={userData.location} />}
        {userData.company && <MainText title="Company: " value={userData.company} />}
      </Box>
    </VStack>
  );
}) as React.FC<{ userData: UserData }>;

export const MainText = (({ title, value }) => {
  const highlight = useColorModeValue('highlight', 'highlightDark');

  return (
    <Flex justifyContent="space-between" w="100%" transition="0.3s">
      <chakra.span fontWeight={500} color={highlight}>
        {title}
      </chakra.span>
      <chakra.span fontStyle="italic" color={useColorModeValue('gray.600', 'gray.400')}>
        {value}
      </chakra.span>
    </Flex>
  );
}) as React.FC<{ title: string; value: string }>;
