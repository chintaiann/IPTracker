import { useState } from "react";
import { Button } from "@mui/material";
import { TextField, MenuItem } from "@mui/material";
import axios from "axios";
import Result from "../component/Result";
import {isEmpty} from "lodash"
import { Typography } from "@mui/material";
import {protocols} from '../util/constants'
import {ToastContainer,toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import SendIcon from '@mui/icons-material/Send';
import ResultTable from "../component/ResultTable";

export default function SingleQuery() { 
    const [ip,setip] = useState('');
    const [data,setData] = useState([]); 
    const [protocol,setProtocol] = useState('IPv4'); 

    const handleSubmitIP = ( event ) => { 
        axios(
            {
                method:'GET', 
                url:`/singleQuery/${protocol}/${ip}`,
            }
        ).then(function (response){ 
            const listo = []
            console.log(response.data.response)
            listo.push(response.data.response);
            setData(listo);
            // setData(response.data.response);
        }
        ).catch (function (error){ 
            toast.error(error.response.data.errorMessage);
        })          
    }
    return (
        <div id="Page" className="Page">
            <Typography sx={{textDecoration: 'underline', color:"#650000"}} variant="h4">Single Query</Typography>

            <div className="selectProtocol">
                <TextField
                id="selectProtocol"
                select
                defaultValue="IPv4"
                fullWidth
                onChange={e=>{setProtocol(e.target.value)}}
            >
                {protocols.map((option)=> (
                    <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
                ))}
            </TextField>
            </div>

            <TextField sx={{ input: { color:"#650000"} }} onChange={e=>setip(e.target.value)} />
            <Button  endIcon={<SendIcon />} size="medium" id="singleQuerySubmit" variant="contained" onClick={e=>{handleSubmitIP()}}>Query IPv6/IPv4</Button> 
            <ToastContainer id="Toast" />

            <ResultTable data={data} />
        </div>
    ) 
}