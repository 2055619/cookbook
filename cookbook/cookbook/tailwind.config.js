/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'cook': 'var(--cook-color)',
        'cook-light': 'var(--cook-light-color)',
        'cook-yellow': 'var(--cook-yellow-color)',
        'cook-orange': 'var(--cook-orange-color)',
        'cook-red': 'var(--cook-red-color)',
        'cook-hover': 'var(--cook-hover-color)',
      },
    },
  },
  plugins: [],
}