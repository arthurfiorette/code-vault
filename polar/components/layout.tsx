import { Grid, GridItem } from '@chakra-ui/react';
import { UserData } from 'lib/types';
import React from 'react';
import { Navbar } from './navbar/index';

export const Layout = (({ children, userData }) => {
  return (
    <Grid
      h="100%"
      marginX="auto"
      maxW="110rem"
      templateRows="1fr 1fr"
      templateColumns="300px 1fr"
      p={4}
      gap={4}
    >
      <GridItem colSpan={{ base: 2, md: 1 }} rowSpan={{ base: 1, md: 2 }}>
        <Navbar userData={userData} />
      </GridItem>
      <GridItem colSpan={{ base: 2, md: 1 }} rowSpan={{ base: 1, md: 2 }}>
        {children}
      </GridItem>
    </Grid>
  );
}) as React.FC<{
  userData: UserData;
}>;
