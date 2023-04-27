import { useState, useEffect } from "react";
import { Button } from "@mui/material";
import { TextField, MenuItem } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import { Typography } from "@mui/material";
import {protocols} from '../util/constants'
import {ToastContainer,toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ResultTable from "../tables/ResultTable";
import axiosAuth from "../util/axiosAuth";
import SourceView from "../component/SourceView";
import { sources } from "../util/constants";
import GreynoiseResult from "../tables/GreynoiseResult"
import React from "react";
export default function SingleQuery() { 
    const [ip,setip] = useState('');
    const [data,setData] = useState([]); 
    const [protocol,setProtocol] = useState('IPv4'); 
    const [source,setSource] = useState(sources[0])
    const [didRender, setDidRender]= useState(false);

    const handleSubmitIP = ( event ) => { 
        console.log("Single IP Query")
        setData([])
        axiosAuth(
                {
                    method:'GET', 
                    url:`/singleQuery/${protocol}/${source}/${ip}`,
                }
            ).then(function (response){ 
                const listo = []
                listo.push(response.data.response);
                setData(listo);
            }
            ).catch (function (error){ 
                toast.error(error.response.data.errorMessage);
            })   
    }
    

    // useEffect(handleSubmitIP,[source]);





    useEffect(()=>{
        setDidRender(true);
    },[]);

    useEffect( () => { 
        setip('')
        setData([])
    },[protocol])

    useEffect(()=>{
        if(didRender){
            handleSubmitIP()
        }
    },[source]);

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

            <SourceView changeSource={setSource}></SourceView>
            <div className="resultTableContainer">
                {
                    source === sources[0]&& data.length>0  && <ResultTable data={data}/> 
                }
                {
                    source === sources[1] && data.length>0 && <GreynoiseResult data={data} /> 

                }
            </div>
            <ToastContainer id="Toast" />
        </div>
    ) 
}