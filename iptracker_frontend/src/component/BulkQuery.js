import { useState } from "react";
import { Button } from "@mui/material";
import { TextField } from "@mui/material";
import {Typography,MenuItem }from "@mui/material";
import axios from "axios";
import Result from "./Result";
export default function BulkQuery() { 

    const [ip,setip] = useState('');
    const [split,setsplit] = useState([]);
    const [response,setResponse] = useState([]);
    let fileReader; 
    const handleBulkQuery=(event) => { 
        axios(
            {
                method:'GET', 
                url:`/bulkQuery/${ip}`,
            }
                ).then(function (response){ 
                    const split = ip.split(",")
                    setsplit(split);
                    setResponse(response.data);
                }
            )
    }

    const handleBulkUpload =(ipString) => { 
        axios(
            {
                method:'GET', 
                url:`/bulkQuery/${ipString}`,
            }
                ).then(function (response){ 
                    const split = ipString.split(",")
                    setsplit(split);
                    setResponse(response.data);
                }
            )
    }


    const handleFileRead = (e) => { 
        const content = fileReader.result; 
        var ipString = '';
        var lines = content.split('\n'); 
        for (var line=0;line<lines.length;line++){
            ipString+=lines[line]; 
            if (line !== lines.length-1) { 
                ipString+=','
            }
        console.log(ipString);
        }
        handleBulkUpload(ipString);
    }

    const handleFile = (file) => { 
        fileReader = new FileReader(); 
        fileReader.onloadend = handleFileRead;
        fileReader.readAsText(file);
    }

    return (
        <div>
            <TextField fullWidth onChange={e=>{setip(e.target.value)}}></TextField> 
            <Button onClick={e=>{handleBulkQuery()}}>Query in bulk</Button>
            <input type="file" onChange={(e)=>handleFile(e.target.files[0])}/>
            <ul>
                {
                    response.map( (data,i) => { 
                        return <Result key={split[i]} ip={split[i]} data={data} />
                    })
                }
            </ul>
        </div>
    )
}