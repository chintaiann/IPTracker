import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SingleQuery from './pages/SingleQuery';
import BulkQuery from './pages/BulkQuery';
import ReverseLookup from './pages/ReverseLookup';
import Navbar from './component/Navbar';
import keycloak from './util/keycloak';
import { ReactKeycloakProvider } from "@react-keycloak/web";
import TestPage from './pages/TestPage'
import { ThemeProvider, createTheme } from '@mui/material/styles'; 


function App() {
  const eventLogger = (event, error) => {
    // console.log('onKeycloakEvent', event, error)
  }
  
  const tokenLogger = (tokens) => {
    // console.log('onKeycloakTokens', tokens)
    if (tokens.token) { 
      window.accessToken = tokens.token;
    }
  }

  //styling of all typography fonts
  //h4 is page title 
  const { palette } = createTheme();
  const { augmentColor } = palette;
  const createColor = (mainColor) => augmentColor({ color: { main: mainColor } });
  const theme = createTheme({
    palette: {
      anger: createColor('#F40B27'),
      apple: createColor('#5DBA40'),
      steelBlue: createColor('#5C76B7'),
      violet: createColor('#BC00A3'),
      navItem : createColor('#E0E0E1'),
    },
    typography: {
      h4: {
        fontFamily: 'Cairo'
      },
      button : { 
        fontFamily: 'Raleway',
      },
      box : {
        fontFamily: 'Raleway'
      }


  }});
  return (
    <ThemeProvider theme={theme}>
    <div className="home">
      <ReactKeycloakProvider 
        initOptions={{onLoad:"login-required",checkLoginIframe:false}}
        authClient={keycloak}
        onEvent={eventLogger}
        onTokens={tokenLogger}>
        
      <header>
      <Navbar/>
      </header>
      <Router>
        <Routes>
          <Route path='/' element={<SingleQuery/>}/>
          <Route path='/Single' element={<SingleQuery/>} />
          <Route path='/Bulk' element={<BulkQuery/>} />
          <Route path='/Reverse' element={<ReverseLookup/>} />
          <Route path='/test' element={<TestPage/>} />
        </Routes>
      </Router>

    </ReactKeycloakProvider>

    </div>
    </ThemeProvider>
  );
}

export default App;
