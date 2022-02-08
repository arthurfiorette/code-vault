import { Text } from '@chakra-ui/react';
import { Constants } from 'lib/constants';
import React from 'react';
import { MainLayout } from '../main-layout';

export const AboutMe = (({}) => {
  return (
    <MainLayout title="About">
      {Constants.ABOUT.map((p, i) => (
        <Text p={2} color="gray.500" key={i} className="textParagraph">
          {p}
        </Text>
      ))}
    </MainLayout>
  );
}) as React.FC<{}>;
