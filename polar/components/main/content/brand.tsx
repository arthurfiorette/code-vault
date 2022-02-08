import { Box } from '@chakra-ui/react';
import { Constants } from 'lib/constants';
import React from 'react';
import { CodeEffect } from '../code-effect';
import { MainLayout } from '../main-layout';

export const MainBrand = (({}) => {
  return (
    <MainLayout title="Discover my open world!">
      <Box
        mt={6}
        fontFamily="consolas, monospace"
        fontWeight="bold"
        p={1}
        color="gray.500"
        w="100%"
        textAlign="center"
      >
        <CodeEffect texts={Constants.WHAT_I_DO} />
      </Box>
    </MainLayout>
  );
}) as React.FC<{}>;
