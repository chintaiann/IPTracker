import { useState, useEffect} from "react";
import {Typography,MenuItem,FormControl,Select,Button,TextField,InputLabel, OutlinedInput}from "@mui/material";
import {isEmpty } from "lodash"
import axios from "axios";

export default function ReverseLookup() { 
    const [protocol,setProtocol] = useState('IPv4'); 

    //reverse lookup for countries 
    const [countries,setCountries] = useState([]); 
    const [selectedCountry,setSelectedCountry] = useState('');

    const[filteredIP, setFilteredIP] = useState([]);

    const protocols = [
        {
            value: 'IPv4',
            label: 'IPv4'
        },
        {
            value:'IPv6',
            label:'IPv6'
        }
    ]

    useEffect( () => { 
        fetch(`/getDistinctCountries/${protocol}`)
        .then(response => response.json())
        .then(data => setCountries(data))
        .then(setSelectedCountry('')).then(setFilteredIP([]));
    },[protocol])

    //fetch IPv4 by default on first load 
    useEffect( () => { 
        fetch("/getDistinctCountries/IPv4")
        .then(response => response.json())
        .then(data => setCountries(data));
    },[])

    const handleChange = (event) => { 
        const { target: {value}, } = event; 
        setSelectedCountry(value)
    }

    const handleCountryLookUp = ( event ) => { 
        axios(
            {
                method:'GET', 
                url:`/getIPFromCountry/${protocol}/${selectedCountry}`,
            }
                ).then(function (response){ 
                    setFilteredIP(response.data); 
                }
            )
    }


    return (
        <div>
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
            <Typography>Doing reverse lookup for {protocol}</Typography>

            <div className="selectCountry">
            <FormControl sx={{ m: 1, width: 300 }}>
                <InputLabel id="demo-multiple-name-label">Country</InputLabel>
                <Select
                labelId="demo-multiple-name-label"
                id="demo-multiple-name"
                // multiple
                value={selectedCountry}
                onChange= {handleChange}
                input={<OutlinedInput label="Country" />}
                // MenuProps={MenuProps}
                >
                {countries.map((country) => (
                    <MenuItem
                    key={country}
                    value={country}
                    // style={getStyles(name, personName, theme)}
                    >
                    {country}
                    </MenuItem>
                ))}
                </Select>
            </FormControl>
            <Button variant="contained" onClick={e=>{handleCountryLookUp()}}>Look up country</Button> 
            </div>


            <div className="filteredIPs">
                {!isEmpty(filteredIP) && 
                    <ul>
                        {filteredIP.map( (ip) => { 
                            return <Typography key={ip}>{ip}</Typography>
                        })}
                    </ul>
                }
            </div>
        </div>
    )
}