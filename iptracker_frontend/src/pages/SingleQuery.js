import { useState } from "react";
import { Button } from "@mui/material";
import { TextField, MenuItem } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import { Typography } from "@mui/material";
import {protocols} from '../util/constants'
import {ToastContainer,toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ResultTable from "../component/ResultTable";
import axiosAuth from "../util/axiosAuth";
export default function SingleQuery() { 
    const [ip,setip] = useState('');
    const [data,setData] = useState([]); 
    const [protocol,setProtocol] = useState('IPv4'); 

    const handleSubmitIP = ( event ) => { 
        axiosAuth(
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
            <Typography style={{"marginTop":"20px"}} variant="h4">Single Query</Typography>
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

            <div className="submit">
            <TextField fullWidth onChange={e=>setip(e.target.value)} />
            </div>            
            <Button endIcon={<SearchIcon/>} color="steelBlue" size="medium" variant="contained" onClick={e=>{handleSubmitIP()}}>Search</Button> 


           
            <div className="resultTableContainer">
                {
                    (data.length === 0) 
                    ? <div></div>
                    : <ResultTable data={data}/>
                }
            </div>
            <ToastContainer id="Toast" />
        </div>
    ) 
}