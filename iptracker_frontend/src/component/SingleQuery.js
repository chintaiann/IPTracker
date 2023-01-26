import { useState } from "react";
import { Button } from "@mui/material";
import { TextField } from "@mui/material";
import axios from "axios";
import Result from "./Result";
import {isEmpty} from "lodash"
import { Typography } from "@mui/material";

export default function SingleQuery() { 
    const [ip,setip] = useState('');
    const [data,setData] = useState({}); 
    const handleSubmitIP =( event ) => { 
        axios(
            {
                method:'GET', 
                url:`/singleQuery/${ip}`,
            }
                ).then(function (response){ 
                    setData(response.data);
                }
            )
    }


    return (
        <div>
            <TextField onChange={e=>setip(e.target.value)} />
            <Button variant="contained" onClick={e=>{handleSubmitIP()}}>Query IPv6/IPv4</Button> 
            <h1>{ip}</h1>

            {isEmpty(data) && <Typography>No results yet. Try a IP Address</Typography>}
            {!isEmpty(data) && <Result ip={ip} data={data}/>}
        </div>
    ) 
}