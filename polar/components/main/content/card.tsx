import { chakra, Flex, Icon, Text, useColorModeValue } from '@chakra-ui/react';
import { RepoData } from 'lib/types';
import { format } from 'lib/util';
import React from 'react';
import { AiOutlineFork, AiOutlineStar } from 'react-icons/ai';

export const GitCard = (({ repoData }) => {
  const bg = useColorModeValue('gray.200', 'gray.700');
  const highlight = useColorModeValue('highlight', 'highlightDark');

  return (
    <Flex p={4} borderRadius="lg" bg={bg} direction="column" fontSize="0.75rem">
      <Flex>
        <chakra.a
          href={repoData.html_url}
          fontSize="1rem"
          fontWeight="700"
          _hover={{ textDecoration: 'underline' }}
          color={highlight}
        >
          {repoData.name}
        </chakra.a>
        {repoData.updated_at && (
          <Text ms="auto" color="gray.500">
            {format(repoData.updated_at)}
          </Text>
        )}
      </Flex>
      {repoData.fork && <Text></Text>}
      <Text mt={3} mb={4} h="100%" color="gray.500">
        {repoData.description}
      </Text>
      <Flex color="gray.500">
        {repoData.language && <chakra.div mr={3}>{repoData.language}</chakra.div>}

        <chakra.a href={`${repoData.html_url}/stargazers`} mr={3} _hover={{ color: highlight }}>
          <Icon as={AiOutlineStar} h={4} w={4} mr={1} />
          {repoData.stargazers_count}
        </chakra.a>

        <chakra.a href={`${repoData.html_url}/network`} _hover={{ color: highlight }}>
          <Icon as={AiOutlineFork} h={4} w={4} mr={1} />
          {repoData.forks}
        </chakra.a>

        {repoData.license && <Text ms="auto">{repoData.license.spdx_id}</Text>}
      </Flex>
    </Flex>
  );
}) as React.FC<{ repoData: RepoData }>;
