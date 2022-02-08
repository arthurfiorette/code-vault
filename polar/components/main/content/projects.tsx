import { SimpleGrid, Text } from '@chakra-ui/layout';
import { useColorModeValue } from '@chakra-ui/react';
import { chakra } from '@chakra-ui/system';
import { UserData } from 'lib/types';
import React from 'react';
import '../main-layout';
import { MainLayout } from '../main-layout';
import { GitCard } from './card';

export const Projects = (({ userData }) => {
  const highlight = useColorModeValue('highlight', 'highlightDark');

  return (
    <MainLayout title="Open Projects">
      <Text p={2} color="gray.500" className="textParagraph">
        Here shows dynamically my latest 15 projects that i open sourced. You can find a
        more accurate list on my{' '}
        <chakra.a
          href={`${userData.html_url}?tab=repositories`}
          _hover={{ textDecoration: 'underline' }}
          color={highlight}
        >
          repositores
        </chakra.a>
        .
      </Text>
      <SimpleGrid mt={5} columns={{ base: 1, lg: 2 }} spacing={4}>
        {userData.repos
          // Get 15 latest projects
          .sort(
            (a, b) =>
              new Date(b.updated_at!).getTime() - new Date(a.updated_at!).getTime()
          )
          .slice(0, 15)
          .map((repo) => (
            <GitCard repoData={repo} key={repo.name} />
          ))}
      </SimpleGrid>
    </MainLayout>
  );
}) as React.FC<{ userData: UserData }>;
