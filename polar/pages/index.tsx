import { Octokit } from '@octokit/rest';
import { Layout } from 'components/layout';
import { Main } from 'components/main';
import { Constants } from 'lib/constants';
import { UserData } from 'lib/types';
import { cleanUserData } from 'lib/util';
import { GetStaticProps } from 'next';
import React from 'react';

const Index = (({ userData }) => {
  return (
    <Layout userData={userData}>
      <Main userData={userData} />
    </Layout>
  );
}) as React.FC<IndexProps>;

type IndexProps = {
  userData: UserData;
};

export default Index;

export const getStaticProps: GetStaticProps<IndexProps> = async () => {
  const octokit = new Octokit({ auth: process.env.GITHUB_API_TOKEN });

  const username = { username: Constants.GITHUB_USERNAME };

  const user = await octokit.users.getByUsername(username);
  const repos = await octokit.repos.listForUser(username);

  return {
    props: {
      // Extract only used properties to decrease the data sent to the client
      userData: cleanUserData(user.data, repos.data)
    },
    revalidate: 60 * 5 // 5 Minutes
  };
};
