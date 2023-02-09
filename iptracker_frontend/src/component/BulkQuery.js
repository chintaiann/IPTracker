import { useState } from "react";
import { Button } from "@mui/material";
import { TextField,MenuItem,Typography } from "@mui/material";
import axios from "axios";
import Result from "./Result";
import { protocols } from "../util/constants";
import {ToastContainer,toast} from 'react-toastify';
export default function BulkQuery() { 

    const [ip,setip] = useState('');
    const [response,setResponse] = useState([]);
    const [protocol,setProtocol] = useState('IPv4'); 
    const [jsonField,setjsonField] = useState(''); 
    let fileReader;

    const handleBulkQuery = (event) => {
        const ipList = ip.split(",");

        let formData = new FormData();
        formData.append('ipList',ipList);
        const config = { 
            headers : { 'content-type':'multipart/form-data'}
        }

        axios.post(
            `/bulkQuery/${protocol}` , 
            formData, 
            config
        ).then(response => {setResponse(response.data.response)} 
        ).catch ( error => toast.error(error.response.data.errorMessage));
    }

    const handleBulkUpload =(ipString) => {
        const ipList = ipString.split(",");
        let formData = new FormData();
        formData.append('ipList',ipList);
        const config = { 
            headers : { 'content-type':'multipart/form-data'}
        }
        axios.post(
            `/bulkQuery/${protocol}` , 
            formData, 
            config
        ).then(response =>  {setResponse(response.data.response)} 
        ).catch ( error => toast.error(error.response.data.errorMessage));
    }

    //read in uploaded text file and parse into 1 string of ip addresses to query 
    const handleFileRead = async (e) => { 
        const content = fileReader.result; 
        var ipString = '';
        var lines = content.split('\n'); 
        for (var line=0;line<lines.length;line++){
            ipString+=lines[line]; 
            if (line !== lines.length-1) { 
                ipString+=','
            }
        }
        handleBulkUpload(ipString); 
    }

    const handleFile = (file) => { 
        fileReader = new FileReader(); 
        fileReader.onloadend = handleFileRead;
        fileReader.readAsText(file);
    }

    //read in a JSON field. assumes IP is in the form of a list. 
    const handleJSONUpload = (e) => {
        if (jsonField.length === 0) { 
            toast("Please enter JSON field to read the IP address before uploading.")
            return null; 
        }

        const fileReader = new FileReader();
        fileReader.readAsText(e.target.files[0], "UTF-8");
        e.target.value="";
        fileReader.onload = e => {
          let json = JSON.parse(e.target.result); 
          if (!json.hasOwnProperty(jsonField)) { 
            toast("JSON field doesn't exist in the JSON file.");
            return null; 
          }

          const listOfIP = json[jsonField]; 
          var ipString = listOfIP.join(","); 
          console.log(ipString); 
          handleBulkUpload(ipString);
        };
    }

    return (
        <div className="Page">
            <TextField
                select
                defaultValue="IPv4"
                helperText="Please select protocol."
                onChange={e=>{setProtocol(e.target.value)}}
            >
                {protocols.map((option)=> (
                    <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
                ))}
            </TextField>
            <Typography>Doing bulk querying for {protocol}</Typography>
            <TextField fullWidth onChange={e=>{setip(e.target.value)}}></TextField> 
            <Button onClick={e=>{handleBulkQuery()}}>Query in bulk</Button>
            <Typography>Upload TXT File </Typography>
            <input type="file" onChange={ (e)=>{handleFile(e.target.files[0]);e.target.value=""} }/>
            <TextField label="JSON Field" onChange={e=>{setjsonField(e.target.value);}}></TextField> 

            <Typography>Upload JSON File </Typography>
            <input type="file" onChange={handleJSONUpload}/>

            <ul>
                {
                    response.map( (data,i) => { 
                        return <Result data={data} />
                    })
                }
            </ul>
            <ToastContainer/>
        </div>
    )
}