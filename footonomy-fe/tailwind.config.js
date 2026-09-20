import { colors, fontFamily, fontSize, spacing, borderRadius } from './src/theme/tokens.js';

/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors,
      fontFamily,
      fontSize,
      spacing,
      borderRadius,
      maxWidth: {
        'max-width': spacing['max-width'],
      },
    },
  },
  plugins: [],
};
