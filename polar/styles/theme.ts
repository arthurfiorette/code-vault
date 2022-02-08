import { ChakraTheme, extendTheme } from '@chakra-ui/react';

export default extendTheme({
  config: {
    initialColorMode: 'light',
    useSystemColorMode: false
  },
  colors: {
    highlightDark: '#eee',
    highlight: '#f44034'
  }

  // Add type checking that for some reason isn't working.
} as Partial<ChakraTheme>);
