import { useState, useEffect} from "react";
import {Typography,MenuItem,FormControl,Select,Button,TextField,InputLabel, OutlinedInput}from "@mui/material";
import {isEmpty } from "lodash"
import axios from "axios";
import { protocols } from "../util/constants";
import { countries, usage_type, ipConstants } from "../util/constants";
import {ToastContainer,toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import SelectField from "../util/SelectField";
import ReverseResultTable from "../component/ReverseResultTable";
import convertIPv4NumberToAddress from "../util/converter";
export default function ReverseLookup() { 
    const [protocol,setProtocol] = useState('IPv4'); 
    const[filteredIP, setFilteredIP] = useState([]);
    const [filters,setFilters] = useState(ipConstants);
    const [isp,setIsp] = useState("");

    //on changing protocol, set filters to empty 
    useEffect( () => { 

        setFilters(ipConstants); 
        setFilteredIP([]); 
        setIsp("");

    },[protocol])



    const handleChange = (name,value) => { 
        // const copy =  _.cloneDeep(filters);
        if (name === 'country_name') { 
            setFilters(prev => ({...prev, country_name: value}))
        }

        if (name === 'usage_type') { 
            setFilters(prev => ({...prev, usage_type: value}))
        }
    }

    const handleReverseLookUp = (event) => {
        let formData = new FormData();
        formData.append('country_name',filters.country_name);
        formData.append('isp',isp);
        formData.append('usage_type',filters.usage_type);
        const config = { 
            headers : { 'content-type':'multipart/form-data'}
        }
        axios.post(
            `/reverseLookUp/${protocol}` , 
            formData, 
            config
        ).then(response => {
            if (response.data.response.length > 100) { 
                toast.error("Result size is above 100. Showing the first 100 results, use the API to access the full list.")
                setFilteredIP(response.data.response.slice(0,100))
            }
            else { 
                setFilteredIP(response.data.response)
            }
            } 
        ).catch ( error => toast.error(error.response.data.errorMessage));
    }



    return (
        <div className="Page">
            <Typography sx={{textDecoration: 'underline', color:"#650000"}} variant="h4">Reverse Lookup</Typography>
            <div className="selectProtocol">
                 <TextField
                id="selectProtocol"
                select
                defaultValue="IPv4"
                onChange={e=>{setProtocol(e.target.value)}}

            >
                {protocols.map((option)=> (
                    <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
                ))}
            </TextField>
            </div>
        
            <div className="selectFields">
            <SelectField protocol={protocol} list={countries} name='country_name' onChangeFunction={handleChange} /> 
            <SelectField protocol={protocol}  list={usage_type} name='usage_type' onChangeFunction={handleChange} /> 
            {/* <SelectField protocol={protocol}  list={isp} name='isp' onChangeFunction={handleChange} /> */}
            <TextField onChange={e=>{setIsp(e.target.value)}} variant="outlined" label="ISP"></TextField>
            <Button size="medium" variant="contained" onClick={e=>{handleReverseLookUp()}}>Look up</Button> 


            </div>
            {/* <div className="filteredIPs">
                {!isEmpty(filteredIP) && 
                    <ul>
                        {filteredIP.map((address) => { 
                            return <Typography key={address}>{address}</Typography>
                        })}
                    </ul>
                }
            </div> */}


            <ReverseResultTable data={filteredIP}/>
            <ToastContainer/>
        </div>
    )
}