import { ColorModeScript } from '@chakra-ui/react';
import NextDocument, { Head, Html, Main, NextScript } from 'next/document';
import theme from 'styles/theme';

class Document extends NextDocument {
  render() {
    return (
      <Html lang="en">
        <Head>
          <meta httpEquiv="Content-Language" content="en" />
        </Head>
        <body>
          <ColorModeScript initialColorMode={theme.config.initialColorMode} />
          <Main />
          <NextScript />
        </body>
      </Html>
    );
  }
}

export default Document;
