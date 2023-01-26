import './App.css';
import TextField from '@mui/material/TextField';
import { Button } from '@mui/material';
import { useState } from 'react';
import axios from 'axios'
import GreyNoise from './component/GreyNoise';
import Query from './pages/Query';

function App() {

  // const [ip,setip] = useState('');
  // const [greyNoiseResult,setGreyNoiseResult] = useState([]);
  // const [greyNoise,setGreyNoise] = useState(false);
  // const [file, setFile] = useState();

  // const handleSubmit = (event) => { 
  //   axios(
  //     {
  //       method:'GET', 
  //       url:`/greynoise/${ip}`,
  //     }
  //   ).then(function (response){ 
  //     console.log(response);
  //     if (response.status === 200) {
  //       if (response.data.message === "Success"){ 
  //         setGreyNoiseResult([response.data.classification,response.data.name]); 
  //       }
  //       else{ 
  //         setGreyNoiseResult([response.data.message]); 
  //       }
  //     }
  //     else { 
  //       setGreyNoiseResult(["Something went wrong."]);
  //     }
  //     setGreyNoise(true)
  //   });
  // }



  return (
    <div className="App">
      <Query/>
    </div>
  );
}

export default App;
