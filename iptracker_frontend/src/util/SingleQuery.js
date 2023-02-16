import { useState } from "react";
import { Button } from "@mui/material";
import { TextField, MenuItem } from "@mui/material";
import axios from "axios";
import Result from "../component/Result";
import {isEmpty} from "lodash"
import { Typography } from "@mui/material";
import {protocols} from './constants'
import {ToastContainer,toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


export default function SingleQuery() { 
    const [ip,setip] = useState('');
    const [data,setData] = useState({}); 
    const [protocol,setProtocol] = useState('IPv4'); 
    const handleSubmitIP = ( event ) => { 
            axios(
                        {
                            method:'GET', 
                            url:`/singleQuery/${protocol}/${ip}`,
                        }
                            ).then(function (response){ 
                                setData(response.data.response);
                            }
                        ).catch (function (error){ 
                            toast.error(error.response.data.errorMessage);
                        })          
    }

    return (
        <div id="Page" className="Page">
            <TextField
                id="selectProtocol"
                select
                defaultValue="IPv4"
                helperText="Please select protocol."
                onChange={e=>{setProtocol(e.target.value)}}

            >
                {protocols.map((option)=> (
                    <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
                ))}
            </TextField>
            <Typography>Doing single querying for {protocol}</Typography>
            <TextField id="ipField" onChange={e=>setip(e.target.value)} />
            <Button label="singleQuerySubmit" id="singleQuerySubmit" variant="contained" onClick={e=>{handleSubmitIP()}}>Query IPv6/IPv4</Button> 
            {isEmpty(data) && <Typography>No results yet. Try a IP Address</Typography>}
            {!isEmpty(data) && <Result id="ipResult" data={data}/>}
            <ToastContainer id="Toast" />
        </div>
    ) 
}