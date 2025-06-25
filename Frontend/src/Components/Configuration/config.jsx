// // config.js

// const config = {
//     // Define your dynamic ports here
//     ports: process.env.REACT_APP_API_PORTS
//       ? process.env.REACT_APP_API_PORTS.split(',').map(port => parseInt(port))
//       : [8081, 8082, 8083, 8084, 8085, 8086],
//   };
  
//   export default config;
  
// config.js

const config = {
  // Define your API gateway port here
  port: 9999,
};

export default config;
