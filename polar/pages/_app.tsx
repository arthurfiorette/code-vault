import { ChakraProvider } from '@chakra-ui/react';
import { AppProps } from 'next/app';
import React from 'react';
import 'styles/global.sass';
import theme from 'styles/theme';

export const App = (({ Component, pageProps }) => {
  return (
    <ChakraProvider theme={theme} resetCSS>
      <Component {...pageProps} />
    </ChakraProvider>
  );
}) as React.FC<AppProps>;

export default App;
