import { useState, useEffect} from "react";
import {Typography,MenuItem,FormControl,Select,Button,TextField,InputLabel, OutlinedInput}from "@mui/material";
import {isEmpty } from "lodash"
import axios from "axios";
import { protocols } from "../util/constants";
import { countries, usage_type, ipConstants } from "../util/constants";
import {ToastContainer,toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import SelectField from "../util/SelectField";

export default function ReverseLookup() { 
    const [protocol,setProtocol] = useState('IPv4'); 
    const[filteredIP, setFilteredIP] = useState([]);
    const [filters,setFilters] = useState(ipConstants);
    const [isp,setIsp] = useState([]);

    //fetch ISP based on Protocol 
    useEffect( () => { 
        fetch(`/getISP/${protocol}`)
        .then(setFilters(ipConstants)).then(setFilteredIP([]))
        .then(response => response.json())
        .then(data => setIsp(data));

    },[protocol])

    //fetch countries from IPv4 dataset by default on first load 
    useEffect( () => { 
        fetch("/getISP/IPv4")
        .then(response => response.json())
        .then(data => setIsp(data));
    },[])

    const handleChange = (name,value) => { 
        // const copy =  _.cloneDeep(filters);
        if (name === 'country_name') { 
            setFilters(prev => ({...prev, country_name: value}))
        }
        if (name === 'isp') { 
            setFilters(prev => ({...prev, isp: value}))
        }
        if (name === 'usage_type') { 
            setFilters(prev => ({...prev, usage_type: value}))
        }
    }

    const handleReverseLookUp = (event) => {
        let formData = new FormData();
        formData.append('country_name',filters.country_name);
        formData.append('isp',filters.isp);
        formData.append('usage_type',filters.usage_type);
        const config = { 
            headers : { 'content-type':'multipart/form-data'}
        }
        axios.post(
            `/reverseLookUp/${protocol}` , 
            formData, 
            config
        ).then(response => {setFilteredIP(response.data.response)} 
        ).catch ( error => toast.error(error.response.data.errorMessage));
    }

    // const handleCountryLookUp = ( event ) => { 
    //     axios (
    //         {
    //             method:'GET', 
    //             url:`/getIPFromCountry/${protocol}?country=${selectedCountry}&?usagetype=${usageType}`
    //         }
    //     ).then(function (response){
    //         console.log(response);
    //     }).catch (function (error) { 
    //         toast.error(error.response.data.errorMessage);
    //     })
    // }


    return (
        <div className="Page">
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

            <Typography>Doing reverse lookup for {protocol}</Typography>

            <div className="selectFields">
            <SelectField protocol={protocol} list={countries} name='country_name' onChangeFunction={handleChange} /> 
            <SelectField protocol={protocol}  list={usage_type} name='usage_type' onChangeFunction={handleChange} /> 
            <SelectField protocol={protocol}  list={isp} name='isp' onChangeFunction={handleChange} />
        
            <Button variant="contained" onClick={e=>{handleReverseLookUp()}}>Look up</Button> 


            </div>


            <div className="filteredIPs">
                {!isEmpty(filteredIP) && 
                    <ul>
                        {filteredIP.map((address) => { 
                            return <Typography key={address}>{address}</Typography>
                        })}
                    </ul>
                }
            </div>
            <ToastContainer/>
        </div>
    )
}